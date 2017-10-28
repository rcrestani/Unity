package movimentacao.produtoTask;

import java.util.List;

public interface ProdutoTaskDAO 
{
	public void salvar(ProdutoTask produtoTask);
	public void excluir(ProdutoTask produtoTask);
	public List<ProdutoTask> listar();
	public ProdutoTask carregar(Integer id);
}
