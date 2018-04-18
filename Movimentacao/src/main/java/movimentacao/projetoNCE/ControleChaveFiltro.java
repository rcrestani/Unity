package movimentacao.projetoNCE;

import java.io.Serializable;
import java.util.Date;

import movimentacao.projetoNCE.status.StatusRequisicao;
import movimentacao.projetoNCE.tecnico.Tecnico;
import movimentacao.usuario.Usuario;

public class ControleChaveFiltro implements Serializable
{
	private static final long serialVersionUID = 7415970195105227610L;
	
	private Integer id;
	private String idAno;
	private Date dataAbertura;
	private Date dataFechamento;
	private String projeto;
	private String crq;
	private Tecnico idTecnico;
	private StatusRequisicao status;
	private String statusFechamento;
	private String obs;
	private Date dataHoraReg;
	private Usuario usuario;
	
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
	public String getIdAno() {
		return idAno;
	}
	public void setIdAno(String idAno) {
		this.idAno = idAno;
	}
	public Date getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public Date getDataFechamento() {
		return dataFechamento;
	}
	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	public String getProjeto() {
		return projeto;
	}
	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}
	public String getCrq() {
		return crq;
	}
	public void setCrq(String crq) {
		this.crq = crq;
	}
	public Tecnico getIdTecnico() {
		return idTecnico;
	}
	public void setIdTecnico(Tecnico idTecnico) {
		this.idTecnico = idTecnico;
	}
	public StatusRequisicao getStatus() {
		return status;
	}
	public void setStatus(StatusRequisicao status) {
		this.status = status;
	}
	public String getStatusFechamento() {
		return statusFechamento;
	}
	public void setStatusFechamento(String statusFechamento) {
		this.statusFechamento = statusFechamento;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Date getDataHoraReg() {
		return dataHoraReg;
	}
	public void setDataHoraReg(Date dataHoraReg) {
		this.dataHoraReg = dataHoraReg;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
