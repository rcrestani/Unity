package movimentacao.mov;

import java.util.Date;
import java.util.List;

import movimentacao.cliente.Cliente;

public interface MovDAO 
{
	public void salvar(Mov mov);
	public void excluir(Mov mov);
	public Mov carregar(Integer id);
	public List<Mov> listar();
	public Mov buscaPorTalao(Integer talao);
	public List<Mov> buscarTodosPaginado(MovFiltro filtro);
	public int quantidadeFiltrados(MovFiltro filtro);
	public List<Mov> movFechamento(Date dataInicial , Date dataFinal);
	public List<Mov> movFechamentoCliente(Cliente cliente , Date dataInicial , Date dataFinal);
}
