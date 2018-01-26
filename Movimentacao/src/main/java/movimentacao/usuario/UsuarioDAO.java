package movimentacao.usuario;

import java.util.List;

public interface UsuarioDAO 
{
	public void salvar(Usuario usuario);
	public void atualizar(Usuario usuario);
	public void atualizarEvict(Usuario usuario);
	public void excluir(Usuario usuario);
	public Usuario carregar(Integer codigo);
	public List<String> completeText(String query);
	public Usuario buscarPorLogin(String login);
	public Usuario buscarPorNome(String nome);
	public List<String> completeNome(String text);
	public List<String> completeLogin(String text);
	public List<String> completeEmail(String text);
	public List<String> completeCliente(String text);
	public List<Usuario> listar();
	public List<Usuario> buscarTodosPaginado(UsuarioFiltro filtro);
	public int quantidadeFiltrados(UsuarioFiltro filtro);

}
