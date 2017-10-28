package movimentacao.produto;

import java.util.List;
import movimentacao.util.DAOFactory;

public class ProdutoRN 
{
	private ProdutoDAO produtoDAO;
	private ProdutoFiltro filtro;
	
	public void setFiltro(ProdutoFiltro filtro) {
		this.filtro = filtro;
	}


	public ProdutoFiltro getFiltro() {
		return filtro;
	}


	public ProdutoRN()
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
	}
	
	public void salvar(Produto produto)
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		this.produtoDAO.salvar(produto);
	}
	
	public void excluir(Produto produto)
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		this.produtoDAO.excluir(produto);
	}
	
	public Produto carregar(Integer codigo)
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		return this.produtoDAO.carregar(codigo);
	}
	
	public Produto buscarPorDescricao(String descricao)
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		return this.produtoDAO.buscarPorDescricao(descricao);
	}
	
	public List<String> completeText(String query)
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		return this.produtoDAO.completeText(query);
	}
	
	public List<Produto> listar()
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		return this.produtoDAO.listar();
	}
	
	public List<Produto> buscarTodosPaginado(ProdutoFiltro filtro)
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		return this.produtoDAO.buscarTodosPaginado(filtro);
	}
	
	public int quantidadeFiltrados(ProdutoFiltro filtro)
	{
		this.produtoDAO = DAOFactory.criarProdutoDAO();
		return this.produtoDAO.quantidadeFiltrados(filtro);
	}
	

}
