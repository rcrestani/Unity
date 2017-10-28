package movimentacao.usuario;

import java.util.List;

import org.primefaces.model.SortOrder;

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
	public List<Usuario> listar();
	public List<Usuario> buscarTodosPaginado(int first, int pageSize, String sortField, SortOrder sortOrder);
	public int pegarQuantidadeDeUsuarios();

}
