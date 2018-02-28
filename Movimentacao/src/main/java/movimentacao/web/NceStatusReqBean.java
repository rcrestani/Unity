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

import movimentacao.projetoNCE.status.StatusRequisicao;
import movimentacao.projetoNCE.status.StatusRequisicaoRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean(name = "nceStatusBean")
@ViewScoped
public class NceStatusReqBean implements Serializable
{
	private static final long serialVersionUID = -422655574621873465L;
	
	private StatusRequisicao status = new StatusRequisicao();
	private StatusRequisicaoRN statusRN = new StatusRequisicaoRN();
	private List<StatusRequisicao> listaStatus = new ArrayList<StatusRequisicao>();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	private UsuarioRN usuarioRN = new UsuarioRN();
	
	@PostConstruct
	public void init()
	{
		this.listaStatus = this.statusRN.listar();
	}
	
	public String salvar()
	{
		try
		{
			this.status.setDataHoraReg(new Date());
			this.status.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
			this.statusRN.salvar(status);
			
			this.listaStatus = this.statusRN.listar();
			
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
	
	public String novo()
	{
		this.status = new StatusRequisicao();
		
		return null;
	}
	
	public StatusRequisicao getStatus() {
		return status;
	}
	public void setStatus(StatusRequisicao status) {
		this.status = status;
	}
	public StatusRequisicaoRN getStatusRN() {
		return statusRN;
	}
	public void setStatusRN(StatusRequisicaoRN statusRN) {
		this.statusRN = statusRN;
	}
	public List<StatusRequisicao> getListaStatus() {
		return listaStatus;
	}
	public void setListaStatus(List<StatusRequisicao> listaStatus) {
		this.listaStatus = listaStatus;
	}
	
	
	
}
