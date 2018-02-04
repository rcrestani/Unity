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
	
	public Tecnico tecnicoPorCPF(String cpf)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.tecnicoPorCPF(cpf);
	}
	
	public List<Tecnico> listar()
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.listar();
	}
	
	public List<String> completeTecnico(String text)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.completeTecnico(text);
	}
	
	public Tecnico tecnicoPorNome(String nome)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.tecnicoPorNome(nome);
	}
	
	public TecnicoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(TecnicoFiltro filtro) {
		this.filtro = filtro;
	}

}
