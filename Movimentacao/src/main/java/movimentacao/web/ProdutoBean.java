package movimentacao.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import movimentacao.produto.LazyProdutoDataModel;
import movimentacao.produto.Produto;
import movimentacao.produto.ProdutoFiltro;
import movimentacao.produto.ProdutoRN;
import movimentacao.produtoTask.ProdutoTask;
import movimentacao.produtoTask.ProdutoTaskRN;
import movimentacao.util.JavaMailApp;
import movimentacao.util.RelatorioUtilStream;
import movimentacao.util.UtilException;
import movimentacao.web.util.RelatorioUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "produtoBean")
@ViewScoped
public class ProdutoBean implements Serializable
{
	private static final long serialVersionUID = -6780432052789644854L;
	
	private Produto produto = new Produto();
	private ProdutoRN produtoRN = new ProdutoRN();
	private LazyProdutoDataModel lazyProduto;
	private ProdutoFiltro filtro = new ProdutoFiltro();
		
	private JavaMailApp jMail = new JavaMailApp();
	private String destinatario , formatoRelatorio;
	
	private StreamedContent arquivoRetorno;
	private InputStream conteudoRetorno;
	private int tipoRelatorio;
	private List<Produto> lista = new ArrayList<Produto>();	
	
	private ProdutoTask produtoTask = new ProdutoTask();
	private ProdutoTaskRN produtoTaskRN = new ProdutoTaskRN();
	private List<ProdutoTask> listaTask = new ArrayList<ProdutoTask>();
	private List<String> diasDaSemana;
	private List<String> diasSelecionados = new ArrayList<String>();
	private boolean editando = false;
	private List<String> emails = new ArrayList<String>();
	
	
	@PostConstruct
	public void init()
	{
		lazyProduto = new LazyProdutoDataModel(produtoRN, filtro);
		
		diasDaSemana = new ArrayList<String>();
		diasDaSemana.add("domingo");
		diasDaSemana.add("segunda");
		diasDaSemana.add("terca");
		diasDaSemana.add("quarta");
		diasDaSemana.add("quinta");
		diasDaSemana.add("sexta");
		diasDaSemana.add("sabado");
	}
	
