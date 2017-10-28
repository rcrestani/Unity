package movimentacao.produtoTask;

import java.util.List;

import movimentacao.util.DAOFactory;

public class ProdutoTaskRN 
{
	private ProdutoTaskDAO produtoTaskDAO;
	
	public ProdutoTaskRN()
	{
		this.produtoTaskDAO = DAOFactory.criarProdutoTaskDAO();
	}
	
	public void salvar(ProdutoTask produtoTask)
	{
		this.produtoTaskDAO = DAOFactory.criarProdutoTaskDAO();
		this.produtoTaskDAO.salvar(produtoTask);
	}
	
	public void excluir(ProdutoTask produtoTask)
	{
		this.produtoTaskDAO = DAOFactory.criarProdutoTaskDAO();
		this.produtoTaskDAO.excluir(produtoTask);
	}
	
	public List<ProdutoTask> listar()
	{
		this.produtoTaskDAO = DAOFactory.criarProdutoTaskDAO();
		return this.produtoTaskDAO.listar();
	}
	
	public ProdutoTask carregar(Integer id)
	{
		this.produtoTaskDAO = DAOFactory.criarProdutoTaskDAO();
		return this.produtoTaskDAO.carregar(id);
	}

}
