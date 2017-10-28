package movimentacao.relatorios;

import java.util.Date;

public class NegociosSemAtividades implements Comparable<NegociosSemAtividades>
{
	
	private String statusNegocio;
	private String nomeNegocio;
	private float valorNegocio;
	private Date prazoAtividade;
	private String acaoAtividade;
	private String obsAtividade;
	private String responsavel;
	
	//GETTERS AND SETTERS===============================
	public String getStatusNegocio() {
		return statusNegocio;
	}

	public void setStatusNegocio(String statusNegocio) {
		this.statusNegocio = statusNegocio;
	}

	public String getNomeNegocio() {
		return nomeNegocio;
	}

	public void setNomeNegocio(String nomeNegocio) {
		this.nomeNegocio = nomeNegocio;
	}

	public float getValorNegocio() {
		return valorNegocio;
	}

	public void setValorNegocio(float valorNegocio) {
		this.valorNegocio = valorNegocio;
	}

	public Date getPrazoAtividade() {
		return prazoAtividade;
	}

	public void setPrazoAtividade(Date prazoAtividade) {
		this.prazoAtividade = prazoAtividade;
	}

	public String getAcaoAtividade() {
		return acaoAtividade;
	}

	public void setAcaoAtividade(String acaoAtividade) {
		this.acaoAtividade = acaoAtividade;
	}

	public String getObsAtividade() {
		return obsAtividade;
	}

	public void setObsAtividade(String obsAtividade) {
		this.obsAtividade = obsAtividade;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	@Override
	public int compareTo(NegociosSemAtividades o) 
	{
		return this.getPrazoAtividade().compareTo(o.getPrazoAtividade());
	}
	
}
