package movimentacao.projetoNCE.tecnico;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.usuario.Usuario;

@Entity (name = "nce_tecnico")
public class Tecnico implements Serializable
{
	private static final long serialVersionUID = -816774017028281701L;

	@Id
	@GeneratedValue
	private Integer id;
	private String nome;
	
	@ManyToOne
	@JoinColumn (name = "idEmpresa")
	private Empresa idEmpresa;
	
	private String foneFixo1;
	private String celular;
	private String rg;
	@NaturalId (mutable = true)
	@Column (length = 14)
	private String cpf;
	private String re;
	private Date tempBlock;
	private boolean status;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public Date getTempBlock() {
		return tempBlock;
	}

	public void setTempBlock(Date tempBlock) {
		this.tempBlock = tempBlock;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataHoraReg == null) ? 0 : dataHoraReg.hashCode());
		result = prime * result + ((foneFixo1 == null) ? 0 : foneFixo1.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((re == null) ? 0 : re.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((tempBlock == null) ? 0 : tempBlock.hashCode());
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
		Tecnico other = (Tecnico) obj;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataHoraReg == null) {
			if (other.dataHoraReg != null)
				return false;
		} else if (!dataHoraReg.equals(other.dataHoraReg))
			return false;
		if (foneFixo1 == null) {
			if (other.foneFixo1 != null)
				return false;
		} else if (!foneFixo1.equals(other.foneFixo1))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idEmpresa == null) {
			if (other.idEmpresa != null)
				return false;
		} else if (!idEmpresa.equals(other.idEmpresa))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (re == null) {
			if (other.re != null)
				return false;
		} else if (!re.equals(other.re))
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (status != other.status)
			return false;
		if (tempBlock == null) {
			if (other.tempBlock != null)
				return false;
		} else if (!tempBlock.equals(other.tempBlock))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
}
