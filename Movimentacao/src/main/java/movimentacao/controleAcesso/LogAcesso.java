package movimentacao.controleAcesso;

import java.io.*;
import javax.persistence.*;
import java.util.Date;

@Entity (name="logAcesso")
public class LogAcesso implements Serializable
{
	
	private static final long serialVersionUID = 1192496786749737307L;
	
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	private String usuario;
	private Date dataLogin;
	private Date dataLogout;
	private String tempoSessao;
	private String sessionId;
	
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getDataLogin() {
		return dataLogin;
	}
	public void setDataLogin(Date dataLogin) {
		this.dataLogin = dataLogin;
	}
	public Date getDataLogout() {
		return dataLogout;
	}
	public void setDataLogout(Date dataLogout) {
		this.dataLogout = dataLogout;
	}
	public String getTempoSessao() {
		return tempoSessao;
	}
	public void setTempoSessao(String tempoSessao) {
		this.tempoSessao = tempoSessao;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dataLogin == null) ? 0 : dataLogin.hashCode());
		result = prime * result + ((dataLogout == null) ? 0 : dataLogout.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((tempoSessao == null) ? 0 : tempoSessao.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogAcesso other = (LogAcesso) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataLogin == null) {
			if (other.dataLogin != null)
				return false;
		} else if (!dataLogin.equals(other.dataLogin))
			return false;
		if (dataLogout == null) {
			if (other.dataLogout != null)
				return false;
		} else if (!dataLogout.equals(other.dataLogout))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (tempoSessao == null) {
			if (other.tempoSessao != null)
				return false;
		} else if (!tempoSessao.equals(other.tempoSessao))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}
