package movimentacao.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import movimentacao.usuario.LazyUsuarioDataModel;
import movimentacao.usuario.Usuario;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import movimentacao.usuario.UsuarioRN;


@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable
{
	
	private static final long serialVersionUID = 6679679633405104457L;
	private Usuario usuario = new Usuario();
	private UsuarioRN usuarioRN = new UsuarioRN();
	private String senhaAtual;
	private String senhaNova;
	private String confirmarSenha;
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	
	private LazyUsuarioDataModel lazyUsuario;
	
	public LazyUsuarioDataModel getLazyUsuario() {
		return lazyUsuario;
	}
	
	@PostConstruct
	public void init()
	{
		lazyUsuario = new LazyUsuarioDataModel(new UsuarioRN());
	}
	
	
	/*public String novo() { 
		this.usuario = new Usuario();
		return "/publico/usuario.jsf"; 
	}*/
	

	public String salvar() 
	{ 
		UsuarioRN userRN = new UsuarioRN();
		String senha = this.usuario.getSenha();
		
		if(this.usuario.getCodigo() == null)
		{
			if (!senha.equals(this.confirmarSenha)) 
			{ 
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Senha não confere!" , ""));
			}
			else
			{
				String bCrypt = passwordEncoder.encode(senha);
				this.usuario.setSenha(bCrypt);
				this.usuario.getPermissao().add("ROLE_USUARIO");
				this.usuario.setAtivo(true);
				userRN.salvar(this.usuario);
				
				return "/restrito/usuario.jsf";
			}
		}
		else
		{
			if(senhaAtual.equals(this.usuario.getSenha()))
			{				
				Usuario newUser = usuarioRN.carregar(this.usuario.getCodigo());
				this.usuario.setPermissao(newUser.getPermissao());
				usuarioRN.atualizarEvict(newUser);
				usuarioRN.atualizar(this.usuario);
				this.usuario = new Usuario();
				
				return "/restrito/usuario.jsf";
			}
			else
			{
				if(this.usuario.getSenha().equals(this.confirmarSenha))
				{
					String bCrypt = passwordEncoder.encode(this.confirmarSenha);
					this.usuario.setSenha(bCrypt);
					
					Usuario newUser = usuarioRN.carregar(this.usuario.getCodigo());
					this.usuario.setPermissao(newUser.getPermissao());
					usuarioRN.atualizarEvict(newUser);
					usuarioRN.atualizar(this.usuario);
					this.usuario = new Usuario();
					
					return "/restrito/usuario.jsf";
				}
				else
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Senha não confere!" , ""));
				}
				
			}
			
		}
		
		return null;
		
	}
	
	public void excluir(Usuario usuario) 
	{
		this.usuarioRN.excluir(usuario);
		this.lazyUsuario = null;
	}
	
	public String novo()
	{
		this.usuario = new Usuario();
		this.confirmarSenha = "";
		
		return null;
	}
	
	public String editar()
	{
		this.confirmarSenha = this.usuario.getSenha();
		this.senhaAtual = this.confirmarSenha;
				
		return null;
	}
	
	public String status()
	{
		if(this.usuario.isAtivo())
		{
			this.usuario.setAtivo(false);
		}
		else
		{
			this.usuario.setAtivo(true);
		}
		
		Usuario newUser = usuarioRN.carregar(this.usuario.getCodigo());
		this.usuario.setPermissao(newUser.getPermissao());
		usuarioRN.atualizarEvict(newUser);
		usuarioRN.atualizar(this.usuario);
		this.usuario = new Usuario();
		
		return null;
	}
	
	public String trocarSenha()
	{
		this.usuario = this.usuarioRN.buscarPorLogin(login);	
		
		
		//Este if utiliza o metodo matches para comparar a senha do BD com a senha digitada no form
		//pois o bcrypt gera novas criptografias mesmo se tiver senhas repetidas no BD
		//Exemplo: senha 1322 ficou diferente em três usuários
		if(passwordEncoder.matches(senhaAtual, this.usuario.getSenha()))
		{
			if(senhaNova.equals(confirmarSenha))
			{
				try
				{
					this.usuario.setSenha(passwordEncoder.encode(senhaNova));
					this.usuarioRN.atualizar(this.usuario);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_INFO, "Senha alterada com sucesso!" , ""));
				}
				catch (Exception e)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Motivo: " + e.getMessage(), ""));
				}
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Senha Nova não confere!" , ""));
			}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Senha Atual não confere!" , ""));
		}
		return "/restrito/trocaSenha.jsf";
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}
	
	

}
