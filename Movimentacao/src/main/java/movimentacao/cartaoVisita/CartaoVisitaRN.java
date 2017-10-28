package movimentacao.cartaoVisita;

import java.util.List;

import movimentacao.util.DAOFactory;

public class CartaoVisitaRN 
{
	private CartaoVisitaDAO cartaoVisitaDAO;
	
	public CartaoVisitaRN()
	{
		this.cartaoVisitaDAO = DAOFactory.criarCartaoVisitaDAO();
	}
	
	public void salvar(CartaoVisita cartaoVisita)
	{
		this.cartaoVisitaDAO = DAOFactory.criarCartaoVisitaDAO();
		this.cartaoVisitaDAO.salvar(cartaoVisita);
	}
	
	public void excluir(CartaoVisita cartaoVisita)
	{
		this.cartaoVisitaDAO = DAOFactory.criarCartaoVisitaDAO();
		this.cartaoVisitaDAO.excluir(cartaoVisita);
	}
	
	public CartaoVisita carregar(Integer id)
	{
		this.cartaoVisitaDAO = DAOFactory.criarCartaoVisitaDAO();
		
		return this.cartaoVisitaDAO.carregar(id);
	}
	
	public List<CartaoVisita> listar(CartaoVisitaFiltro filtro)
	{
		this.cartaoVisitaDAO = DAOFactory.criarCartaoVisitaDAO();
		
		return this.cartaoVisitaDAO.listar(filtro);
	}
}
