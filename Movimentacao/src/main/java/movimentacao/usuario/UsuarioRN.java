package movimentacao.usuario;

import java.util.List;

import movimentacao.util.DAOFactory;

public class UsuarioRN
{
	private UsuarioDAO usuarioDAO;
	private UsuarioFiltro filtro;

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
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.listar();
	}
	
	public List<String> completeNome(String text)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.completeNome(text);
	}
	
	public List<String> completeLogin(String text)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.completeLogin(text);
	}
	
	public List<String> completeEmail(String text)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.completeEmail(text);
	}
	
	public List<String> completeCliente(String text)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.completeCliente(text);
	}
	
	public List<Usuario> buscarTodosPaginado(UsuarioFiltro filtro)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.buscarTodosPaginado(filtro);
	}
	
	public int quantidadeFiltrados(UsuarioFiltro filtro)
	{
		this.usuarioDAO = DAOFactory.criarUsuarioDAO();
		return this.usuarioDAO.quantidadeFiltrados(filtro);
	}

	public UsuarioFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(UsuarioFiltro filtro) {
		this.filtro = filtro;
	}

	
}
