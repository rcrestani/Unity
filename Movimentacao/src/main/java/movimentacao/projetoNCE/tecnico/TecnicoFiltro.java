package movimentacao.projetoNCE.tecnico;

import java.io.Serializable;
import java.util.Date;

import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.usuario.Usuario;

public class TecnicoFiltro implements Serializable
{
	private static final long serialVersionUID = -5147038257919904345L;
	
	private Integer id;
	private String nome;
	private Empresa idEmpresa;
	private String foneFixo1;
	private String foneFixo2;
	private String rg;
	private String re;
	private Date dataHoraReg;
	private Usuario usuario;
	
	private int primeiroRegistro;
	private int quantidadeRegistros;
	private String propriedadeOrdenacao;
	private boolean ascendente;
	
	
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
	public String getFoneFixo2() {
		return foneFixo2;
	}
	public void setFoneFixo2(String foneFixo2) {
		this.foneFixo2 = foneFixo2;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getRe() {
		return re;
	}
	public void setRe(String re) {
		this.re = re;
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
	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}
	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}
	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}
	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}
	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}
	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}
	public boolean isAscendente() {
		return ascendente;
	}
	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ascendente ? 1231 : 1237);
		result = prime * result + ((dataHoraReg == null) ? 0 : dataHoraReg.hashCode());
		result = prime * result + ((foneFixo1 == null) ? 0 : foneFixo1.hashCode());
		result = prime * result + ((foneFixo2 == null) ? 0 : foneFixo2.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + primeiroRegistro;
		result = prime * result + ((propriedadeOrdenacao == null) ? 0 : propriedadeOrdenacao.hashCode());
		result = prime * result + quantidadeRegistros;
		result = prime * result + ((re == null) ? 0 : re.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
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
		TecnicoFiltro other = (TecnicoFiltro) obj;
		if (ascendente != other.ascendente)
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
		if (foneFixo2 == null) {
			if (other.foneFixo2 != null)
				return false;
		} else if (!foneFixo2.equals(other.foneFixo2))
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
		if (primeiroRegistro != other.primeiroRegistro)
			return false;
		if (propriedadeOrdenacao == null) {
			if (other.propriedadeOrdenacao != null)
				return false;
		} else if (!propriedadeOrdenacao.equals(other.propriedadeOrdenacao))
			return false;
		if (quantidadeRegistros != other.quantidadeRegistros)
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
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
}
