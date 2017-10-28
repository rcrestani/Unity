package movimentacao.produto;

import java.util.List;

public interface ProdutoDAO 
{
	public void salvar(Produto produto);
	public void excluir(Produto produto);
	public Produto carregar(Integer codigo);
	public Produto buscarPorDescricao(String descricao);
	public List<String> completeText(String query);
	public List<Produto> listar();
	public List<Produto> buscarTodosPaginado(ProdutoFiltro filtro);
	public int quantidadeFiltrados(ProdutoFiltro filtro);

}
