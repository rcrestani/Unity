package movimentacao.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.StreamedContent;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;
import movimentacao.itMov.ItMov;
import movimentacao.itMov.ItMovRN;
import movimentacao.mov.Mov;
import movimentacao.mov.MovRN;
import movimentacao.relatorios.Fechamento;
import movimentacao.util.UtilException;
import movimentacao.web.util.RelatorioUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "fechamentoBean")
@ViewScoped
public class FechamentoBean implements Serializable
{
	private static final long serialVersionUID = -767270829103900203L;
	
	private Fechamento fechamento;
	private Mov mov;
	private MovRN movRN = new MovRN();
	private ItMov itMov;
	private ItMovRN itMovRN = new ItMovRN();
	private Cliente cliente = new Cliente();
	private ClienteRN clienteRN = new ClienteRN();
	
	private Date dataInicial;
	private Date dataFinal;
	private int talao;
	private String nomeCliente;
	private String tipoMovi;
	private float totais;
	private float totalGeral;
	private int registros;
	
	private StreamedContent arquivoRetorno;
	private int tipoRelatorio;
	
	private List<Fechamento> lista = new ArrayList<Fechamento>();

	
	public String filtrar()
	{
		this.mov = new Mov();
		
		this.totalGeral = 0;
		lista.clear();
		if(StringUtils.isEmpty(nomeCliente))
		{
			for (int i = 0; i < movRN.movFechamento(this.dataInicial, this.dataFinal).size(); i++)
			{
				this.mov = movRN.movFechamento(this.dataInicial, this.dataFinal).get(i);
				this.itMov = new ItMov();
				
				for (int x = 0; x < itMovRN.itMovFechamento(this.mov, this.tipoMovi).size(); x++)
				{
					this.itMov = itMovRN.itMovFechamento(this.mov, this.tipoMovi).get(x);
					this.totais += this.itMov.getQtde() * this.itMov.getvUnit();
				}
				
				if (StringUtils.isNotEmpty(this.itMov.getTipoMovi()))
				{
					this.totalGeral += this.totais;
					
					this.fechamento = new Fechamento();
					
					this.fechamento.setData(this.mov.getData());
					this.fechamento.setCliente(this.mov.getCliente().getNome());
					this.fechamento.setTalao(this.mov.getTalao());
					this.fechamento.setTipoMovi(this.itMov.getTipoMovi());
					this.fechamento.setTotais(this.totais);
					this.fechamento.setDataInicial(this.dataInicial);
					this.fechamento.setDataFinal(this.dataFinal);
					
					this.lista.add(fechamento);
					
					this.totais = 0;
				}
			}
			this.registros = lista.size();
		}
		else
		{
			this.cliente = clienteRN.buscarPorNome(this.nomeCliente);

			for (int i = 0; i < movRN.movFechamentoCliente(this.cliente , this.dataInicial , this.dataFinal).size(); i++)
			{
				this.mov = movRN.movFechamentoCliente(this.cliente , this.dataInicial, this.dataFinal).get(i);
				this.itMov = new ItMov();
				
				for (int x = 0; x < itMovRN.itMovFechamento(this.mov, this.tipoMovi).size(); x++)
				{
					this.itMov = itMovRN.itMovFechamento(this.mov, this.tipoMovi).get(x);
					this.totais += this.itMov.getQtde() * this.itMov.getvUnit();
				}
				
				if (StringUtils.isNotEmpty(this.itMov.getTipoMovi()))
				{
					this.totalGeral += this.totais;
					
					this.fechamento = new Fechamento();
					
					this.fechamento.setData(this.mov.getData());
					this.fechamento.setCliente(this.mov.getCliente().getNome());
					this.fechamento.setTalao(this.mov.getTalao());
					this.fechamento.setTipoMovi(this.itMov.getTipoMovi());
					this.fechamento.setTotais(this.totais);
					
					this.lista.add(fechamento);
					
					this.totais = 0;
				}
				else
				{
					lista.clear();
				}
			}
			this.registros = lista.size();
		}
		
		return null;
	}
	
	public StreamedContent getArquivoRetorno()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		String nomeRelatorioJasper = "FechamentoGeral";
		String nomeRelatorioSaida = "Fechamento_Geral_" + formatDate(this.dataInicial) + "_a_" + formatDate(this.dataFinal);
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
	
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public int getTalao() {
		return talao;
	}

	public void setTalao(int talao) {
		this.talao = talao;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getTipoMovi() {
		return tipoMovi;
	}

	public void setTipoMovi(String tipoMovi) {
		this.tipoMovi = tipoMovi;
	}

	public float getTotais() {
		return totais;
	}

	public void setTotais(float totais) {
		this.totais = totais;
	}

	public List<Fechamento> getLista() {
		return lista;
	}

	public void setLista(List<Fechamento> lista) {
		this.lista = lista;
	}

	public float getTotalGeral() {
		return totalGeral;
	}

	public void setTotalGeral(float totalGeral) {
		this.totalGeral = totalGeral;
	}

	public int getRegistros() {
		return registros;
	}

	public void setRegistros(int registros) {
		this.registros = registros;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
	
	
}
