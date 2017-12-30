package movimentacao.projetoNCE.chave;

import java.util.List;

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

	public ChaveFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(ChaveFiltro filtro) {
		this.filtro = filtro;
	}
	
}
