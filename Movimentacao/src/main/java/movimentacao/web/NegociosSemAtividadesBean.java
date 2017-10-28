package movimentacao.web;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import movimentacao.negocios.Negocios;
import movimentacao.negocios.NegociosRN;
import movimentacao.negocios.atividades.Atividades;
import movimentacao.negocios.atividades.AtividadesRN;
import movimentacao.relatorios.NegociosSemAtividades;

@ManagedBean(name = "negSemAtivBean")
@ViewScoped
public class NegociosSemAtividadesBean implements Serializable 
{
	private static final long serialVersionUID = -7201406643362792829L;
	
	private Negocios negocios = new Negocios();
	private NegociosRN negociosRN = new NegociosRN();
	private Atividades atividades = new Atividades();
	private AtividadesRN atividadesRN = new AtividadesRN();
	
	private int qtde;
	private List<NegociosSemAtividades> lista = new ArrayList<NegociosSemAtividades>();
	
	@PostConstruct
	public void init()
	{
		filtrar();
		this.qtde = this.lista.size();
	}
	
	public String filtrar()
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
			try 
			{
				dataFinal = sd.parse(sd.format(dataFinal));
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		//====================================================================
			//Listagem dos negocios exceto os tipos Declined e Active Client
			List<Negocios> listaNegocios = this.negociosRN.negociosPreVenda();
			
			for(int x = 0; x < listaNegocios.size(); x++)
			{
				this.negocios = listaNegocios.get(x);
				this.atividades = this.atividadesRN.ultimaAtividade(this.negocios);
				
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
			
			/*System.out.println("NEGÓCIOS SEM ATIVIDADES COM MAIS DE 30 DIAS\n");
			for(int y = 0; y < this.lista.size(); y++)
			{
				System.out.println("Status: " + this.lista.get(y).getStatusNegocio()
									+ "\nCliente: " + this.lista.get(y).getNomeNegocio()
									+ "\nValor: " + this.lista.get(y).getValorNegocio()
									+ "\nÚltima Atividade: " + formatDate(this.lista.get(y).getPrazoAtividade())
									+ "\nAção: " + this.lista.get(y).getAcaoAtividade()
									+ "\nResponsável: " + this.lista.get(y).getResponsavel()
									+ "\nObs: " + this.lista.get(y).getObsAtividade());
				System.out.println("\n==================================================================\n");
			}*/
		
		return null;
	}
	
	/*private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return dateFormat.format(data);
	}*/

	public List<NegociosSemAtividades> getLista() {
		return lista;
	}

	public void setLista(List<NegociosSemAtividades> lista) {
		this.lista = lista;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}
	
	
}
