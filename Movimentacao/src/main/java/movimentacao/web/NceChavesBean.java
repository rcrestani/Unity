package movimentacao.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import movimentacao.projetoNCE.chave.Chave;
import movimentacao.projetoNCE.chave.ChaveFiltro;
import movimentacao.projetoNCE.chave.ChaveRN;
import movimentacao.projetoNCE.chave.LazyChaveDataModel;
import movimentacao.projetoNCE.site.SiteRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean (name = "nceChavesBean")
@ViewScoped
public class NceChavesBean implements Serializable
{
	private static final long serialVersionUID = 3080652540707636009L;
	
	private Chave chave = new Chave();
	private ChaveRN chaveRN = new ChaveRN();
	private SiteRN siteRN = new SiteRN();
	private List<String> listaSites = new ArrayList<String>();
	private UsuarioRN usuarioRN = new UsuarioRN();
	//Dados da sessão===================================================
		private FacesContext context = FacesContext.getCurrentInstance();
		private ExternalContext external = context.getExternalContext();
		private String login = external.getRemoteUser();
	//==================================================================
		
	//LazyDataMode======================================================
		private ChaveFiltro filtro = new ChaveFiltro();
		private LazyChaveDataModel lazyChave;
	//==================================================================
		
	
	@PostConstruct
	public void init()
	{
		this.lazyChave = new LazyChaveDataModel(this.chaveRN, this.filtro);
	}
		
		
	public String novo()
	{
		this.chave = new Chave();
		this.listaSites.clear();
		
		return null;
	}
	
	public String salvar()
	{
		//listaSites.forEach(System.out::println);
		this.chave.getIdSite().clear();
		
		if(this.chave.getId() == null)
		{
			this.chave.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
			this.chave.setDataHoraReg(new Date());
		}
		
		for(String sites:listaSites)
		{
			this.chave.getIdSite().add(this.siteRN.sitePorIdCodAtual(sites).getId());
		}
		
		try
		{
			this.chaveRN.salvar(this.chave);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Chave registrada com sucesso!", ""));
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro: " + e.getMessage() + " ao Registrar a chave" + this.chave.getNome(), ""));
		}
		
		
		return null;
	}
	
	public String editar()
	{
		this.listaSites.clear();
		this.chave = this.chaveRN.carregar(this.chave.getId());
		
		List<Integer> listaIdSites = new ArrayList<Integer>();
		listaIdSites.addAll(this.chave.getIdSite());
		
		for(Integer idSites:listaIdSites)
		{
			this.listaSites.add(this.siteRN.carregar(idSites).getIdCodAtual());
		}
		
		return null;
	}
	
	public String liberarChave()
	{
		this.chave.setIdControleChave(null);
		this.chave.setTransito(false);
		
		try
		{
			this.chaveRN.salvar(this.chave);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Chave " + this.chave.getNome() + " liberada com sucesso!", ""));
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao liberar a Chave " + this.chave.getNome(), ""));
		}
		
		
		return null;
	}
	
	public boolean botaoLiberarChave(Chave chave)
	{
		if(chave.isTransito())
		{
			return true;
		}
		else
		{
			return false;
		}
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

	public List<String> getlistaSites() {
		return listaSites;
	}

	public void setlistaSites(List<String> listaSites) {
		this.listaSites = listaSites;
	}

	public SiteRN getSiteRN() {
		return siteRN;
	}

	public void setSiteRN(SiteRN siteRN) {
		this.siteRN = siteRN;
	}


	public UsuarioRN getUsuarioRN() {
		return usuarioRN;
	}


	public void setUsuarioRN(UsuarioRN usuarioRN) {
		this.usuarioRN = usuarioRN;
	}


	public ChaveFiltro getFiltro() {
		return filtro;
	}


	public void setFiltro(ChaveFiltro filtro) {
		this.filtro = filtro;
	}


	public LazyChaveDataModel getLazyChave() {
		return lazyChave;
	}


	public void setLazyChave(LazyChaveDataModel lazyChave) {
		this.lazyChave = lazyChave;
	}
	
}
