package movimentacao.usuario;

import java.util.List;

import org.primefaces.model.SortOrder;

import movimentacao.util.DAOFactory;

public class UsuarioRN{
	private UsuarioDAO usuarioDAO;

	public UsuarioRN() 
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
	}

	public void salvar(Usuario usuario) 
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		this.usuarioDAO.salvar(usuario);
	}
	
	public void atualizar(Usuario usuario) 
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		this.usuarioDAO.atualizar(usuario);
	}
	
	public void atualizarEvict(Usuario usuario)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		this.usuarioDAO.atualizarEvict(usuario);
	}

	public void excluir(Usuario usuario) 
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		this.usuarioDAO.excluir(usuario);
	}
	
	public Usuario carregar(Integer codigo) 
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.carregar(codigo);
	}

	public Usuario buscarPorLogin(String login) 
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.buscarPorLogin(login);
	}
	
	public Usuario buscarPorNome(String nome)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.buscarPorNome(nome);
	}
	
	public List<String> completeText(String query)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.completeText(query);
	}
	
	public List<Usuario> listar()
	{
		return this.usuarioDAO.listar();
	}
	
	public int pegarQuantidadeDeUsuarios()
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.pegarQuantidadeDeUsuarios();
	}
	
	public List<Usuario> buscarTodosPaginado(int first, int pageSize, String sortField, SortOrder sortOrder)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.buscarTodosPaginado(first , pageSize, sortField, sortOrder);
	}
	

}
