package movimentacao.projetoNCE.site;

import java.io.Serializable;
import java.util.Date;

import movimentacao.projetoNCE.coordenador.Coordenador;
import movimentacao.usuario.Usuario;

public class SiteFiltro implements Serializable
{
	private static final long serialVersionUID = 7127631818087737448L;
	
	private Integer id;
	private String idCodAtual;
	private String idCodAntigo;
	private String risco;
	private Coordenador idCoordenador;
	private String endereco;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	private String obs;
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
	public String getIdCodAtual() {
		return idCodAtual;
	}
	public void setIdCodAtual(String idCodAtual) {
		this.idCodAtual = idCodAtual;
	}
	public String getIdCodAntigo() {
		return idCodAntigo;
	}
	public void setIdCodAntigo(String idCodAntigo) {
		this.idCodAntigo = idCodAntigo;
	}
	public String getRisco() {
		return risco;
	}
	public void setRisco(String risco) {
		this.risco = risco;
	}
	public Coordenador getIdCoordenador() {
		return idCoordenador;
	}
	public void setIdCoordenador(Coordenador idCoordenador) {
		this.idCoordenador = idCoordenador;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
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
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((dataHoraReg == null) ? 0 : dataHoraReg.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCodAntigo == null) ? 0 : idCodAntigo.hashCode());
		result = prime * result + ((idCodAtual == null) ? 0 : idCodAtual.hashCode());
		result = prime * result + ((idCoordenador == null) ? 0 : idCoordenador.hashCode());
		result = prime * result + ((obs == null) ? 0 : obs.hashCode());
		result = prime * result + primeiroRegistro;
		result = prime * result + ((propriedadeOrdenacao == null) ? 0 : propriedadeOrdenacao.hashCode());
		result = prime * result + quantidadeRegistros;
		result = prime * result + ((risco == null) ? 0 : risco.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
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
		SiteFiltro other = (SiteFiltro) obj;
		if (ascendente != other.ascendente)
			return false;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (dataHoraReg == null) {
			if (other.dataHoraReg != null)
				return false;
		} else if (!dataHoraReg.equals(other.dataHoraReg))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCodAntigo == null) {
			if (other.idCodAntigo != null)
				return false;
		} else if (!idCodAntigo.equals(other.idCodAntigo))
			return false;
		if (idCodAtual == null) {
			if (other.idCodAtual != null)
				return false;
		} else if (!idCodAtual.equals(other.idCodAtual))
			return false;
		if (idCoordenador == null) {
			if (other.idCoordenador != null)
				return false;
		} else if (!idCoordenador.equals(other.idCoordenador))
			return false;
		if (obs == null) {
			if (other.obs != null)
				return false;
		} else if (!obs.equals(other.obs))
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
		if (risco == null) {
			if (other.risco != null)
				return false;
		} else if (!risco.equals(other.risco))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}
