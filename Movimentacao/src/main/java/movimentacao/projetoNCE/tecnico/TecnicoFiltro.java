package movimentacao.projetoNCE.tecnico;

import java.io.Serializable;
import java.util.Date;

import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.usuario.Usuario;

public class TecnicoFiltro implements Serializable
{
	private static final long serialVersionUID = -5147038257919904345L;
	
	private Integer id;
	private String nome;
	private Empresa idEmpresa;
	private String foneFixo1;
	private String foneFixo2;
	private String rg;
	private String re;
	private Date inicio;
	private Date fim;
	private Usuario usuario;
	private Date tempBlock;
	private String status;
	
	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	
	
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
	public Empresa getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Empresa idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getFoneFixo1() {
		return foneFixo1;
	}
	public void setFoneFixo1(String foneFixo1) {
		this.foneFixo1 = foneFixo1;
	}
	public String getFoneFixo2() {
		return foneFixo2;
	}
	public void setFoneFixo2(String foneFixo2) {
		this.foneFixo2 = foneFixo2;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getRe() {
		return re;
	}
	public void setRe(String re) {
		this.re = re;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getTempBlock() {
		return tempBlock;
	}
	public void setTempBlock(Date tempBlock) {
		this.tempBlock = tempBlock;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
