package movimentacao.cliente;

import java.util.List;
import movimentacao.util.DAOFactory;

public class ClienteRN 
{
	private ClienteDAO clienteDAO;
	private ClienteFiltro filtro;
	
	public void setFiltro(ClienteFiltro filtro) {
		this.filtro = filtro;
	}


	public ClienteFiltro getFiltro() {
		return filtro;
	}


	public ClienteRN()
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
	}
	
	
	public void salvar(Cliente cliente)
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
		this.clienteDAO.salvar(cliente);
	}
	
	public void excluir(Cliente cliente)
	{
		this.clienteDAO.excluir(cliente);
	}
	
	
	public Cliente carregar(Integer codigo)
	{
		return this.clienteDAO.carregar(codigo);
	}
	
	
	public Cliente buscarPorNome(String nome)
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
		return this.clienteDAO.buscarPorNome(nome);
	}
	
	public List<String> completeText(String query)
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
		return this.clienteDAO.completeText(query);
	}
	
	public List<Cliente> listar()
	{
		return this.clienteDAO.listar();
	}
	
	public List<Cliente> buscarTodosPaginado(ClienteFiltro filtro)
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
		return this.clienteDAO.buscarTodosPaginado(filtro);
	}
	public int quantidadeFiltrados(ClienteFiltro filtro)
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
		return this.clienteDAO.quantidadeFiltrados(filtro);
	}
	
	public List<String> buscaPorExcecao(String cliente)
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
		return this.clienteDAO.buscaPorExcecao(cliente);
	}
	
	public List<Cliente> buscaPorParticipacao(String filtro)
	{
		this.clienteDAO = DAOFactory.criarClienteDAO();
		return this.clienteDAO.buscaPorParticipacao(filtro);
	}
}
