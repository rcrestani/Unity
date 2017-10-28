package movimentacao.webservice;

import java.util.Date;

import movimentacao.negocios.Negocios;
import movimentacao.usuario.Usuario;


public class Atividades
{
	private Integer id;
	private String tipoAtividade;
	private String obs;
	private Date data;
	private Date prazo;
	private Usuario usuario;
	private boolean finalizado;
	private Negocios negocios;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipoAtividade() {
		return tipoAtividade;
	}
	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getPrazo() {
		return prazo;
	}
	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Negocios getNegocios() {
		return negocios;
	}
	public void setNegocios(Negocios negocios) {
		this.negocios = negocios;
	}
	public boolean isFinalizado() {
		return finalizado;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	
}