	public String salvar()
	{
		try
		{
			Produto produtoNovo = new Produto();
			produtoNovo = produtoRN.carregar(produto.getCodigo());
			
			if(produtoNovo.getCodigo() == null)
			{
				produtoNovo.setDescricao(produto.getDescricao());
				produtoNovo.setStatus(produto.isStatus());
				produtoNovo.setQtde(0);
				System.out.println("DESC " + produtoNovo.getDescricao());
				produtoRN.salvar(produtoNovo);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , produto.getDescricao() + " salvo com sucesso!" , ""));
				return "/restrito/produto.jsf";
			}
			else
			{
				produtoNovo.setDescricao(produto.getDescricao());
				produtoNovo.setStatus(produto.isStatus());
				produtoRN.salvar(produtoNovo);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , produto.getDescricao() + " salvo com sucesso!" , ""));
				return "/restrito/produto.jsf";
			}
			
		}
		catch (Exception e)
		{
		    e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro!", "O Produto não foi gravado no Banco de Dados. Motivo: " + e.getMessage()));
		    return null;
		}
		
	}
	
	public void limparForm()
	{
		this.produto = new Produto();
	}
	
	
	public String sts()
	{
		if (this.produto.isStatus())
		{
			this.produto.setStatus(false);
		}
		else
		{
			this.produto.setStatus(true);
		}
		
		ProdutoRN produtoRN = new ProdutoRN();
		produtoRN.salvar(this.produto);
		
		return null;
	}
	
	//Método para gerar relatório e fornecer download do arquivo
	public StreamedContent getArquivoRetorno()
	{
		lista = produtoRN.listar();
		FacesContext context = FacesContext.getCurrentInstance();
		String nomeRelatorioJasper = "PosicaoEstoque";
		String nomeRelatorioSaida = "PosicaoEstoque_" + formatDate(new Date());
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		JRBeanCollectionDataSource cBean = new JRBeanCollectionDataSource(lista);
		
		try
		{
			this.arquivoRetorno = relatorioUtil.geraRelatorio(nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio, cBean);
			
		}
		catch (UtilException e)
		{
			context.addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Motivo: " + e.getMessage(), ""));
			return null;
		}
		
		return this.arquivoRetorno;
	}
	
	//Método para gerar relatório em memória e enviar anexo para email.
	public InputStream getConteudoRelatorio() throws IOException
	{
		lista = produtoRN.listar();
		FacesContext context = FacesContext.getCurrentInstance();
		String nomeRelatorioJasper = "PosicaoEstoque";
		String nomeRelatorioSaida = "PosicaoEstoque_" + formatDate(new Date());
		RelatorioUtilStream relatorioUtilStream = new RelatorioUtilStream();
		JRBeanCollectionDataSource cBean = new JRBeanCollectionDataSource(lista);
		
		try
		{
			this.conteudoRetorno = relatorioUtilStream.geraRelatorio(nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio, cBean);
			
		}
		catch (UtilException e)
		{
			context.addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Motivo: " + e.getMessage(), ""));
			return null;
		}
		
		return this.conteudoRetorno;
	}
	
	public String sendEmail() throws IOException
	{
		jMail.setDestinatario(destinatario);
		jMail.setAssunto("Relatório Posição Estoque");
		jMail.setMsg("<center><h3>Relatório Posição Estoque</h3></center>"
					+ "<br>Olá,<br>Segue em anexo o Relatório da posição do estoque, gerado em "
					+ formatDate(new Date()) + ".<br><br>Atenciosamente,<br><br><b><i>Equipe Performancelab</i></b>");
		
		if(formatoRelatorio.equals("pdf"))
		{
			this.tipoRelatorio = 1;
			jMail.setArquivo(getConteudoRelatorio());
			jMail.setApplicationType("application/pdf");
			jMail.setNomeArquivo("PosicaoEstoque_" + formatDate(new Date()) + ".pdf");
		}
		else if(formatoRelatorio.equals("xls"))
		{
			this.tipoRelatorio = 2;
			jMail.setArquivo(getConteudoRelatorio());
			jMail.setApplicationType("application/xls");
			jMail.setNomeArquivo("PosicaoEstoque_" + formatDate(new Date()) + ".xls");
		}
		else if(formatoRelatorio.equals("html"))
		{
			this.tipoRelatorio = 3;
			jMail.setArquivo(getConteudoRelatorio());
			jMail.setApplicationType("application/html");
			jMail.setNomeArquivo("PosicaoEstoque_" + formatDate(new Date()) + ".html");
		}
		else if(formatoRelatorio.equals("ods"))
		{
			this.tipoRelatorio = 4;
			jMail.setArquivo(getConteudoRelatorio());
			jMail.setApplicationType("application/ods");
			jMail.setNomeArquivo("PosicaoEstoque_" + formatDate(new Date()) + ".ods");
		}
		
		
		try 
		{
			jMail.sendMail();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "E-mail enviado com sucesso!" , ""));
		} 
		catch (IOException e) 
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro!", "O e-mail não foi enviado. Motivo: " + e.getMessage()));
		}
		
		return null;
	}
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(data);
	}
	
	public String salvarTarefa()
	{
		//Limpando a lista do objeto para que as alterações sejam realizadas======================
		produtoTask.getDiasDaSemana().clear();
		
		for(int x = 0 ; x < diasSelecionados.size() ; x++)
		{
			produtoTask.getDiasDaSemana().add(diasSelecionados.get(x));
		}
		//=========================================================================================
		
		//Tranformando o ArrayList em String separando os e-mails por vírgula======================
		String text = "";
		
		for(int x = 0 ; x < emails.size() ; x++)
		{
			text = text + emails.get(x);
			
			if(x < emails.size() - 1)
			{
				text = text + ",";
			}
		}
		
		produtoTask.setEmails(text);
		//=========================================================================================
		
		produtoTaskRN.salvar(produtoTask);
		
		//Limpando o formulário após salvar========================================================
		produtoTask = new ProdutoTask();
		emails.clear();
		//=========================================================================================
		
		//Recarregando a lista da datatable========================================================
		listaTask = produtoTaskRN.listar();
		
		return null;
	}
	
	public String statusTask()
	{
		if (this.produtoTask.isStatus())
		{
			this.produtoTask.setStatus(false);
		}
		else
		{
			this.produtoTask.setStatus(true);
		}
		
		produtoTaskRN.salvar(produtoTask);

		return null;
	}
	
	public String limparFormTask()
	{
		produtoTask = new ProdutoTask();
		listaTask = produtoTaskRN.listar();
		
		return null;
	}
	
	public String editarTask()
	{
		String [] texto = produtoTask.getEmails().split(",");
		emails = Arrays.asList(texto);
		
		diasSelecionados.addAll(produtoTask.getDiasDaSemana());
		
		editando = true;
		
		return null;
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public ProdutoRN getProdutoRN() {
		return produtoRN;
	}
	public void setProdutoRN(ProdutoRN produtoRN) {
		this.produtoRN = produtoRN;
	}
	public ProdutoFiltro getFiltro() {
		return filtro;
	}
	public void setFiltro(ProdutoFiltro filtro) {
		this.filtro = filtro;
	}
	public LazyProdutoDataModel getLazyProduto() {
		return lazyProduto;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public List<Produto> getLista() {
		return lista;
	}

	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getFormatoRelatorio() {
		return formatoRelatorio;
	}

	public void setFormatoRelatorio(String formatoRelatorio) {
		this.formatoRelatorio = formatoRelatorio;
	}

	public List<String> getDiasDaSemana() {
		return diasDaSemana;
	}

	public void setDiasDaSemana(List<String> diasDaSemana) {
		this.diasDaSemana = diasDaSemana;
	}

	public List<String> getDiasSelecionados() {
		return diasSelecionados;
	}

	public void setDiasSelecionados(List<String> diasSelecionados) {
		this.diasSelecionados = diasSelecionados;
	}

	public ProdutoTask getProdutoTask() {
		return produtoTask;
	}

	public void setProdutoTask(ProdutoTask produtoTask) {
		this.produtoTask = produtoTask;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public ProdutoTaskRN getProdutoTaskRN() {
		return produtoTaskRN;
	}

	public void setProdutoTaskRN(ProdutoTaskRN produtoTaskRN) {
		this.produtoTaskRN = produtoTaskRN;
	}

	public List<ProdutoTask> getListaTask() {
		return listaTask;
	}

	public void setListaTask(List<ProdutoTask> listaTask) {
		this.listaTask = listaTask;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}
	
}
