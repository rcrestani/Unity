package movimentacao.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import movimentacao.controleAcesso.LazyLogAcessoDataModel;
import movimentacao.controleAcesso.LogAcesso;
import movimentacao.controleAcesso.LogAcessoFiltro;
import movimentacao.controleAcesso.LogAcessoRN;
import movimentacao.usuario.UsuarioRN;
import movimentacao.util.DateCalculator;

@ManagedBean(name = "logAcessoBean")
@ViewScoped
public class LogAcessoBean 
{
	private LogAcesso logAcesso = new LogAcesso();
	private LogAcessoRN logAcessoRN = new LogAcessoRN();
	private LogAcessoFiltro filtro = new LogAcessoFiltro();
	private Calendar dataHora = new GregorianCalendar();
	private DateCalculator dateCalculator = new DateCalculator();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	
	private LazyLogAcessoDataModel lazyLogAcesso;
	
	public LazyLogAcessoDataModel getLazyLogAcesso() {
		return lazyLogAcesso;
	}

	@PostConstruct
	public void init()
	{
		this.lazyLogAcesso = new LazyLogAcessoDataModel(this.logAcessoRN, this.filtro);
	}
	
	public void registrarLog() throws IOException
	{
		UsuarioRN usuarioRN = new UsuarioRN();
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		logAcesso = logAcessoRN.buscaPorSessionId(sessionId);

		if (logAcesso == null)
		{
			LogAcesso logAcessoNew = new LogAcesso();
			logAcessoNew.setUsuario(login);
			logAcessoNew.setSessionId(sessionId);
			logAcessoNew.setDataLogin(dataHora.getTime());
			
			logAcessoRN.salvar(logAcessoNew);
			
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/restrito/" + usuarioRN.buscarPorLogin(login).getPaginaInicial());
		}
		else
		{
			boolean x = sessionId.equals(logAcesso.getSessionId());
			
			if(x == false)
			{
				LogAcesso logAcessoNew = new LogAcesso();
				logAcessoNew.setUsuario(login);
				logAcessoNew.setSessionId(sessionId);
				logAcessoNew.setDataLogin(dataHora.getTime());
				
				logAcessoRN.salvar(logAcessoNew);
				FacesContext.getCurrentInstance().getExternalContext().redirect("/restrito/" + usuarioRN.buscarPorLogin(login).getPaginaInicial());
			}
		}
		
	}
	
	public void registrarLogout() throws IOException
	{		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		logAcesso = logAcessoRN.buscaPorSessionId(sessionId);
		
		logAcesso.setDataLogout(new Date());		
		
		logAcesso.setTempoSessao(dateCalculator.calculaHoras(logAcesso.getDataLogin(), logAcesso.getDataLogout()));
		
		logAcessoRN.salvar(logAcesso);
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("j_spring_security_logout");
	}

	public LogAcessoRN getLogAcessoRN() {
		return logAcessoRN;
	}

	public void setLogAcessoRN(LogAcessoRN logAcessoRN) {
		this.logAcessoRN = logAcessoRN;
	}

	public LogAcessoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(LogAcessoFiltro filtro) {
		this.filtro = filtro;
	}

}
