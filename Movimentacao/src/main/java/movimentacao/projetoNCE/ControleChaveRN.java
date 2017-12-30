package movimentacao.projetoNCE;

import java.util.List;

import movimentacao.util.DAOFactory;

public class ControleChaveRN
{
	private ControleChaveDAO controleChaveDAO;
	private ControleChaveFiltro filtro;
	
	public ControleChaveRN()
	{
		this.controleChaveDAO = DAOFactory.criarControleChaveDAO();
	}

	public void salvar(ControleChave controleChave)
	{
		this.controleChaveDAO = DAOFactory.criarControleChaveDAO();
		this.controleChaveDAO.salvar(controleChave);
	}
	
	public void excluir(ControleChave controleChave)
	{
		this.controleChaveDAO = DAOFactory.criarControleChaveDAO();
		this.controleChaveDAO.excluir(controleChave);
	}
	
	public ControleChave carregar(Integer id)
	{
		this.controleChaveDAO = DAOFactory.criarControleChaveDAO();
		return this.controleChaveDAO.carregar(id);
	}
	public List<ControleChave> listar()
	{
		this.controleChaveDAO = DAOFactory.criarControleChaveDAO();
		return this.controleChaveDAO.listar();
	}
	
	public ControleChaveFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(ControleChaveFiltro filtro) {
		this.filtro = filtro;
	}
}
