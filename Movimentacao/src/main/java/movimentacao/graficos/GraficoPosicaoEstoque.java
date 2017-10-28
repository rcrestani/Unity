package movimentacao.graficos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import movimentacao.produto.Produto;
import movimentacao.produto.ProdutoRN;

@ManagedBean (name = "graficoEstoque")
@RequestScoped
public class GraficoPosicaoEstoque 
{
	private BarChartModel colunaEstoque;
	private ProdutoRN produtoRN = new ProdutoRN();
	
	public GraficoPosicaoEstoque()
	{
		List<Produto> produto = produtoRN.listar();
		this.colunaEstoque = new BarChartModel();
		
		for(int x = 0; x < produto.size(); x++)
		{
			if(produto.get(x).getQtde() > 0 && !produto.get(x).getDescricao().equals("CD DE INSTALACAO DEGGY") && !produto.get(x).getDescricao().equals("FONTE VEICULAR"))
			{
				ChartSeries item = new ChartSeries();
				item.setLabel(produto.get(x).getDescricao());
				item.set(produto.get(x).getDescricao(), produto.get(x).getQtde());
				this.colunaEstoque.addSeries(item);
			}
		}
		
		this.colunaEstoque.setTitle("Posição Estoque em " + formatDate(new Date()));
		this.colunaEstoque.setLegendCols(produto.size());
		this.colunaEstoque.setLegendPosition("n");
		this.colunaEstoque.setAnimate(true);

	}

	public BarChartModel getColunaEstoque() {
		return colunaEstoque;
	}
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(data);
	}
}
