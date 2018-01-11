package movimentacao.usuario;

import java.io.*;
import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@Entity (name="usuario")
public class Usuario implements Serializable
{
	
	private static final long serialVersionUID = -7531887679145652696L;
	
	
	@Id
	@GeneratedValue
	private Integer codigo;
	private String nome;
	@NaturalId (mutable = true)
	private String login;
	private String senha;
	private String email;
	private Integer telegramId;
	private String paginaInicial;
	private boolean ativo;
	
	@ElementCollection(targetClass = String.class)
	@JoinTable(
			name="usuario_permissao",
			uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario" , "permissao"})},
			joinColumns = @JoinColumn(name = "usuario"))
	@Column(name = "permissao" , length=50)
	private Set<String> permissao = new HashSet<String>();
	
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<String> getPermissao() {
		return permissao;
	}
	public void setPermissao(Set<String> permissao) {
		this.permissao = permissao;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Integer getTelegramId() {
		return telegramId;
	}
	public void setTelegramId(Integer telegramId) {
		this.telegramId = telegramId;
	}
	
	public String getPaginaInicial() {
		return paginaInicial;
	}
	public void setPaginaInicial(String paginaInicial) {
		this.paginaInicial = paginaInicial;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((paginaInicial == null) ? 0 : paginaInicial.hashCode());
		result = prime * result + ((permissao == null) ? 0 : permissao.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((telegramId == null) ? 0 : telegramId.hashCode());
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
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (paginaInicial == null) {
			if (other.paginaInicial != null)
				return false;
		} else if (!paginaInicial.equals(other.paginaInicial))
			return false;
		if (permissao == null) {
			if (other.permissao != null)
				return false;
		} else if (!permissao.equals(other.permissao))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (telegramId == null) {
			if (other.telegramId != null)
				return false;
		} else if (!telegramId.equals(other.telegramId))
			return false;
		return true;
	}
	
}
