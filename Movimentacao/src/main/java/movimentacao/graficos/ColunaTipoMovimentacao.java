package movimentacao.graficos;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import movimentacao.itMov.ItMovRN;

@ManagedBean (name = "graficoColuna")
@RequestScoped
public class ColunaTipoMovimentacao 
{
	private BarChartModel colunaTipoMovi;
	private ItMovRN itMovRN = new ItMovRN();
	
	public ColunaTipoMovimentacao()
	{
		ChartSeries entrada = new ChartSeries();
		ChartSeries saida = new ChartSeries();
		ChartSeries devolucao = new ChartSeries();
		ChartSeries manutencao = new ChartSeries();
		
		this.colunaTipoMovi = new BarChartModel();
		
		entrada.setLabel("Entrada");
		entrada.set("", itMovRN.tipoMov("entrada").size());
		
		saida.setLabel("Saída");
		saida.set("", itMovRN.tipoMov("saida").size());
		
		devolucao.setLabel("Devolução");
		devolucao.set("", itMovRN.tipoMov("devolucao").size());
		
		manutencao.setLabel("Manutenção");
		manutencao.set("", itMovRN.tipoMov("manutencao").size());

		
		this.colunaTipoMovi.addSeries(entrada);
		this.colunaTipoMovi.addSeries(saida);
		this.colunaTipoMovi.addSeries(devolucao);
		this.colunaTipoMovi.addSeries(manutencao);
		this.colunaTipoMovi.setTitle("Quantidades por Tipo de Movimentação");
		this.colunaTipoMovi.setLegendPosition("ne");
		this.colunaTipoMovi.setAnimate(true);
		Axis xAxis = this.colunaTipoMovi.getAxis(AxisType.X);
		xAxis.setLabel("Quantidades por período");
		Axis yAxis = this.colunaTipoMovi.getAxis(AxisType.Y);
		yAxis.setLabel("Volume de Movimentações");
	}

	public BarChartModel getColunaTipoMovi() {
		return colunaTipoMovi;
	}
	
	
}
