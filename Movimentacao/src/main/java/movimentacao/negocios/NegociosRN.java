package movimentacao.negocios;

import java.util.List;

import org.hibernate.Criteria;

import movimentacao.cliente.Cliente;
import movimentacao.util.DAOFactory;

public class NegociosRN 
{
	private NegociosDAO negociosDAO;
		
	public NegociosRN()
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
	}
	
	public void salvar(Negocios negocios)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		this.negociosDAO.salvar(negocios);
	}
	
	public void excluir(Negocios negocios)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		this.negociosDAO.excluir(negocios);
	}
	
	public Negocios carregar(Integer codigo)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.carregar(codigo);
	}
	
	public Negocios buscaPorCliente(Cliente cliente)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.buscaPorCliente(cliente);
	}
	
	public List<Negocios> listar()
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.listar();
	}
	
	public List<Negocios> negociosPreVenda()
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.negociosPreVenda();
	}
	
	public List<Negocios> buscarTodosPaginado(NegociosFiltro filtro)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.buscarTodosPaginado(filtro);
	}
	public int quantidadeFiltrados(NegociosFiltro filtro)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.quantidadeFiltrados(filtro);
	}
	
	public Criteria criarCriteriaParaFiltro(NegociosFiltro filtro)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.criarCriteriaParaFiltro(filtro);
	}
	
	public List<String> negociosPorCliente(String query)
	{
		this.negociosDAO = DAOFactory.criarNegociosDAO();
		return this.negociosDAO.negociosPorCliente(query);
	}
}
