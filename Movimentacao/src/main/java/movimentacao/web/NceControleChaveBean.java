package movimentacao.web;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.rpc.ServiceException;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteServiceLocator;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SQLException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.ControleChaveFiltro;
import movimentacao.projetoNCE.ControleChaveRN;
import movimentacao.projetoNCE.LazyControleChaveDataModel;
import movimentacao.projetoNCE.ListaSiteChave;
import movimentacao.projetoNCE.chave.Chave;
import movimentacao.projetoNCE.chave.ChaveRN;
import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.projetoNCE.empresa.EmpresaRN;
import movimentacao.projetoNCE.site.Site;
import movimentacao.projetoNCE.site.SiteRN;
import movimentacao.projetoNCE.tecnico.Tecnico;
import movimentacao.projetoNCE.tecnico.TecnicoRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean(name = "nceControleChaveBean")
@ViewScoped
public class NceControleChaveBean implements Serializable
{
	private static final long serialVersionUID = 7741636814187218597L;
	
	private ControleChave controleChave = new ControleChave();
	private ControleChaveRN controleChaveRN = new ControleChaveRN();
	private UsuarioRN usuarioRN = new UsuarioRN();
	private Tecnico tecnico = new Tecnico();
	private TecnicoRN tecnicoRN = new TecnicoRN();
	private Empresa empresa = new Empresa();
	private EmpresaRN empresaRN = new EmpresaRN();
	private Site site = new Site();
	private SiteRN siteRN = new SiteRN();
	private List<Site> listaSites;
	private Chave chave = new Chave();
	private ChaveRN chaveRN = new ChaveRN();
	private List<Chave> listaChaves = new ArrayList<Chave>();
	private int totalListaChaves = 0;
	private List<ListaSiteChave> listaSiteChave = new ArrayList<ListaSiteChave>();
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	private String usuario = "";
	private String nomeTecnico = "";
	private String nomeEmpresa = "";
	private String cep = "";
	
	//Variaveis de controle de formulario===============================
	private boolean formControleChave = false;
	private boolean formSiteChave = false;
	private boolean siteChaveRequisitado = false;
	//==================================================================
	private ControleChaveFiltro filtro = new ControleChaveFiltro();
	private LazyControleChaveDataModel lazyControleChave;
	
	public LazyControleChaveDataModel getLazyControleChave() {
		return lazyControleChave;
	}

	@PostConstruct
	public void init()
	{
		this.lazyControleChave = new LazyControleChaveDataModel(this.controleChaveRN, this.filtro);
	}
	
	public String recuperarId()
	{
		int ultimoId = this.controleChaveRN.ultimoRegistro().getId() + 1;
		this.controleChave.setIdAno(String.format("%d",  ultimoId) + "/" + formatYear(new Date()));
		
		this.tecnico = this.tecnicoRN.tecnicoPorNome(this.nomeTecnico);
		this.controleChave.setIdTecnico(this.tecnico);
		
		this.controleChave.setDataHoraReg(new Date());
		this.controleChave.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.controleChaveRN.salvar(this.controleChave);
		
		return null;
	}
	
	public String salvar()
	{
		this.controleChaveRN.salvar(this.controleChave);
		
		for(int x = 0;x < this.listaChaves.size();x++)
		{
			this.listaChaves.get(x).setIdControleChave(this.controleChave);
			
			this.chaveRN.salvar(this.listaChaves.get(x));
		}
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Requisição Registrada com sucesso!", ""));
		
		this.formControleChave = false;
		this.listaSiteChave.clear();
		return null;
	}
	
	public String novo()
	{
		this.controleChave = new ControleChave();
		this.controleChave.setDataAbertura(new Date());
		this.formControleChave = true;
		this.formSiteChave = false;
		this.siteChaveRequisitado = false;
		this.usuario = this.usuarioRN.buscarPorLogin(this.login).getNome();
		this.tecnico = new Tecnico();
		this.empresa = new Empresa();
		this.site = new Site();
		this.chave = new Chave();
		this.nomeTecnico = "";
		this.nomeEmpresa = "";
		this.cep = "";
		
		return null;
	}
	
	//MÉTODOS PARA O BEAN TECNICO===============================================================
	public String salvarTecnico()
	{
		this.tecnico.setIdEmpresa(this.empresaRN.empresaPorNome(this.nomeEmpresa));
		this.tecnico.setDataHoraReg(new Date());
		this.tecnico.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.tecnicoRN.salvar(this.tecnico);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Técnico Registrado com sucesso!", ""));
		
		this.nomeEmpresa = "";
		this.tecnico = new Tecnico();
		return null;
	}
	
