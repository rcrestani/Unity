package movimentacao.cartaoVisita;

import java.io.Serializable;

public class CartaoVisitaFiltro implements Serializable
{
	private static final long serialVersionUID = -2588406561105105073L;
	
	private Integer id;
	private String nome;
	private String empresa;
	private String ordenacao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getOrdenacao() {
		return ordenacao;
	}
	public void setOrdenacao(String ordenacao) {
		this.ordenacao = ordenacao;
	}
	
}
