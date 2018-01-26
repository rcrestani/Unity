package movimentacao.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import movimentacao.cliente.ClienteRN;
import movimentacao.usuario.LazyUsuarioDataModel;
import movimentacao.usuario.Usuario;
import movimentacao.usuario.UsuarioFiltro;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	private ClienteRN clienteRN  = new ClienteRN();
	private String senhaAtual;
	private String senhaNova;
	private String confirmarSenha;
	private List<String> permissoesSource= new ArrayList<String>();
	private List<String> permissoesTarget = new ArrayList<String>();
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	
	private LazyUsuarioDataModel lazyUsuario;
	private UsuarioFiltro filtro = new UsuarioFiltro();
	
	public LazyUsuarioDataModel getLazyUsuario() {
		return lazyUsuario;
	}
	
	@PostConstruct
	public void init()
	{
		this.lazyUsuario = new LazyUsuarioDataModel(usuarioRN , filtro);
	}
	
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
				for(int x = 0; x < this.permissoesTarget.size(); x++)
				{
					this.usuario.getPermissao().add(this.permissoesTarget.get(x));
				}
				this.usuario.setAtivo(true);
				
				try
				{
					userRN.salvar(this.usuario);
					this.usuario = new Usuario();
				}
				catch (Exception e)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Usuário inválido!" , ""));
				}
				
				
				return "/restrito/usuario.jsf";
			}
		}
		else
		{
			if(senhaAtual.equals(this.usuario.getSenha()))
			{				
				Usuario newUser = usuarioRN.carregar(this.usuario.getCodigo());
				//Limpando a lista de permissoes para atualizar com os novos valores===========
				newUser.getPermissao().clear();
				newUser.getPermissao().add("ROLE_USUARIO");
				for(int x = 0; x < this.permissoesTarget.size(); x++)
				{
					newUser.getPermissao().add(this.permissoesTarget.get(x));
				}
				//=============================================================================
				this.usuario.setPermissao(newUser.getPermissao());
				
				try
				{
					usuarioRN.atualizarEvict(newUser);
					usuarioRN.atualizar(this.usuario);
				}
				catch (ConstraintViolationException e)
				{
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Usuário inválido!" , ""));
				}
				
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
					//Limpando a lista de permissoes para atualizar com os novos valores===========
					newUser.getPermissao().clear();
					newUser.getPermissao().add("ROLE_USUARIO");
					for(int x = 0; x < this.permissoesTarget.size(); x++)
					{
						newUser.getPermissao().add(this.permissoesTarget.get(x));
					}
					//==============================================================================
					this.usuario.setPermissao(newUser.getPermissao());
					
					try
					{
						usuarioRN.atualizarEvict(newUser);
						usuarioRN.atualizar(this.usuario);
					}
					catch (ConstraintViolationException e)
					{
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Usuário inválido!" , ""));
					}
					
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
		
		this.permissoesTarget.clear();
		this.permissoesSource.clear();
		this.permissoesSource.add("ROLE_ADM");
		this.permissoesSource.add("ROLE_DGR");
		this.permissoesSource.add("ROLE_CONTROLE_FROTA");
		this.permissoesSource.add("ROLE_CONTROLE_CHAVE");
		
		return null;
	}
	
	public String editar()
	{
		this.confirmarSenha = this.usuario.getSenha();
		this.senhaAtual = this.confirmarSenha;
		
		this.permissoesSource.clear();
		this.permissoesSource.add("ROLE_ADM");
		this.permissoesSource.add("ROLE_DGR");
		this.permissoesSource.add("ROLE_CONTROLE_FROTA");
		this.permissoesSource.add("ROLE_CONTROLE_CHAVE");
		
		//UsuarioRN usuarioRN = new UsuarioRN();
	    this.usuario = this.usuarioRN.carregar(this.usuario.getCodigo());
		System.out.println("USUARIO: " + this.usuario.getNome());
	    this.permissoesTarget.addAll(this.usuario.getPermissao());
		
	    for(int x = 0; x < this.permissoesTarget.size(); x++)
	    {
	    	if(this.permissoesTarget.get(x).equals("ROLE_USUARIO"))
	    	{
	    		this.permissoesTarget.remove(x);
	    	}
	    }
	    
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
		
		usuarioRN.atualizar(this.usuario);
		//this.usuario = new Usuario();
		
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

	public List<String> getPermissoesSource() {
		return permissoesSource;
	}

	public void setPermissoesSource(List<String> permissoesSource) {
		this.permissoesSource = permissoesSource;
	}

	public List<String> getPermissoesTarget() {
		return permissoesTarget;
	}

	public void setPermissoesTarget(List<String> permissoesTarget) {
		this.permissoesTarget = permissoesTarget;
	}

	public UsuarioFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(UsuarioFiltro filtro) {
		this.filtro = filtro;
	}

	public UsuarioRN getUsuarioRN() {
		return usuarioRN;
	}

	public void setUsuarioRN(UsuarioRN usuarioRN) {
		this.usuarioRN = usuarioRN;
	}

	public ClienteRN getClienteRN() {
		return clienteRN;
	}

	public void setClienteRN(ClienteRN clienteRN) {
		this.clienteRN = clienteRN;
	}

}
