package movimentacao.web;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import movimentacao.projetoNCE.chave.Chave;
import movimentacao.projetoNCE.chave.ChaveRN;
import movimentacao.projetoNCE.site.SiteRN;

@ManagedBean (name = "nceChavesBean")
@ViewScoped
public class NceChavesBean implements Serializable
{
	private static final long serialVersionUID = 3080652540707636009L;
	
	private Chave chave = new Chave();
	private ChaveRN chaveRN = new ChaveRN();
	private SiteRN siteRN = new SiteRN();
	private List<String> listaSites;
	
	public String novo()
	{
		this.chave = new Chave();
		
		return null;
	}
	
	public String testeSites()
	{
		listaSites.forEach(System.out::println);
		
		return null;
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
	
}
