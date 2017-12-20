package movimentacao.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import movimentacao.projetoAES.ControleFrota;
import movimentacao.projetoAES.ControleFrotaFiltro;
import movimentacao.projetoAES.ControleFrotaRN;
import movimentacao.projetoAES.LazyControleFrotaDataModel;
import movimentacao.util.UtilException;
import movimentacao.web.util.RelatorioUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "controleFrotaBean")
@ViewScoped
public class ControleFrotaBean implements Serializable
{
	private static final long serialVersionUID = 7067955497889472078L;
	
	private ControleFrotaFiltro filtro = new ControleFrotaFiltro();
	private ControleFrotaRN controleFrotaRN = new ControleFrotaRN();
	private StreamedContent arquivoRetorno;
	private int tipoRelatorio;
	private List<ControleFrota> lista = new ArrayList<ControleFrota>();
	private LazyControleFrotaDataModel lazyControleFrota;

	public LazyControleFrotaDataModel getLazyControleFrota() {
		return lazyControleFrota;
	}
	
	@PostConstruct
	public void init()
	{
		this.lazyControleFrota = new LazyControleFrotaDataModel(controleFrotaRN, filtro);
	}
	
	//Método para gerar relatório e fornecer download do arquivo===========================
	public StreamedContent getArquivoRetorno()
	{
		this.lista = controleFrotaRN.buscarTodosPaginado(filtro);
		//For para setar o periodo escolhido pelo usuario==========
		for(int x = 0; x < this.lista.size();x++)
		{
			this.lista.get(x).setPeriodoInicial(filtro.getInicio());
			this.lista.get(x).setPeriodoFinal(filtro.getFim());
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		String nomeRelatorioJasper = "ControleFrota";
		String nomeRelatorioSaida = "ControleFrota_" + formatDate(new Date());
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		JRBeanCollectionDataSource cBean = new JRBeanCollectionDataSource(lista);
		
		try
		{
			this.arquivoRetorno = relatorioUtil.geraRelatorio(nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio, cBean);
			
		}
		catch (UtilException e)
		{
			context.addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Motivo: " + e.getMessage(), ""));
			return null;
		}
		
		return this.arquivoRetorno;
	}
	
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		return dateFormat.format(data);
	}
	
	public ControleFrotaFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(ControleFrotaFiltro filtro) {
		this.filtro = filtro;
	}

	public ControleFrotaRN getControleFrotaRN() {
		return controleFrotaRN;
	}

	public void setControleFrotaRN(ControleFrotaRN controleFrotaRN) {
		this.controleFrotaRN = controleFrotaRN;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

}
