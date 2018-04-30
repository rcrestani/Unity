package movimentacao.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import movimentacao.projetoNCE.emails.NivelEmail;
import movimentacao.projetoNCE.emails.NivelEmailRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean(name = "nceNivelEmailBean")
@ViewScoped
public class NceNivelEmailBean implements Serializable
{
	private static final long serialVersionUID = 9034861444561730283L;
	
	private NivelEmail nivelEmail = new NivelEmail();
	private NivelEmailRN nivelEmailRN = new NivelEmailRN();
	private List<NivelEmail> lista = new ArrayList<NivelEmail>();
	private List<String> emails = new ArrayList<String>();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	private UsuarioRN usuarioRN = new UsuarioRN();
	
	@PostConstruct
	public void init()
	{
		this.lista = this.nivelEmailRN.listar();
	}
	
	public String salvar()
	{
		String aux = this.nivelEmail.getNivel();
		try
		{
			for(int x = 0; x < emails.size(); x++)
			{
				this.nivelEmail.setNivel(aux);
				this.nivelEmail.setEmail(emails.get(x));
				this.nivelEmail.setDataHoraReg(new Date());
				this.nivelEmail.setIdUsuario(this.usuarioRN.buscarPorLogin(this.login));
				this.nivelEmailRN.salvar(this.nivelEmail);
				
				this.nivelEmail = new NivelEmail();
			}
			
			this.lista = this.nivelEmailRN.listar();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Salvo com sucesso!", ""));
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao salvar!", ""));
		}
		
		
		return null;
	}
	
	public String excluir()
	{
		try
		{
			this.nivelEmailRN.excluir(this.nivelEmail);
			
			this.lista = this.nivelEmailRN.listar();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Excluído com sucesso!", ""));
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao excluir!", ""));
		}
		
		
		return null;
	}
	
	public String novo()
	{
		this.nivelEmail = new NivelEmail();
		this.emails.clear();
		
		return null;
	}
	
	public String editar()
	{
		this.emails.clear();
		
		this.emails.add(this.nivelEmail.getEmail());
		
		return null;
	}
	
	public NivelEmail getNivelEmail() {
		return nivelEmail;
	}
	public void setNivelEmail(NivelEmail nivelEmail) {
		this.nivelEmail = nivelEmail;
	}

	public List<NivelEmail> getLista() {
		return lista;
	}

	public void setLista(List<NivelEmail> lista) {
		this.lista = lista;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	
	

}
