package movimentacao.projetoNCE.chave;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.site.Site;
import movimentacao.usuario.Usuario;

@Entity (name = "nce_chave")
public class Chave implements Serializable
{
	private static final long serialVersionUID = 3835131282554243180L;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn (name = "IdSite")
	private Site idSite;
	
	@ManyToOne
	@JoinColumn (name = "IdControleChave")
	private ControleChave idControleChave;
	
	private String nome;
	private String tipo;
	private String claviculario;
	private String posicao;
	private boolean selecao;
	private boolean transito;
	private boolean status;
	private String obs;
	private Date dataHoraReg;
	
	@ManyToOne
	@JoinColumn (name = "idUsuario")
	private Usuario usuario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Site getIdSite() {
		return idSite;
	}

	public void setIdSite(Site idSite) {
		this.idSite = idSite;
	}

	public ControleChave getIdControleChave() {
		return idControleChave;
	}

	public void setIdControleChave(ControleChave idControleChave) {
		this.idControleChave = idControleChave;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getClaviculario() {
		return claviculario;
	}

	public void setClaviculario(String claviculario) {
		this.claviculario = claviculario;
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public boolean isSelecao() {
		return selecao;
	}

	public void setSelecao(boolean selecao) {
		this.selecao = selecao;
	}

	public boolean isTransito() {
		return transito;
	}

	public void setTransito(boolean transito) {
		this.transito = transito;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claviculario == null) ? 0 : claviculario.hashCode());
		result = prime * result + ((dataHoraReg == null) ? 0 : dataHoraReg.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idControleChave == null) ? 0 : idControleChave.hashCode());
		result = prime * result + ((idSite == null) ? 0 : idSite.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + ((posicao == null) ? 0 : posicao.hashCode());
		result = prime * result + (selecao ? 1231 : 1237);
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + (transito ? 1231 : 1237);
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
		Chave other = (Chave) obj;
		if (claviculario == null) {
			if (other.claviculario != null)
				return false;
		} else if (!claviculario.equals(other.claviculario))
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
		if (idControleChave == null) {
			if (other.idControleChave != null)
				return false;
		} else if (!idControleChave.equals(other.idControleChave))
			return false;
		if (idSite == null) {
			if (other.idSite != null)
				return false;
		} else if (!idSite.equals(other.idSite))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (posicao == null) {
			if (other.posicao != null)
				return false;
		} else if (!posicao.equals(other.posicao))
			return false;
		if (selecao != other.selecao)
			return false;
		if (status != other.status)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (transito != other.transito)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}


}
