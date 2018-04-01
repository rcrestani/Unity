package movimentacao.webservice;

import java.util.Date;

public class NceControleChaveWS
{

	private Integer id;
	private String idAno;
	private Date dataAbertura;
	private Date dataAtendimento;
	private Date dataFechamento;
	private String tempoAberto;
	private String atividade;
	private String crq;
	private Integer idTecnico;
	private String nomeTecnico;
	private String cpfTecnico;
	private String celularTecnico;
	private String status;
	private String obs;	
	private UsuarioWS usuarioAbertura;
	private UsuarioWS usuarioAtendimento;
	private UsuarioWS usuarioFechamento;
	
	
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
	public Date getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	public Date getDataFechamento() {
		return dataFechamento;
	}
	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	public String getTempoAberto() {
		return tempoAberto;
	}
	public void setTempoAberto(String tempoAberto) {
		this.tempoAberto = tempoAberto;
	}
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public String getCrq() {
		return crq;
	}
	public void setCrq(String crq) {
		this.crq = crq;
	}
	public Integer getIdTecnico() {
		return idTecnico;
	}
	public void setIdTecnico(Integer idTecnico) {
		this.idTecnico = idTecnico;
	}
	public String getNomeTecnico() {
		return nomeTecnico;
	}
	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}
	public String getCpfTecnico() {
		return cpfTecnico;
	}
	public void setCpfTecnico(String cpfTecnico) {
		this.cpfTecnico = cpfTecnico;
	}
	public String getCelularTecnico() {
		return celularTecnico;
	}
	public void setCelularTecnico(String celularTecnico) {
		this.celularTecnico = celularTecnico;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public UsuarioWS getUsuarioAbertura() {
		return usuarioAbertura;
	}
	public void setUsuarioAbertura(UsuarioWS usuarioAbertura) {
		this.usuarioAbertura = usuarioAbertura;
	}
	public UsuarioWS getUsuarioAtendimento() {
		return usuarioAtendimento;
	}
	public void setUsuarioAtendimento(UsuarioWS usuarioAtendimento) {
		this.usuarioAtendimento = usuarioAtendimento;
	}
	public UsuarioWS getUsuarioFechamento() {
		return usuarioFechamento;
	}
	public void setUsuarioFechamento(UsuarioWS usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atividade == null) ? 0 : atividade.hashCode());
		result = prime * result + ((celularTecnico == null) ? 0 : celularTecnico.hashCode());
		result = prime * result + ((cpfTecnico == null) ? 0 : cpfTecnico.hashCode());
		result = prime * result + ((crq == null) ? 0 : crq.hashCode());
		result = prime * result + ((dataAbertura == null) ? 0 : dataAbertura.hashCode());
		result = prime * result + ((dataAtendimento == null) ? 0 : dataAtendimento.hashCode());
		result = prime * result + ((dataFechamento == null) ? 0 : dataFechamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idAno == null) ? 0 : idAno.hashCode());
		result = prime * result + ((idTecnico == null) ? 0 : idTecnico.hashCode());
		result = prime * result + ((nomeTecnico == null) ? 0 : nomeTecnico.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tempoAberto == null) ? 0 : tempoAberto.hashCode());
		result = prime * result + ((usuarioAbertura == null) ? 0 : usuarioAbertura.hashCode());
		result = prime * result + ((usuarioAtendimento == null) ? 0 : usuarioAtendimento.hashCode());
		result = prime * result + ((usuarioFechamento == null) ? 0 : usuarioFechamento.hashCode());
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
		NceControleChaveWS other = (NceControleChaveWS) obj;
		if (atividade == null) {
			if (other.atividade != null)
				return false;
		} else if (!atividade.equals(other.atividade))
			return false;
		if (celularTecnico == null) {
			if (other.celularTecnico != null)
				return false;
		} else if (!celularTecnico.equals(other.celularTecnico))
			return false;
		if (cpfTecnico == null) {
			if (other.cpfTecnico != null)
				return false;
		} else if (!cpfTecnico.equals(other.cpfTecnico))
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
		if (dataAtendimento == null) {
			if (other.dataAtendimento != null)
				return false;
		} else if (!dataAtendimento.equals(other.dataAtendimento))
			return false;
		if (dataFechamento == null) {
			if (other.dataFechamento != null)
				return false;
		} else if (!dataFechamento.equals(other.dataFechamento))
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
		if (nomeTecnico == null) {
			if (other.nomeTecnico != null)
				return false;
		} else if (!nomeTecnico.equals(other.nomeTecnico))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tempoAberto == null) {
			if (other.tempoAberto != null)
				return false;
		} else if (!tempoAberto.equals(other.tempoAberto))
			return false;
		if (usuarioAbertura == null) {
			if (other.usuarioAbertura != null)
				return false;
		} else if (!usuarioAbertura.equals(other.usuarioAbertura))
			return false;
		if (usuarioAtendimento == null) {
			if (other.usuarioAtendimento != null)
				return false;
		} else if (!usuarioAtendimento.equals(other.usuarioAtendimento))
			return false;
		if (usuarioFechamento == null) {
			if (other.usuarioFechamento != null)
				return false;
		} else if (!usuarioFechamento.equals(other.usuarioFechamento))
			return false;
		return true;
	}
}
