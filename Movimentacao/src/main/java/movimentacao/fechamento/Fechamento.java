package movimentacao.fechamento;

import java.io.Serializable;
import java.util.Date;

import movimentacao.cliente.Cliente;

public class Fechamento implements Serializable
{
	private static final long serialVersionUID = 7425160789287563223L;
	
	private Date data;
	private int talao;
	private String cliente;
	private String tipoMovi;
	private float totais;
	private Date dataInicial;
	private Date dataFinal;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getTalao() {
		return talao;
	}
	public void setTalao(int talao) {
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
	public float getTotais() {
		return totais;
	}
	public void setTotais(float totais) {
		this.totais = totais;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	

}
