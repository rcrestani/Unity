package movimentacao.mov;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.persistence.GeneratedValue;

import movimentacao.cliente.Cliente;
import movimentacao.usuario.Usuario;

@Entity (name="mov")
public class Mov implements Serializable
{
	private static final long serialVersionUID = -382722272490557991L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn (name="cod_cliente")
	private Cliente cliente;
	
	private String posto;
	private String obs;
	
	@ManyToOne
	@JoinColumn (name = "cod_usuario")
	private Usuario usuario;
	
	@Temporal (TemporalType.DATE)
	private Date data;
	
	private String autorizado;
	private String recebido;
	private Integer talao;
	
	//Getters and setters===============================================================
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getPosto() {
		return posto;
	}
	public void setPosto(String posto) {
		this.posto = posto;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getAutorizado() {
		return autorizado;
	}
	public void setAutorizado(String autorizado) {
		this.autorizado = autorizado;
	}
	public String getRecebido() {
		return recebido;
	}
	public void setRecebido(String recebido) {
		this.recebido = recebido;
	}
	
	public Integer getTalao() {
		return talao;
	}
	public void setTalao(Integer talao) {
		this.talao = talao;
	}
	
	//Hashcode and equals======================================================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autorizado == null) ? 0 : autorizado.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + ((posto == null) ? 0 : posto.hashCode());
		result = prime * result + ((recebido == null) ? 0 : recebido.hashCode());
		result = prime * result + ((talao == null) ? 0 : talao.hashCode());
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
		Mov other = (Mov) obj;
		if (autorizado == null) {
			if (other.autorizado != null)
				return false;
		} else if (!autorizado.equals(other.autorizado))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
			return false;
		if (posto == null) {
			if (other.posto != null)
				return false;
		} else if (!posto.equals(other.posto))
			return false;
		if (recebido == null) {
			if (other.recebido != null)
				return false;
		} else if (!recebido.equals(other.recebido))
			return false;
		if (talao == null) {
			if (other.talao != null)
				return false;
		} else if (!talao.equals(other.talao))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	

}
