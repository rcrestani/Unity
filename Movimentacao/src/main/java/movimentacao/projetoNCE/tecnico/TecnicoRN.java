package movimentacao.projetoNCE.tecnico;

import java.util.List;

import movimentacao.util.DAOFactory;

public class TecnicoRN
{
	private TecnicoDAO tecnicoDAO;
	private TecnicoFiltro filtro;
	
	public TecnicoRN()
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
	}
	
	public void salvar(Tecnico tecnico)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		this.tecnicoDAO.salvar(tecnico);
	}
	
	public void excluir(Tecnico tecnico)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		this.tecnicoDAO.excluir(tecnico);
	}
	
	public Tecnico carregar (Integer id)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.carregar(id);
	}
	
	public List<Tecnico> listar()
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.listar();
	}
	
	public TecnicoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(TecnicoFiltro filtro) {
		this.filtro = filtro;
	}
	
	

}
