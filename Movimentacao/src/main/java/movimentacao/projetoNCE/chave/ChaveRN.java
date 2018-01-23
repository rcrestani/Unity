package movimentacao.projetoNCE.chave;

import java.util.List;

import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.site.Site;
import movimentacao.util.DAOFactory;

public class ChaveRN
{
	private ChaveDAO chaveDAO;
	private ChaveFiltro filtro;
	
	public ChaveRN()
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
	}
	
	public void salvar(Chave chave)
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		this.chaveDAO.salvar(chave);
	}
	
	public void excluir(Chave chave)
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		this.chaveDAO.excluir(chave);
	}
	
	public Chave carregar(Integer id)
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		return this.chaveDAO.carregar(id);
	}
	
	public List<Chave> listar()
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		return this.chaveDAO.listar();
	}

	public List<String> completeChave(String text)
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		return this.chaveDAO.completeChave(text);
	}
	
	public Chave chavePorId(Integer id)
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		return this.chaveDAO.chavePorId(id);
	}
	
	public List<Chave> listaPorSiteStatusTrue(Site site)
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		return this.chaveDAO.listaPorSiteStatusTrue(site);
	}
	
	public List<Chave> listaPorSiteSelecaoTrue(Site site , ControleChave controleChave)
	{
		this.chaveDAO = DAOFactory.criarChaveDAO();
		return this.chaveDAO.listaPorSiteSelecaoTrue(site , controleChave);
	}
	public ChaveFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(ChaveFiltro filtro) {
		this.filtro = filtro;
	}
	
}
