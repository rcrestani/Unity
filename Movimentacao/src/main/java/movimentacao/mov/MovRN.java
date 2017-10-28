package movimentacao.mov;

import java.util.Date;
import java.util.List;

import movimentacao.cliente.Cliente;
import movimentacao.util.DAOFactory;

public class MovRN 
{
	private MovDAO movDAO;
	
	public MovRN()
	{
		this.movDAO = DAOFactory.criarMovDAO();
	}
	
	public void salvar(Mov mov)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		this.movDAO.salvar(mov);
	}
	
	public void excluir(Mov mov)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		this.movDAO.excluir(mov);
	}
	
	public Mov carregar(Integer id)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		return this.movDAO.carregar(id);
	}
	
	public List<Mov> listar()
	{
		this.movDAO = DAOFactory.criarMovDAO();
		return this.movDAO.listar();
	}
	
	public Mov buscaPorTalao(Integer talao)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		return this.movDAO.buscaPorTalao(talao);
	}
	
	public List<Mov> buscarTodosPaginado(MovFiltro filtro)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		return this.movDAO.buscarTodosPaginado(filtro);
	}
	
	public int quantidadeFiltrados(MovFiltro filtro)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		return this.movDAO.quantidadeFiltrados(filtro);
	}
	
	public List<Mov> movFechamento(Date dataInicial , Date dataFinal)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		return this.movDAO.movFechamento(dataInicial, dataFinal);
	}
	
	public List<Mov> movFechamentoCliente(Cliente cliente , Date dataInicial , Date dataFinal)
	{
		this.movDAO = DAOFactory.criarMovDAO();
		return this.movDAO.movFechamentoCliente(cliente, dataInicial, dataFinal);
	}

}
