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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ascendente ? 1231 : 1237);
		result = prime * result + ((crq == null) ? 0 : crq.hashCode());
		result = prime * result + ((dataAbertura == null) ? 0 : dataAbertura.hashCode());
		result = prime * result + ((dataFechamento == null) ? 0 : dataFechamento.hashCode());
		result = prime * result + ((dataHoraReg == null) ? 0 : dataHoraReg.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idAno == null) ? 0 : idAno.hashCode());
		result = prime * result + ((idTecnico == null) ? 0 : idTecnico.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + primeiroRegistro;
		result = prime * result + ((projeto == null) ? 0 : projeto.hashCode());
		result = prime * result + ((propriedadeOrdenacao == null) ? 0 : propriedadeOrdenacao.hashCode());
		result = prime * result + quantidadeRegistros;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		ControleChaveFiltro other = (ControleChaveFiltro) obj;
		if (ascendente != other.ascendente)
			return false;
		if (crq == null) {
			if (other.crq != null)
				return false;
		} else if (!crq.equals(other.crq))
			return false;
		if (dataAbertura == null) {
			if (other.dataAbertura != null)
				return false;
		} else if (!dataAbertura.equals(other.dataAbertura))
			return false;
		if (dataFechamento == null) {
			if (other.dataFechamento != null)
				return false;
		} else if (!dataFechamento.equals(other.dataFechamento))
			return false;
		if (dataHoraReg == null) {
			if (other.dataHoraReg != null)
				return false;
		} else if (!dataHoraReg.equals(other.dataHoraReg))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idAno == null) {
			if (other.idAno != null)
				return false;
		} else if (!idAno.equals(other.idAno))
			return false;
		if (idTecnico == null) {
			if (other.idTecnico != null)
				return false;
		} else if (!idTecnico.equals(other.idTecnico))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (primeiroRegistro != other.primeiroRegistro)
			return false;
		if (projeto == null) {
			if (other.projeto != null)
				return false;
		} else if (!projeto.equals(other.projeto))
			return false;
		if (propriedadeOrdenacao == null) {
			if (other.propriedadeOrdenacao != null)
				return false;
		} else if (!propriedadeOrdenacao.equals(other.propriedadeOrdenacao))
			return false;
		if (quantidadeRegistros != other.quantidadeRegistros)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
