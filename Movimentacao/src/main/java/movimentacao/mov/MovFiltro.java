package movimentacao.mov;

import java.io.Serializable;
import java.util.Date;

public class MovFiltro implements Serializable
{
	private static final long serialVersionUID = 2254994948203778469L;
	
	private Date inicial;
	private Date fim;
	private Integer talao;
	private String cliente;
	private String tipoMovi;
	
	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	
	
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
	public Integer getTalao() {
		return talao;
	}
	public void setTalao(Integer talao) {
		this.talao = talao;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getTipoMovi() {
		return tipoMovi;
	}
	public void setTipoMovi(String tipoMovi) {
		this.tipoMovi = tipoMovi;
	}
	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}
	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}
	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}
	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}
	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}
	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}
	public boolean isAscendente() {
		return ascendente;
	}
	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
	
}
