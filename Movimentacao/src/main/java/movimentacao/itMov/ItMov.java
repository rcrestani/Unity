package movimentacao.itMov;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import movimentacao.mov.Mov;
import movimentacao.produto.Produto;

@Entity (name="itmov")
public class ItMov implements Serializable
{
	private static final long serialVersionUID = 7204545269859451732L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn (name = "id_mov")
	private Mov id_mov;
	
	@ManyToOne
	@JoinColumn (name = "cod_prod")
	private Produto codProd;
	
	private String numeracao;
	private String tipoMovi;
	private Integer qtde;
	private float vUnit;
	
	//Getters and setters===========================================================
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Mov getId_mov() {
		return id_mov;
	}
	public void setId_mov(Mov id_mov) {
		this.id_mov = id_mov;
	}
	public Produto getCodProd() {
		return codProd;
	}
	public void setCodProd(Produto codProd) {
		this.codProd = codProd;
	}
	public String getNumeracao() {
		return numeracao;
	}
	public void setNumeracao(String numeracao) {
		this.numeracao = numeracao;
	}
	public String getTipoMovi() {
		return tipoMovi;
	}
	public void setTipoMovi(String tipoMovi) {
		this.tipoMovi = tipoMovi;
	}
	public Integer getQtde() {
		return qtde;
	}
	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
	public float getvUnit() {
		return vUnit;
	}
	public void setvUnit(float vUnit) {
		this.vUnit = vUnit;
	}
	
	//HashCode and equals================================================================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codProd == null) ? 0 : codProd.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((id_mov == null) ? 0 : id_mov.hashCode());
		result = prime * result + ((numeracao == null) ? 0 : numeracao.hashCode());
		result = prime * result + ((qtde == null) ? 0 : qtde.hashCode());
		result = prime * result + ((tipoMovi == null) ? 0 : tipoMovi.hashCode());
		result = prime * result + Float.floatToIntBits(vUnit);
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
		ItMov other = (ItMov) obj;
		if (codProd == null) {
			if (other.codProd != null)
				return false;
		} else if (!codProd.equals(other.codProd))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (id_mov == null) {
			if (other.id_mov != null)
				return false;
		} else if (!id_mov.equals(other.id_mov))
			return false;
		if (numeracao == null) {
			if (other.numeracao != null)
				return false;
		} else if (!numeracao.equals(other.numeracao))
			return false;
		if (qtde == null) {
			if (other.qtde != null)
				return false;
		} else if (!qtde.equals(other.qtde))
			return false;
		if (tipoMovi == null) {
			if (other.tipoMovi != null)
				return false;
		} else if (!tipoMovi.equals(other.tipoMovi))
			return false;
		if (Float.floatToIntBits(vUnit) != Float.floatToIntBits(other.vUnit))
			return false;
		return true;
	}
	
	

}
