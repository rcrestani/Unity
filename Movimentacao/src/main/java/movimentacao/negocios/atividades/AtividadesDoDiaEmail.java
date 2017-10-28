package movimentacao.negocios.atividades;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import movimentacao.usuario.Usuario;
import movimentacao.util.HibernateUtil;
import movimentacao.util.JavaMailApp;

public class AtividadesDoDiaEmail 
{
	private static JavaMailApp jMail = new JavaMailApp();
	private static Atividades atividades = new Atividades();
	private static List<Usuario> listaUsuario = new ArrayList<Usuario>();
	private static List<Atividades> atividadesDoDia = new ArrayList<Atividades>();
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public void enviaRelatorio() throws IOException
	{
		int minutoAtual = Integer.parseInt(formatMin(new Date()));
		if(formatHour(new Date()).equals("8") && minutoAtual < 10)
		{
		    //Bloco para abrir a sessao do hibernate==================================
		    	Session sessionAtiv = null;
				sessionAtiv = HibernateUtil.getSessionFactory().openSession();
				Transaction transacaoAtiv = sessionAtiv.beginTransaction();
				
				Date dataHoje = new Date();
				Query consulta = sessionAtiv.createQuery("from atividades where date(prazo) = :dataHoje order by prazo asc");
				consulta.setDate("dataHoje", dataHoje);
				this.atividadesDoDia = consulta.list();
				
				transacaoAtiv.commit();
			//===============================================================================
		    //Coletando os usuarios que possuem atividades e com HashSet eliminando os usuarios repetidos na listagem========
				for(int z = 0; z < atividadesDoDia.size(); z++)
				{
					this.listaUsuario.add(atividadesDoDia.get(z).getUsuario());
				}
				HashSet<Usuario> usuarioUnico = new HashSet<Usuario>();
				usuarioUnico.addAll(this.listaUsuario);
				this.listaUsuario.clear();
				this.listaUsuario.addAll(usuarioUnico);
			//================================================================================================================
			//Rodando os fors para enviar as atividades para cada usuario=====================================================	
				for(int x = 0; x < this.listaUsuario.size(); x++)
				{
					String textAtividades = "";
					for(int y = 0; y < this.atividadesDoDia.size(); y++)
					{
						if(this.atividadesDoDia.get(y).getUsuario().equals(this.listaUsuario.get(x)))
						{
							this.atividades = atividadesDoDia.get(y);
							textAtividades = "<b>Cliente:</b> " + this.atividades.getNegocios().getCliente().getNome()
											+ "<br><b>Ação:</b> " + this.atividades.getTipoAtividade()
											+ "<br><b>Prazo:</b> " + formatDateHour(this.atividades.getPrazo())
											+ "<br><b>Valor:</b> " + String.format("R$%.2f", this.atividades.getNegocios().getValor())
											+ "<br><b>Obs:</b> " + this.atividades.getObs()
											+ "<br><h3>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</h3><br>"
											+ textAtividades;
						}
						
					}
										
					jMail.setDestinatario(this.listaUsuario.get(x).getEmail());
					jMail.setAssunto("Unity - Atividades de Hoje");
					jMail.setMsg("<center><h2>Unity - Atividades de Hoje</h2></center>"
								+"<br>Olá "+ this.listaUsuario.get(x).getNome() + "," 
								+"<br><br>Abaixo estão as suas atividades de hoje ("+ formatDate(new Date()) + "):"
								+"<br><br>"
								+ textAtividades
								+ "<br><br>Atenciosamente,"
								+ "<br><br><b><i>Equipe Performancelab</i></b>");
				
					jMail.sendMail();
					
					jMail = new JavaMailApp();
				}
				sessionAtiv.close();
		}
	}
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return dateFormat.format(data);
	}
	
	private String formatDateHour(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - H:mm");
		
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
