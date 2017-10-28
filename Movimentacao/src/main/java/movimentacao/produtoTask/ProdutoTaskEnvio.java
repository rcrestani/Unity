package movimentacao.produtoTask;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import movimentacao.produto.Produto;
import movimentacao.util.HibernateUtil;
import movimentacao.util.JavaMailApp;
import movimentacao.util.RelatorioUtilStreamTask;
import movimentacao.util.UtilException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProdutoTaskEnvio
{
	private static List<Produto> lista = new ArrayList<Produto>();
	private static List<ProdutoTask> listaProdTask = new ArrayList<ProdutoTask>();
	private static JavaMailApp jMail = new JavaMailApp();
	private static int tipoRelatorio;
	private static InputStream conteudoRetorno;
	
	private static Calendar calendar = Calendar.getInstance();
	
	private static int minutoAtual = 0;
	private static int horaAtual = 0;
	
    @SuppressWarnings("unchecked")
	public void enviaRelatorio() throws UtilException, IOException, Exception
    {
		lista.clear();
		listaProdTask.clear();
    	//Bloco para abrir a sessao do hibernate==================================
			Session sessionTask = null;
			sessionTask = HibernateUtil.getSessionFactory().openSession();
			System.out.println("STATUS SESSIONTASK: " + sessionTask.isOpen());
			Transaction transacaoTask = sessionTask.beginTransaction();
	    		listaProdTask = sessionTask.createCriteria(ProdutoTask.class).list();
	    	transacaoTask.commit();
	    	//sessionTask.close();
    	//========================================================================
    	
	    	
	    for(int x = 0; x < listaProdTask.size(); x++)
	    {
	    	ProdutoTask produtoTask = new ProdutoTask();
	    	horaAtual = 0;
	    	minutoAtual = 0;
	    	
	    	produtoTask = listaProdTask.get(x);
	    	
	    	int diaSemanaAtual = calendar.get(Calendar.DAY_OF_WEEK);
	    	horaAtual = Integer.parseInt(formatHour(new Date()));
	    	minutoAtual = Integer.parseInt(formatMin(new Date()));
	    	
	    	Set<String> dias = produtoTask.getDiasDaSemana();
	    	
	    	//Verificar se é o dia da semana da tarefa========================================
	    	if(dias.contains(diaDaSemana(diaSemanaAtual)) && produtoTask.isStatus() == true)
	    	{
	    		//Verificar se é a hora da tarefa=============================================
	    		if(produtoTask.getHora() == horaAtual && minutoAtual < 10)
	    		{
	    			tipoRelatorio = 1;
	    				    			
	    			//Bloco para abrir a sessao do hibernate==================================
	    			//sessionTask.close();
	    			//Session session = null;
	    			//session = HibernateUtil.getSessionFactory().openSession();
	    			//Transaction transacao = session.beginTransaction();
	    	    		lista = sessionTask.createCriteria(Produto.class).list();
	    	    	//transacaoTask.commit();
	    	    	
	    			//========================================================================
	    	    	
	    			String nomeRelatorioJasper = "PosicaoEstoque";
	    			String nomeRelatorioSaida = "PosicaoEstoque_" + formatDate(new Date());
	    			RelatorioUtilStreamTask relatorioUtilStream = new RelatorioUtilStreamTask();
	    			JRBeanCollectionDataSource cBean = new JRBeanCollectionDataSource(lista);	
	    			
	    			conteudoRetorno = relatorioUtilStream.geraRelatorio(nomeRelatorioJasper, nomeRelatorioSaida, tipoRelatorio, cBean);
	    			
	    			jMail.setDestinatario(produtoTask.getEmails());
	    			jMail.setAssunto(produtoTask.getNomeRelatorio());
	    			jMail.setMsg("Olá,\nSegue em anexo o Relatório da posição do estoque, gerado em "
	    							+ formatDate(new Date()) + ".\n\nAtenciosamente,\n\nEquipe Performancelab");
	    					
	    			jMail.setArquivo(conteudoRetorno);
	    			jMail.setApplicationType("application/pdf");
	    			jMail.setNomeArquivo("PosicaoEstoque_" + formatDate(new Date()) + ".pdf");
	    		
	    			jMail.sendMail();
	    			sessionTask.close();
	    		}
	    	}
	    }	
    }
    
    private static String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(data);
	}
    
	private static String formatHour(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("k");
		
		return dateFormat.format(data);
	}
	
	private static String formatMin(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("m");
		
		return dateFormat.format(data);
	}
	
	private static String diaDaSemana(int dia)
	{
		String diaDaSemana = "";
		
		switch (dia)
		{
			case 1:
				diaDaSemana = "domingo";
				break;
				
			case 2:
				diaDaSemana = "segunda";
				break;
			
			case 3:
				diaDaSemana = "terca";
				break;
				
			case 4:
				diaDaSemana = "quarta";
				break;
				
			case 5:
				diaDaSemana = "quinta";
				break;
				
			case 6:
				diaDaSemana = "sexta";
				break;
				
			case 7:
				diaDaSemana = "sabado";
				break;
		}
		
		return diaDaSemana;
	}
    
}
