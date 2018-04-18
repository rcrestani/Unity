package movimentacao.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import movimentacao.projetoAES.ControleFrota;
import movimentacao.projetoAES.ControleFrotaFiltro;
import movimentacao.projetoAES.ControleFrotaRN;
import movimentacao.projetoAES.LazyControleFrotaDataModel;
import movimentacao.usuario.UsuarioRN;
import movimentacao.util.JavaMailApp;
import movimentacao.util.UtilException;
import movimentacao.web.util.RelatorioUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "controleFrotaBean")
@ViewScoped
public class ControleFrotaBean implements Serializable
{
	private static final long serialVersionUID = 7067955497889472078L;
	
	private ControleFrotaFiltro filtro = new ControleFrotaFiltro();
	private ControleFrotaRN controleFrotaRN = new ControleFrotaRN();
	
	private StreamedContent arquivoRetorno;
	private int tipoRelatorio;
	
	private StreamedContent arquivoRetornoCsv;
	
	private JavaMailApp jMail = new JavaMailApp();
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	
	private List<ControleFrota> lista = new ArrayList<ControleFrota>();
	private LazyControleFrotaDataModel lazyControleFrota;

	public LazyControleFrotaDataModel getLazyControleFrota() {
		return lazyControleFrota;
	}
	
	@PostConstruct
	public void init()
	{
		this.lazyControleFrota = new LazyControleFrotaDataModel(controleFrotaRN, filtro);
	}
	
	//Método para gerar relatório e fornecer download do arquivo===========================
	public StreamedContent getArquivoRetorno() throws IOException
	{
		this.lista = controleFrotaRN.consultaLista(filtro);
		//For para setar o periodo escolhido pelo usuario==========
		for(int x = 0; x < this.lista.size();x++)
		{
			this.lista.get(x).setPeriodoInicial(filtro.getInicio());
			this.lista.get(x).setPeriodoFinal(filtro.getFim());
		}
		
		//FacesContext context = FacesContext.getCurrentInstance();
		String nomeRelatorioJasper = "ControleFrota";
		String nomeRelatorioSaida = "ControleFrota_" + formatDate(new Date());
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		JRBeanCollectionDataSource cBean = new JRBeanCollectionDataSource(lista);
		
		try
		{
			this.arquivoRetorno = relatorioUtil.geraRelatorio(nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio, cBean);
			
		}
		catch (UtilException e)
		{
			/*context.addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Motivo: " + e.getMessage(), ""));*/
			UsuarioRN usuarioRN = new UsuarioRN();
			jMail.setDestinatario("suporte@performancelab.com.br");
			jMail.setAssunto("Unity: Controle Frota - Erro ao exportar relatório");
			jMail.setMsg("Usuário: " + usuarioRN.buscarPorLogin(this.login).getNome()
					+ "\n\nMotivo do Erro: " + e.getMessage()
					+ "\n\nConsulta Realizada: "
					+ "\n-->Data Inicial: " + filtro.getInicio()
					+ "\n-->Data Final: " + filtro.getFim()
					+ "\n-->Base: " + filtro.getColetor()
					+ "\n-->Veículo: " + filtro.getVtr()
					+ "\n-->Motorista: " + filtro.getMotorista());
			jMail.sendMail();
			
			return null;
		}
		
		return this.arquivoRetorno;
	}
	
	//Método exclusivo para gerar arquivo csv===========================================================
	public StreamedContent getArquivoRetornoCsv() throws IOException
	{
		FacesContext contextoFaces = FacesContext.getCurrentInstance();
		ExternalContext contextoExterno = contextoFaces.getExternalContext();
		ServletContext contextoServlet = (ServletContext) contextoExterno.getContext();
		String caminhoRelatorios = contextoServlet.getRealPath("/relatorios");
		
		String nomeRelatorioSaida = "ControleFrota_" + formatDate(new Date()) + ".csv";
		String caminhoArquivoRelatorio = caminhoRelatorios + File.separator + nomeRelatorioSaida;
		
		File arquivoGerado = new File(caminhoArquivoRelatorio);
		FileWriter writer = new FileWriter(arquivoGerado);
		
		List<String> conteudoCsv = new ArrayList<String>();
		//Setando as colunas do relatório========
		conteudoCsv.add("Base Coletora");
		conteudoCsv.add("Viatura");
		conteudoCsv.add("Motorista");
		conteudoCsv.add("Saída");
		conteudoCsv.add("Chegada");
		conteudoCsv.add("Tempo");
		//Escrevendo as colunas na primeira linha do arquivo
		writer.write(conteudoCsv.stream().collect(Collectors.joining(";")));
		//Inserindo nova linha
		writer.write("\n");
		//Limpando o ArrayList para não dar efeito cumulativo nos dados
		conteudoCsv.clear();
		
		this.lista = controleFrotaRN.consultaLista(filtro);

		try
		{
			for(ControleFrota controleFrota:this.lista)
			{
				conteudoCsv.add(controleFrota.getColetor());
				conteudoCsv.add(controleFrota.getVtr());
				conteudoCsv.add(controleFrota.getMotorista());
				conteudoCsv.add(formatDateHour(controleFrota.getSaida()));
				conteudoCsv.add(formatDateHour(controleFrota.getChegada()));
				conteudoCsv.add(controleFrota.getTempoMissao());
				
				//Escrevendo os dados linha a linha
				writer.write(conteudoCsv.stream().collect(Collectors.joining(";")));
				//Inserindo nova linha
				writer.write("\n");
				//Limpando o ArrayList para não dar efeito cumulativo nos dados
				conteudoCsv.clear();
			}

		    writer.close();
		    arquivoGerado.deleteOnExit();
		    
		    InputStream conteudoRelatorioCsv = new FileInputStream(arquivoGerado);
			this.arquivoRetornoCsv = new DefaultStreamedContent(conteudoRelatorioCsv, "application/" 
				+ "csv", nomeRelatorioSaida); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		return this.arquivoRetornoCsv;
	}
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(data);
	}
	
	private String formatDateHour(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - H:mm");
		
		if(data != null)
		{
			return dateFormat.format(data);
		}
		else
		{
			return "";
		}
		
	}
	
	public ControleFrotaFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(ControleFrotaFiltro filtro) {
		this.filtro = filtro;
	}

	public ControleFrotaRN getControleFrotaRN() {
		return controleFrotaRN;
	}

	public void setControleFrotaRN(ControleFrotaRN controleFrotaRN) {
		this.controleFrotaRN = controleFrotaRN;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

}
