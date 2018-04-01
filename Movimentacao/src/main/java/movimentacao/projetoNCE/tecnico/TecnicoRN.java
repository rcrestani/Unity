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
	
	public void atualizarEvict(Tecnico tecnico)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		this.tecnicoDAO.atualizarEvict(tecnico);
	}
	
	public void excluir(Tecnico tecnico)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		this.tecnicoDAO.excluir(tecnico);
	}
	
	public void alterarStatus(Integer id , boolean status)
	{
		Tecnico tecnico = new Tecnico();
		tecnico = carregar(id);
		tecnico.setStatus(status);
		
		salvar(tecnico);
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
	
	public List<String> completeNome(String text)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.completeNome(text);
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
	
	public List<Tecnico> buscarTodosPaginado(TecnicoFiltro filtro)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.buscarTodosPaginado(filtro);
	}
	
	public int quantidadeFiltrados(TecnicoFiltro filtro)
	{
		this.tecnicoDAO = DAOFactory.criarTecnicoDAO();
		return this.tecnicoDAO.quantidadeFiltrados(filtro);
	}
	
	public TecnicoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(TecnicoFiltro filtro) {
		this.filtro = filtro;
	}

}
