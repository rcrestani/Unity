package movimentacao.projetoNCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NaturalId;

import movimentacao.projetoNCE.status.StatusRequisicao;
import movimentacao.projetoNCE.tecnico.Tecnico;
import movimentacao.usuario.Usuario;

@Entity (name = "nce_controleChave")
public class ControleChave implements Serializable
{
	private static final long serialVersionUID = 6168579551301952418L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@NaturalId
	private String idAno;
	
	private Date dataAtendimento;
	private Date dataFechamento;
	private String tempoAberto;
	private String atividade;
	private String crq;
	
	@OneToOne
	@JoinColumn (name = "idTecnico")
	private Tecnico idTecnico;
	
	@OneToOne
	@JoinColumn (name = "idStatus")
	private StatusRequisicao status;
	
	@Column (length = 1000)
	private String obs;
	
	private Date dataAbertura;
	
	@OneToOne
	@JoinColumn (name = "idUsuarioAbertura")
	private Usuario usuarioAbertura;
	
	@OneToOne
	@JoinColumn (name = "idUsuarioAtendimento")
	private Usuario usuarioAtendimento;
	
	@OneToOne
	@JoinColumn (name = "idUsuarioFechamento")
	private Usuario usuarioFechamento;

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

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Usuario getUsuarioAbertura() {
		return usuarioAbertura;
	}

	public void setUsuarioAbertura(Usuario usuarioAbertura) {
		this.usuarioAbertura = usuarioAbertura;
	}

	public Usuario getUsuarioAtendimento() {
		return usuarioAtendimento;
	}

	public void setUsuarioAtendimento(Usuario usuarioAtendimento) {
		this.usuarioAtendimento = usuarioAtendimento;
	}

	public Usuario getUsuarioFechamento() {
		return usuarioFechamento;
	}

	public void setUsuarioFechamento(Usuario usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atividade == null) ? 0 : atividade.hashCode());
		result = prime * result + ((crq == null) ? 0 : crq.hashCode());
		result = prime * result + ((dataAbertura == null) ? 0 : dataAbertura.hashCode());
		result = prime * result + ((dataAtendimento == null) ? 0 : dataAtendimento.hashCode());
		result = prime * result + ((dataFechamento == null) ? 0 : dataFechamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idAno == null) ? 0 : idAno.hashCode());
		result = prime * result + ((idTecnico == null) ? 0 : idTecnico.hashCode());
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
		ControleChave other = (ControleChave) obj;
		if (atividade == null) {
			if (other.atividade != null)
				return false;
		} else if (!atividade.equals(other.atividade))
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
