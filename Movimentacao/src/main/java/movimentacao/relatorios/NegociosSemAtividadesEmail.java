package movimentacao.relatorios;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import movimentacao.negocios.Negocios;
import movimentacao.negocios.atividades.Atividades;
import movimentacao.util.HibernateUtil;
import movimentacao.util.JavaMailApp;

public class NegociosSemAtividadesEmail
{	
	private static Negocios negocios = new Negocios();
	private static Atividades atividades = new Atividades();
	private static JavaMailApp jMail = new JavaMailApp();
	
	private static List<Negocios> listaNegocios = new ArrayList<Negocios>();
	private static List<NegociosSemAtividades> lista = new ArrayList<NegociosSemAtividades>();
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public void enviaRelatorio() throws IOException
	{
		//Definindo para disparar apenas no primeiro dia últil de cada mês============================
		int minutoAtual = Integer.parseInt(formatMin(new Date()));
		int primeiroDiaUtil = 0;
		int diaHoje = Integer.parseInt(formatDia(new Date()));
		int diaDaSemana = 0;
		
		//Pegando a data atual========================================================================
		GregorianCalendar gc = new GregorianCalendar();
		gc.getTime();
		
		//Este for é para descobrir o primeiro dia util do mês atual==================================
		for(int z = 1; z < 4; z++)
		{
			//Setando o dia pelo contador para descobrir qual dia não é sábado e nem domingo==========
			gc.set(Calendar.DAY_OF_MONTH, z);
			diaDaSemana = gc.get(Calendar.DAY_OF_WEEK);
						
			if(diaDaSemana != 1 && diaDaSemana != 7)
			{
				primeiroDiaUtil = z;
				
				//encerrando o for==================
				z = 5;
			}
		}

		//Validando se a data atual é igual ao primeiro dia util===================================
		if(primeiroDiaUtil == diaHoje)
		{
			//Executando apenas no horário especificado============================================
			if(formatHour(new Date()).equals("8") && minutoAtual < 10)
			{
			
			//Pegando a data de 30 dias atras da data atual======================
				Date dataHoje = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dataHoje);
				int dias = -30;
				calendar.add(Calendar.DATE, dias);
				Date dataFinal = calendar.getTime();
								
				//Zerando as horas para ajustar a comparação de datas no for abaixo
				SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
				try {
					dataFinal = sd.parse(sd.format(dataFinal));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			//====================================================================
			//Listagem dos negocios exceto os tipos Declined e Active Client
				//Bloco para abrir a sessao do hibernate==================================
		    	Session sessionAtiv = null;
				sessionAtiv = HibernateUtil.getSessionFactory().openSession();
				Transaction transacaoAtiv = sessionAtiv.beginTransaction();
				
				Query consulta = sessionAtiv.createQuery("from negocios where "
														+ "status = 'Lead'"
														+ " or status = 'Prospect'"
														+ " or status = 'POC / Proposal'"
														+ " or status = 'Contract'"
														+ " order by dataHora asc");
				this.listaNegocios = consulta.list();
				
				transacaoAtiv.commit();
			//===============================================================================
				
				for(int x = 0; x < listaNegocios.size(); x++)
				{
					this.negocios = listaNegocios.get(x);
					consulta = sessionAtiv.createQuery("from atividades where id_negocios = " + this.negocios.getId()
														+ " order by prazo desc");
					consulta.setMaxResults(1);
					this.atividades = (Atividades)consulta.uniqueResult();
					
					//Tratando os negócios sem atividades, os que possuem estão sendo setados no else abaixo===============
					if(this.atividades == null)
					{
						NegociosSemAtividades negSemAtiv = new NegociosSemAtividades();
						negSemAtiv.setStatusNegocio(this.negocios.getStatus());
						negSemAtiv.setNomeNegocio(this.negocios.getCliente().getNome());
						negSemAtiv.setValorNegocio(this.negocios.getValor());
						negSemAtiv.setPrazoAtividade(dataFinal);
						negSemAtiv.setAcaoAtividade("E-mail");
						negSemAtiv.setObsAtividade("NEGÓCIO SEM ATIVIDADE");
						negSemAtiv.setResponsavel(this.negocios.getUsuario().getNome());
						
						this.lista.add(negSemAtiv);
					}
					else
					{
						//Zerando as horas do prazo das atividades para ajustar a comparação com a data final acima
						try 
						{
							this.atividades.setPrazo(sd.parse(sd.format(this.atividades.getPrazo())));
							
							if(this.atividades.getPrazo().before(dataFinal))
							{
								NegociosSemAtividades negSemAtiv = new NegociosSemAtividades();
								negSemAtiv.setStatusNegocio(this.negocios.getStatus());
								negSemAtiv.setNomeNegocio(this.negocios.getCliente().getNome());
								negSemAtiv.setValorNegocio(this.negocios.getValor());
								negSemAtiv.setPrazoAtividade(this.atividades.getPrazo());
								negSemAtiv.setAcaoAtividade(this.atividades.getTipoAtividade());
								negSemAtiv.setObsAtividade(this.atividades.getObs());
								negSemAtiv.setResponsavel(this.atividades.getUsuario().getNome());
								
								this.lista.add(negSemAtiv);
							}
							
						} 
						catch (ParseException e) 
						{
							e.printStackTrace();
						}
					}
					
					this.atividades = null;
					
				}
				//Ordenando a lista pelo prazo da atividade crescente=========
				Collections.sort(this.lista);
				
				int qtde = this.lista.size();
				float valor = 0;
				String textNegocios = "";
				
				for(int y = 0; y < this.lista.size(); y++)
				{
					textNegocios = "<b>Status:</b> " + this.lista.get(y).getStatusNegocio()
									+ "<br><b>Cliente:</b> " + this.lista.get(y).getNomeNegocio()
									+ "<br><b>Valor:</b> " + String.format("R$%.2f" , this.lista.get(y).getValorNegocio())
									+ "<br><b>Última Atividade:</b> " + formatDate(this.lista.get(y).getPrazoAtividade())
									+ "<br><b>Ação:</b> " + this.lista.get(y).getAcaoAtividade()
									+ "<br><b>Responsável:</b> " + this.lista.get(y).getResponsavel()
									+ "<br><b>Obs:</b> " + this.lista.get(y).getObsAtividade()
									+"<br><br>==================================================================<br><br>"
									+ textNegocios;
					valor = valor + this.lista.get(y).getValorNegocio();
				}
				
				jMail.setDestinatario("raphael@performancelab.com.br");
				jMail.setAssunto("Unity - Stopped Business Tracking");
				jMail.setMsg("<center><h2>Unity - Stopped Business Tracking</h2></center>"
							+"<br>Olá, " 
							+"<br><br>Temos " + qtde + " negócios sem atividades com mais de 30 dias, totalizando " + String.format("R$%.2f" , valor)
							+"<br><br>"
							+ textNegocios
							+ "<br>Atenciosamente,"
							+ "<br><br><b><i>Equipe Performancelab</i></b>");
			
				jMail.sendMail();
				
				jMail = new JavaMailApp();
				
			}
			
		}
		
		
	}
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return dateFormat.format(data);
	}
	
	private String formatDia(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("d");
		
		return dateFormat.format(data);
	}
	
	private static String formatHour(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("H");
		
		return dateFormat.format(data);
	}
	
	private static String formatMin(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("m");
		
		return dateFormat.format(data);
	}
	
}
