package movimentacao.graficos;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import movimentacao.itMov.ItMovRN;

@ManagedBean (name = "pizzaTipoMovi")
@RequestScoped
public class PizzaTipoMovimentacao 
{
	private PieChartModel tipoMovi;
	private ItMovRN itMovRN = new ItMovRN();
	
	public PizzaTipoMovimentacao()
	{
		this.tipoMovi = new PieChartModel();
		
		this.tipoMovi.set("Entrada", itMovRN.tipoMov("entrada").size());
		this.tipoMovi.set("Saída", itMovRN.tipoMov("saida").size());
		this.tipoMovi.set("Devolução", itMovRN.tipoMov("devolucao").size());
		this.tipoMovi.set("Manutenção", itMovRN.tipoMov("manutencao").size());
		this.tipoMovi.setTitle("Percentual por Tipo de Movimentação");
		this.tipoMovi.setLegendPosition("e");
		this.tipoMovi.setShowDataLabels(true);
		this.tipoMovi.setDataFormat("percent");
	}

	public PieChartModel getTipoMovi() {
		return tipoMovi;
	}
	
	

}
