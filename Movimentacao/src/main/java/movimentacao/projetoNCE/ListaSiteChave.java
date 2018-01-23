package movimentacao.projetoNCE;

import java.io.Serializable;

public class ListaSiteChave implements Serializable
{
	private static final long serialVersionUID = -122740980105538510L;
	
	private String siteIdCodAtual;
	private String listaChaves;
	
	public String getSiteIdCodAtual() {
		return siteIdCodAtual;
	}
	public void setSiteIdCodAtual(String siteIdCodAtual) {
		this.siteIdCodAtual = siteIdCodAtual;
	}
	public String getListaChaves() {
		return listaChaves;
	}
	public void setListaChaves(String listaChaves) {
		this.listaChaves = listaChaves;
	}

}