	public void tecnicoSelecionado(SelectEvent event) 
	{
		String nomeTecnico = this.tecnico.getNome();
		this.tecnico = new Tecnico();
		
		try
		{
			this.tecnico = this.tecnicoRN.tecnicoPorNome(nomeTecnico);
			this.nomeEmpresa = this.empresaRN.carregar(this.tecnico.getIdEmpresa().getId()).getRazaoSocial();
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
		}
		
	}
	//==========================================================================================
	
	//MÉTODOS PARA O BEAN EMPRESA===============================================================
	public String salvarEmpresa()
	{
		this.empresa.setCep(this.cep);
		this.empresa.setDataHoraReg(new Date());
		this.empresa.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.empresaRN.salvar(this.empresa);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Empresa Registrada com sucesso!", ""));
		
		this.cep = "";
		this.empresa = new Empresa();
		
		return null;
	}
	
	public void buscarEndereco(ActionEvent actionEvent) throws MalformedURLException, ServiceException, SQLException, SigepClienteException, RemoteException
	{
		AtendeClienteServiceLocator serviceCorreios = new AtendeClienteServiceLocator();
		AtendeCliente atendeCliente = serviceCorreios.getAtendeClientePort(
				new URL("https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl"));
		EnderecoERP correios = new EnderecoERP();
		correios = atendeCliente.consultaCEP(this.cep);
		
		this.cep = correios.getCep();
		this.empresa.setEndereco(correios.getEnd());
		this.empresa.setBairro(correios.getBairro());
		this.empresa.setCidade(correios.getCidade());
		this.empresa.setUf(correios.getUf());
		
	}
	
	public void empresaSelecionada(SelectEvent event) 
	{
		String nomeEmpresa = this.empresa.getRazaoSocial();
		this.empresa = new Empresa();
		
		try
		{
			this.empresa = this.empresaRN.empresaPorNome(nomeEmpresa);
			this.cep = this.empresa.getCep();
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
		}
		
	}
	//FIM MÉTODOS EMPRESA==========================================================================================
	
	//MÉTODOS PARA O BEAN SITE=====================================================================================
	public String salvarSite()
	{
		this.site.setDataHoraReg(new Date());
		this.site.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
				
		this.siteRN.salvar(this.site);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Site Registrado com sucesso!", ""));
		
		this.cep = "";
		this.site = new Site();
		
		return null;
	}
	
	public void buscarEnderecoSite(ActionEvent actionEvent) throws MalformedURLException, ServiceException, SQLException, SigepClienteException, RemoteException
	{
		AtendeClienteServiceLocator serviceCorreios = new AtendeClienteServiceLocator();
		AtendeCliente atendeCliente = serviceCorreios.getAtendeClientePort(
				new URL("https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl"));
		EnderecoERP correios = new EnderecoERP();
		correios = atendeCliente.consultaCEP(this.cep);
		
		this.cep = correios.getCep();
		this.site.setEndereco(correios.getEnd());
		this.site.setBairro(correios.getBairro());
		this.site.setCidade(correios.getCidade());
		this.site.setUf(correios.getUf());
		
	}
	
	public void siteSelecionado(SelectEvent event) 
	{
		String siteIdAtual = this.site.getIdCodAtual();
		this.site = new Site();
		
		try
		{
			this.site = this.siteRN.sitePorIdCodAtual(siteIdAtual);
			this.cep = this.site.getCep();
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
		}
		
	}
	//FIM MÉTODOS SITE=============================================================================================
	
	//MÉTODOS PARA O FORM SITECHAVE================================================================================
	public String inserirSite()
	{
		
		
		return null;
	}
	
	
	public void atribuirSite(CloseEvent event)
	{
		recuperarId();
		
		for(int y = 0; y < this.listaChaves.size(); y++)
		{
			this.listaChaves.get(y).setIdControleChave(this.controleChave);
			this.chaveRN.salvar(this.listaChaves.get(y));
		}
		
		ListaSiteChave siteChave = new ListaSiteChave();
		siteChave.setSiteIdCodAtual(this.site.getIdCodAtual());
		
		List<Chave> chaveSite  = new ArrayList<Chave>();
		chaveSite = this.chaveRN.listaPorSiteStatusTrue(this.site , this.controleChave);
		
		System.out.println("Chaves Size: " + chaveSite.size() + "\nID Requisicao: " + this.controleChave.getId());
		
		String chavesConcatenadas = "";
		for(int x = 0; x < chaveSite.size(); x++)
		{
			chavesConcatenadas = chavesConcatenadas + chaveSite.get(x).getNome();
			
			if(x < chaveSite.size() - 1)
			{
				chavesConcatenadas = chavesConcatenadas + ", ";
			}
			
			siteChave.setListaChaves(chavesConcatenadas);
		}
		
		this.site = this.siteRN.sitePorIdCodAtual(this.site.getIdCodAtual());
		this.controleChave.getIdSite().add(this.site.getIdCodAtual());
		
		this.listaSiteChave.add(siteChave);
		this.siteChaveRequisitado = true;
		
		this.listaChaves.clear();
		this.site = new Site();
	}
	
