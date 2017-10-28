package movimentacao.cartaoVisita;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity (name = "cartaoVisita")
public class CartaoVisita implements Serializable
{
	private static final long serialVersionUID = -2100680725931240152L;
	
	@Id
	@GeneratedValue
	private Integer id;
	private String nome;
	private String empresa;
	private String cargo;
	private String foneFixo;
	private String foneMovel;
	private String email;
	private String origem;
	private String endereco;
	private String cep;
	private String arquivoImagemFrente;
	private String arquivoImagemVerso;
	
	
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
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getFoneFixo() {
		return foneFixo;
	}
	public void setFoneFixo(String foneFixo) {
		this.foneFixo = foneFixo;
	}
	public String getFoneMovel() {
		return foneMovel;
	}
	public void setFoneMovel(String foneMovel) {
		this.foneMovel = foneMovel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getArquivoImagemFrente() {
		return arquivoImagemFrente;
	}
	public void setArquivoImagemFrente(String arquivoImagemFrente) {
		this.arquivoImagemFrente = arquivoImagemFrente;
	}
	public String getArquivoImagemVerso() {
		return arquivoImagemVerso;
	}
	public void setArquivoImagemVerso(String arquivoImagemVerso) {
		this.arquivoImagemVerso = arquivoImagemVerso;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivoImagemFrente == null) ? 0 : arquivoImagemFrente.hashCode());
		result = prime * result + ((arquivoImagemVerso == null) ? 0 : arquivoImagemVerso.hashCode());
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((foneFixo == null) ? 0 : foneFixo.hashCode());
		result = prime * result + ((foneMovel == null) ? 0 : foneMovel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
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
		CartaoVisita other = (CartaoVisita) obj;
		if (arquivoImagemFrente == null) {
			if (other.arquivoImagemFrente != null)
				return false;
		} else if (!arquivoImagemFrente.equals(other.arquivoImagemFrente))
			return false;
		if (arquivoImagemVerso == null) {
			if (other.arquivoImagemVerso != null)
				return false;
		} else if (!arquivoImagemVerso.equals(other.arquivoImagemVerso))
			return false;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (foneFixo == null) {
			if (other.foneFixo != null)
				return false;
		} else if (!foneFixo.equals(other.foneFixo))
			return false;
		if (foneMovel == null) {
			if (other.foneMovel != null)
				return false;
		} else if (!foneMovel.equals(other.foneMovel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		return true;
	}
	
	
}
