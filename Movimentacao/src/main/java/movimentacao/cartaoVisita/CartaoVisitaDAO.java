package movimentacao.cartaoVisita;

import java.util.List;

public interface CartaoVisitaDAO 
{
	public void salvar(CartaoVisita cartaoVisita);
	public void excluir(CartaoVisita cartaoVisita);
	public CartaoVisita carregar(Integer id);
	public List<CartaoVisita> listar(CartaoVisitaFiltro filtro);

}