	public void atribuirNovoSite(SelectEvent event)
	{
		this.listaChaves.clear();
		System.out.println("========>teste de execução");
	}
	
	public void siteRequisitado(SelectEvent event)
	{
		String siteIdAtual = this.site.getIdCodAtual();
		this.site = new Site();
		boolean consultaSite = false;
		try
		{
			this.site = this.siteRN.sitePorIdCodAtual(siteIdAtual);
			this.formSiteChave = true;
			consultaSite = true;
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
		}
		
		if(consultaSite == true)
		{
			this.listaChaves = this.chaveRN.listaPorSiteStatusFalse(this.site);
			this.totalListaChaves = this.listaChaves.size();
		}
		
	}
	
	public String salvarChave()
	{
		this.chave.setStatus(false);
		this.chave.setIdSite(this.site);
		this.chave.setDataHoraReg(new Date());
		this.chave.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		
		this.chaveRN.salvar(this.chave);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Chave Registrada com sucesso!", ""));
		
		this.listaChaves.add(this.chave);
		this.chave = new Chave();
		
		return null;
	}
	
	public String statusChave()
	{
		if(this.chave.isStatus())
		{
			this.chave.setStatus(false);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Chave Não Selecionada!", ""));
		}
		else
		{
			this.chave.setStatus(true);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Chave Selecionada!", ""));
		}
		
		this.chaveRN.salvar(this.chave);
		
		
		
		return null;
	}
	//FIM MÉTIDOS SITECHAVE========================================================================================
	
	private String formatYear(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		
		return dateFormat.format(data);
	}
	
	public ControleChave getControleChave() {
		return controleChave;
	}
	public void setControleChave(ControleChave controleChave) {
		this.controleChave = controleChave;
	}

	public boolean isFormControleChave() {
		return formControleChave;
	}

	public void setFormControleChave(boolean formControleChave) {
		this.formControleChave = formControleChave;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public TecnicoRN getTecnicoRN() {
		return tecnicoRN;
	}

	public void setTecnicoRN(TecnicoRN tecnicoRN) {
		this.tecnicoRN = tecnicoRN;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public EmpresaRN getEmpresaRN() {
		return empresaRN;
	}

	public void setEmpresaRN(EmpresaRN empresaRN) {
		this.empresaRN = empresaRN;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public SiteRN getSiteRN() {
		return siteRN;
	}

	public void setSiteRN(SiteRN siteRN) {
		this.siteRN = siteRN;
	}

	public boolean isFormSiteChave() {
		return formSiteChave;
	}

	public void setFormSiteChave(boolean formSiteChave) {
		this.formSiteChave = formSiteChave;
	}

	public Chave getChave() {
		return chave;
	}

	public void setChave(Chave chave) {
		this.chave = chave;
	}

	public ChaveRN getChaveRN() {
		return chaveRN;
	}

	public void setChaveRN(ChaveRN chaveRN) {
		this.chaveRN = chaveRN;
	}

	public List<Chave> getListaChaves() {
		return listaChaves;
	}

	public void setListaChaves(List<Chave> listaChaves) {
		this.listaChaves = listaChaves;
	}

	public int getTotalListaChaves() {
		return totalListaChaves;
	}

	public void setTotalListaChaves(int totalListaChaves) {
		this.totalListaChaves = totalListaChaves;
	}

	public List<Site> getListaSites() {
		return listaSites;
	}

	public void setListaSites(List<Site> listaSites) {
		this.listaSites = listaSites;
	}

	public List<ListaSiteChave> getListaSiteChave() {
		return listaSiteChave;
	}

	public void setListaSiteChave(List<ListaSiteChave> listaSiteChave) {
		this.listaSiteChave = listaSiteChave;
	}

	public boolean isSiteChaveRequisitado() {
		return siteChaveRequisitado;
	}

	public void setSiteChaveRequisitado(boolean siteChaveRequisitado) {
		this.siteChaveRequisitado = siteChaveRequisitado;
	}

	public ControleChaveFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(ControleChaveFiltro filtro) {
		this.filtro = filtro;
	}
	
	
}
