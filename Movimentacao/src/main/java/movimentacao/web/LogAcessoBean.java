package movimentacao.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import movimentacao.controleAcesso.LogAcesso;
import movimentacao.controleAcesso.LogAcessoRN;

@ManagedBean(name = "logAcessoBean")
@RequestScoped
public class LogAcessoBean 
{
	private LogAcesso logAcesso = new LogAcesso();
	private LogAcessoRN logAcessoRN = new LogAcessoRN();
	private Calendar dataHora = new GregorianCalendar();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	
	public void registrarLog()
	{
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
			}
		}
		
	}
	
	public void registrarLogout() throws IOException
	{		
		FacesContext fCtx = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
		String sessionId = session.getId();
		
		logAcesso = logAcessoRN.buscaPorSessionId(sessionId);
		
		logAcesso.setDataLogout(dataHora.getTime());		
		
		logAcessoRN.salvar(logAcesso);
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("j_spring_security_logout");
	}

}
