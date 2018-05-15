package movimentacao.projetoNCE;

import java.io.Serializable;
import java.util.Date;

public class ControleChaveProtocolo implements Serializable
{
	//Classe Bean para gerar o protocolo em PDF via JasperReport
	private static final long serialVersionUID = 3537217283149746365L;
	
	private String idAno;
	private Date data;
	private String tecnico;
	private String empresa;
	private String rg;
	private String celular;
	private String site;
	private String chave;
	private String crq;
	private int totalChaves;
	private String usuarioAtendimento;
	private String horaAbertura;
	private String horaAtendimento;
	
	public String getIdAno() {
		return idAno;
	}
	public void setIdAno(String idAno) {
		this.idAno = idAno;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTecnico() {
		return tecnico;
	}
	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getCrq() {
		return crq;
	}
	public void setCrq(String crq) {
		this.crq = crq;
	}
	public int getTotalChaves() {
		return totalChaves;
	}
	public void setTotalChaves(int totalChaves) {
		this.totalChaves = totalChaves;
	}
	public String getUsuarioAtendimento() {
		return usuarioAtendimento;
	}
	public void setUsuarioAtendimento(String usuarioAtendimento) {
		this.usuarioAtendimento = usuarioAtendimento;
	}
	public String getHoraAbertura() {
		return horaAbertura;
	}
	public void setHoraAbertura(String horaAbertura) {
		this.horaAbertura = horaAbertura;
	}
	public String getHoraAtendimento() {
		return horaAtendimento;
	}
	public void setHoraAtendimento(String horaAtendimento) {
		this.horaAtendimento = horaAtendimento;
	}
	
	
}
