package movimentacao.cliente;

import java.util.List;

public interface ClienteDAO 
{
	public void salvar(Cliente cliente);
	public void excluir(Cliente cliente);
	public Cliente carregar(Integer codigo);
	public Cliente buscarPorNome(String nome);
	public List<String> completeText(String query);
	public List<Cliente> listar();
	public List<Cliente> buscarTodosPaginado(ClienteFiltro filtro);
	public int quantidadeFiltrados(ClienteFiltro filtro);
	public List<String> buscaPorExcecao(String cliente);
	public List<Cliente> buscaPorParticipacao(String filtro);
}
