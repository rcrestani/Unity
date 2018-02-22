package movimentacao.projetoNCE.controleSiteChave;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import movimentacao.projetoNCE.ControleChave;

@Entity (name = "nce_controleSiteChave")
public class ControleSiteChave implements Serializable
{
	private static final long serialVersionUID = -122740980105538510L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn (name = "idControleChave")
	private ControleChave idReq;
	
	private String siteIdCodAtual;
	private String listaChaves;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ControleChave getIdReq() {
		return idReq;
	}
	public void setIdReq(ControleChave idReq) {
		this.idReq = idReq;
	}
	public String getSiteIdCodAtual() {
		return siteIdCodAtual;
	}
	public void setSiteIdCodAtual(String siteIdCodAtual) {
		this.siteIdCodAtual = siteIdCodAtual;
	}
	public String getListaChaves() {
		return listaChaves;
	}
	public void setListaChaves(String listaChaves) {
		this.listaChaves = listaChaves;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idReq == null) ? 0 : idReq.hashCode());
		result = prime * result + ((listaChaves == null) ? 0 : listaChaves.hashCode());
		result = prime * result + ((siteIdCodAtual == null) ? 0 : siteIdCodAtual.hashCode());
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
		ControleSiteChave other = (ControleSiteChave) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idReq == null) {
			if (other.idReq != null)
				return false;
		} else if (!idReq.equals(other.idReq))
			return false;
		if (listaChaves == null) {
			if (other.listaChaves != null)
				return false;
		} else if (!listaChaves.equals(other.listaChaves))
			return false;
		if (siteIdCodAtual == null) {
			if (other.siteIdCodAtual != null)
				return false;
		} else if (!siteIdCodAtual.equals(other.siteIdCodAtual))
			return false;
		return true;
	}
	
}
