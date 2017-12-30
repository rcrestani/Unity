package movimentacao.projetoNCE.status;

import java.io.Serializable;
import java.util.Date;

import movimentacao.usuario.Usuario;

public class StatusRequisicaoFiltro implements Serializable
{
	private static final long serialVersionUID = -2491493079369838265L;
	
	private Integer id;
	private Integer idSuperior;
	private String nomeStatus;
	private boolean funcao;
	private Date dataHoraReg;
	private Usuario usuario;
	private String obs;
	
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
	public Integer getIdSuperior() {
		return idSuperior;
	}
	public void setIdSuperior(Integer idSuperior) {
		this.idSuperior = idSuperior;
	}
	public String getNomeStatus() {
		return nomeStatus;
	}
	public void setNomeStatus(String nomeStatus) {
		this.nomeStatus = nomeStatus;
	}
	public boolean isFuncao() {
		return funcao;
	}
	public void setFuncao(boolean funcao) {
		this.funcao = funcao;
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
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ascendente ? 1231 : 1237);
		result = prime * result + ((dataHoraReg == null) ? 0 : dataHoraReg.hashCode());
		result = prime * result + (funcao ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idSuperior == null) ? 0 : idSuperior.hashCode());
		result = prime * result + ((nomeStatus == null) ? 0 : nomeStatus.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + primeiroRegistro;
		result = prime * result + ((propriedadeOrdenacao == null) ? 0 : propriedadeOrdenacao.hashCode());
		result = prime * result + quantidadeRegistros;
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
		StatusRequisicaoFiltro other = (StatusRequisicaoFiltro) obj;
		if (ascendente != other.ascendente)
			return false;
		if (dataHoraReg == null) {
			if (other.dataHoraReg != null)
				return false;
		} else if (!dataHoraReg.equals(other.dataHoraReg))
			return false;
		if (funcao != other.funcao)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idSuperior == null) {
			if (other.idSuperior != null)
				return false;
		} else if (!idSuperior.equals(other.idSuperior))
			return false;
		if (nomeStatus == null) {
			if (other.nomeStatus != null)
				return false;
		} else if (!nomeStatus.equals(other.nomeStatus))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (primeiroRegistro != other.primeiroRegistro)
			return false;
		if (propriedadeOrdenacao == null) {
			if (other.propriedadeOrdenacao != null)
				return false;
		} else if (!propriedadeOrdenacao.equals(other.propriedadeOrdenacao))
			return false;
		if (quantidadeRegistros != other.quantidadeRegistros)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	
}
