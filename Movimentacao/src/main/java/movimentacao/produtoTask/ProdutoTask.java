package movimentacao.produtoTask;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.UniqueConstraint;

@Entity (name="produtoTask")
public class ProdutoTask implements Serializable
{
	private static final long serialVersionUID = -520194083900051912L;
	
	@Id
	@GeneratedValue
	private Integer id;
	private String nomeRelatorio;
	private Integer hora;
	private String emails;
	private boolean status;
	
	@ElementCollection(targetClass = String.class)
	@JoinTable(
			name="produtoTask_DiasDaSemana",
			uniqueConstraints = {@UniqueConstraint(columnNames = {"produtoTask" , "diasDaSemana"})},
			joinColumns = @JoinColumn(name = "produtoTask"))
	@Column(name = "diasDaSemana" , length=50)
	private Set<String> diasDaSemana = new HashSet<String>();

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeRelatorio() {
		return nomeRelatorio;
	}
	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}
	public Integer getHora() {
		return hora;
	}
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<String> getDiasDaSemana() {
		return diasDaSemana;
	}
	public void setDiasDaSemana(Set<String> diasDaSemana) {
		this.diasDaSemana = diasDaSemana;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diasDaSemana == null) ? 0 : diasDaSemana.hashCode());
		result = prime * result + ((emails == null) ? 0 : emails.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeRelatorio == null) ? 0 : nomeRelatorio.hashCode());
		result = prime * result + (status ? 1231 : 1237);
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
		ProdutoTask other = (ProdutoTask) obj;
		if (diasDaSemana == null) {
			if (other.diasDaSemana != null)
				return false;
		} else if (!diasDaSemana.equals(other.diasDaSemana))
			return false;
		if (emails == null) {
			if (other.emails != null)
				return false;
		} else if (!emails.equals(other.emails))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeRelatorio == null) {
			if (other.nomeRelatorio != null)
				return false;
		} else if (!nomeRelatorio.equals(other.nomeRelatorio))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
}
