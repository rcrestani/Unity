package movimentacao.webservice;

import java.util.Date;

public class AtividadesDoDia 
{
	private String cliente;
	private String acao;
	private Date prazo;
	private float valor;
	private String obs;
	private UsuarioWS usuario;
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Date getPrazo() {
		return prazo;
	}
	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public UsuarioWS getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioWS usuario) {
		this.usuario = usuario;
	}

}
