package movimentacao.projetoNCE.site;

import java.util.List;

import movimentacao.util.DAOFactory;

public class SiteRN
{
	private SiteDAO siteDAO;
	private SiteFiltro filtro;
	
	public SiteRN()
	{
		this.siteDAO = DAOFactory.criarSiteDAO();
	}
	
	public void salvar(Site site)
	{
		this.siteDAO = DAOFactory.criarSiteDAO();
		this.siteDAO.salvar(site);
	}
	
	public void excluir(Site site)
	{
		this.siteDAO = DAOFactory.criarSiteDAO();
		this.siteDAO.excluir(site);
	}
	
	public Site carregar(Integer id)
	{
		this.siteDAO = DAOFactory.criarSiteDAO();
		return this.siteDAO.carregar(id);
	}
	
	public List<Site> listar()
	{
		this.siteDAO = DAOFactory.criarSiteDAO();
		return this.siteDAO.listar();
	}

	public List<String> completeSite(String text)
	{
		this.siteDAO = DAOFactory.criarSiteDAO();
		return this.siteDAO.completeSite(text);
	}
	
	public Site sitePorIdCodAtual(String idCodAtual)
	{
		this.siteDAO = DAOFactory.criarSiteDAO();
		return this.siteDAO.sitePorIdCodAtual(idCodAtual);
	}
	
	public SiteFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(SiteFiltro filtro) {
		this.filtro = filtro;
	}
	
}
