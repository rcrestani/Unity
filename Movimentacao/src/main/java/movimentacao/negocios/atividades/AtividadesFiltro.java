package movimentacao.negocios.atividades;

import java.io.Serializable;
import java.util.Date;

import movimentacao.negocios.Negocios;
import movimentacao.usuario.Usuario;

public class AtividadesFiltro implements Serializable
{
	private static final long serialVersionUID = 8011130674462377621L;
	
	private String tipoAtividade;
	private Date data;
	private Date inicial;
	private Date fim;
	private Date prazo;
	private Usuario usuario;
	private boolean finalizado;
	private String status;
	private Negocios negocios;
	private String cliente;
	private String ordenacao;
	
	public String getTipoAtividade() {
		return tipoAtividade;
	}
	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
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
	public boolean isFinalizado() {
		return finalizado;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	public Negocios getNegocios() {
		return negocios;
	}
	public void setNegocios(Negocios negocios) {
		this.negocios = negocios;
	}
	public Date getInicial() {
		return inicial;
	}
	public void setInicial(Date inicial) {
		this.inicial = inicial;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrdenacao() {
		return ordenacao;
	}
	public void setOrdenacao(String ordenacao) {
		this.ordenacao = ordenacao;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	
}
