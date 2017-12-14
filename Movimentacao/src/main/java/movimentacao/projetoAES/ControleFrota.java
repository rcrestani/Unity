package movimentacao.projetoAES;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity (name="controleFrota")
public class ControleFrota implements Serializable
{
	private static final long serialVersionUID = -8063262161787146823L;
	
	@Id
	@GeneratedValue
	private Integer id;
	private String vtr;
	private String motorista;
	private String coletor;
	private String saida;
	private String chegada;
	private String tempoMissao;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVtr() {
		return vtr;
	}
	public void setVtr(String vtr) {
		this.vtr = vtr;
	}
	public String getMotorista() {
		return motorista;
	}
	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}
	public String getColetor() {
		return coletor;
	}
	public void setColetor(String coletor) {
		this.coletor = coletor;
	}
	public String getSaida() {
		return saida;
	}
	public void setSaida(String saida) {
		this.saida = saida;
	}
	public String getChegada() {
		return chegada;
	}
	public void setChegada(String chegada) {
		this.chegada = chegada;
	}
	public String getTempoMissao() {
		return tempoMissao;
	}
	public void setTempoMissao(String tempoMissao) {
		this.tempoMissao = tempoMissao;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chegada == null) ? 0 : chegada.hashCode());
		result = prime * result + ((coletor == null) ? 0 : coletor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((motorista == null) ? 0 : motorista.hashCode());
		result = prime * result + ((saida == null) ? 0 : saida.hashCode());
		result = prime * result + ((tempoMissao == null) ? 0 : tempoMissao.hashCode());
		result = prime * result + ((vtr == null) ? 0 : vtr.hashCode());
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
		ControleFrota other = (ControleFrota) obj;
		if (chegada == null) {
			if (other.chegada != null)
				return false;
		} else if (!chegada.equals(other.chegada))
			return false;
		if (coletor == null) {
			if (other.coletor != null)
				return false;
		} else if (!coletor.equals(other.coletor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (motorista == null) {
			if (other.motorista != null)
				return false;
		} else if (!motorista.equals(other.motorista))
			return false;
		if (saida == null) {
			if (other.saida != null)
				return false;
		} else if (!saida.equals(other.saida))
			return false;
		if (tempoMissao == null) {
			if (other.tempoMissao != null)
				return false;
		} else if (!tempoMissao.equals(other.tempoMissao))
			return false;
		if (vtr == null) {
			if (other.vtr != null)
				return false;
		} else if (!vtr.equals(other.vtr))
			return false;
		return true;
	}
	
	
}
