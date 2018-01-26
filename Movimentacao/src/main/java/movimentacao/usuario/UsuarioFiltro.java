package movimentacao.usuario;

import java.io.Serializable;

public class UsuarioFiltro implements Serializable
{
	private static final long serialVersionUID = -495584394135988888L;
	
	private String nome;
	private String login;
	private String email;
	private String cliente;
	private String status;
	
	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
