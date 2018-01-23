package movimentacao.projetoNCE.coordenador;

import java.util.List;

import movimentacao.util.DAOFactory;

public class CoordenadorRN
{
	private CoordenadorDAO coordenadorDAO;
	private CoordenadorFiltro filtro;
	
	public CoordenadorRN()
	{
		this.coordenadorDAO = DAOFactory.criarCoordenadorDAO();
	}
	
	public void salvar(Coordenador coordenador)
	{
		this.coordenadorDAO = DAOFactory.criarCoordenadorDAO();
		this.coordenadorDAO.salvar(coordenador);
	}
	
	public void excluir(Coordenador coordenador)
	{
		this.coordenadorDAO = DAOFactory.criarCoordenadorDAO();
		this.coordenadorDAO.excluir(coordenador);
	}
	
	public Coordenador carregar(Integer id)
	{
		this.coordenadorDAO = DAOFactory.criarCoordenadorDAO();
		return this.coordenadorDAO.carregar(id);
	}
	
	public List<Coordenador> listar()
	{
		this.coordenadorDAO = DAOFactory.criarCoordenadorDAO();
		return this.coordenadorDAO.listar();
	}
	
	public CoordenadorFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(CoordenadorFiltro filtro) {
		this.filtro = filtro;
	}
	
}
