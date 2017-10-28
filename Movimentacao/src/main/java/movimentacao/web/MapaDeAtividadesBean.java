package movimentacao.web;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;
import movimentacao.negocios.Negocios;
import movimentacao.negocios.NegociosRN;
import movimentacao.negocios.atividades.Atividades;
import movimentacao.negocios.atividades.AtividadesFiltro;
import movimentacao.negocios.atividades.AtividadesRN;
import movimentacao.relatorios.MapaDeAtividades;

@ManagedBean(name = "mapaDeAtividadesBean")
@ViewScoped
public class MapaDeAtividadesBean implements Serializable
{
	private static final long serialVersionUID = 1719926923645299148L;
	
	private MapaDeAtividades mapaDeAtividades = new MapaDeAtividades();
	private	List<MapaDeAtividades> listaMapaDeAtividades = new ArrayList<MapaDeAtividades>();
	private Atividades atividades = new Atividades();
	private AtividadesRN atividadesRN = new AtividadesRN();
	private AtividadesFiltro atividadesFiltro = new AtividadesFiltro();
	private Negocios negocios = new Negocios();
	private NegociosRN negociosRN = new NegociosRN();
	private Cliente cliente = new Cliente();
	private ClienteRN clienteRN = new ClienteRN();
	private String negocioCliente;
	private float totalGeral = 0;
	private int registros = 0;
	//Filtros===================================================
	private Date dataInicial;
	private Date dataFim;
	private List<String> clientes = new ArrayList<String>();
	private List<String> statusNegocio = new ArrayList<String>();
	
	public String filtroLista()
	{
		this.listaMapaDeAtividades.clear();
		
		int mesDataInicial = Integer.parseInt(formatMes(this.atividadesFiltro.getInicial()));
		int mesDataFinal = Integer.parseInt(formatMes(this.atividadesFiltro.getFim()));

		
		if(mesDataInicial == mesDataFinal)
		{
		
			List<Negocios> listaNegocios = negociosRN.negociosPreVenda();
			this.registros = listaNegocios.size();
			
			for(Negocios negocios:listaNegocios)
			{
				this.totalGeral += negocios.getValor();
				this.atividadesFiltro.setCliente(negocios.getCliente().getNome());
				List<Atividades> listaAtividades = atividadesRN.listar(this.atividadesFiltro);
				
				this.mapaDeAtividades = new MapaDeAtividades();
				this.mapaDeAtividades.setNegocio(negocios.getCliente().getNome());
				this.mapaDeAtividades.setDisplayImage1("false");
				this.mapaDeAtividades.setDisplayImage2("false");
				this.mapaDeAtividades.setDisplayImage3("false");
				this.mapaDeAtividades.setDisplayImage4("false");
				this.mapaDeAtividades.setDisplayImage5("false");
				this.mapaDeAtividades.setDisplayImage6("false");
				this.mapaDeAtividades.setDisplayImage7("false");
				this.mapaDeAtividades.setDisplayImage8("false");
				this.mapaDeAtividades.setDisplayImage9("false");
				this.mapaDeAtividades.setDisplayImage10("false");
				this.mapaDeAtividades.setDisplayImage11("false");
				this.mapaDeAtividades.setDisplayImage12("false");
				this.mapaDeAtividades.setDisplayImage13("false");
				this.mapaDeAtividades.setDisplayImage14("false");
				this.mapaDeAtividades.setDisplayImage15("false");
				this.mapaDeAtividades.setDisplayImage16("false");
				this.mapaDeAtividades.setDisplayImage17("false");
				this.mapaDeAtividades.setDisplayImage18("false");
				this.mapaDeAtividades.setDisplayImage19("false");
				this.mapaDeAtividades.setDisplayImage20("false");
				this.mapaDeAtividades.setDisplayImage21("false");
				this.mapaDeAtividades.setDisplayImage22("false");
				this.mapaDeAtividades.setDisplayImage23("false");
				this.mapaDeAtividades.setDisplayImage24("false");
				this.mapaDeAtividades.setDisplayImage25("false");
				this.mapaDeAtividades.setDisplayImage26("false");
				this.mapaDeAtividades.setDisplayImage27("false");
				this.mapaDeAtividades.setDisplayImage28("false");
				this.mapaDeAtividades.setDisplayImage29("false");
				this.mapaDeAtividades.setDisplayImage30("false");
				this.mapaDeAtividades.setDisplayImage31("false");
				
				int dia = 0;
				for(Atividades atividades:listaAtividades)
				{
					dia = Integer.parseInt(formatDia(atividades.getPrazo()));
					
					switch (dia) 
					{
						case 1:
							this.mapaDeAtividades.setDia1(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia1(atividades);
							this.mapaDeAtividades.setDisplayImage1("true");
							break;
							
						case 2:
							this.mapaDeAtividades.setDia2(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia2(atividades);
							this.mapaDeAtividades.setDisplayImage2("true");
							break;
							
						case 3:
							this.mapaDeAtividades.setDia3(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia3(atividades);
							this.mapaDeAtividades.setDisplayImage3("true");
							break;
							
						case 4:
							this.mapaDeAtividades.setDia4(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia4(atividades);
							this.mapaDeAtividades.setDisplayImage4("true");
							break;
							
						case 5:
							this.mapaDeAtividades.setDia5(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia5(atividades);
							this.mapaDeAtividades.setDisplayImage5("true");
							break;
							
						case 6:
							this.mapaDeAtividades.setDia6(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia6(atividades);
							this.mapaDeAtividades.setDisplayImage6("true");
							break;
							
						case 7:
							this.mapaDeAtividades.setDia7(Normalizer.normalize(atividades.getTipoAtividade(),
															Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia7(atividades);
							this.mapaDeAtividades.setDisplayImage7("true");
							break;
							
						case 8:
							this.mapaDeAtividades.setDia8(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia8(atividades);
							this.mapaDeAtividades.setDisplayImage8("true");
							break;
							
						case 9:
							this.mapaDeAtividades.setDia9(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia9(atividades);
							this.mapaDeAtividades.setDisplayImage9("true");
							break;
							
						case 10:
							this.mapaDeAtividades.setDia10(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia10(atividades);
							this.mapaDeAtividades.setDisplayImage10("true");
							break;
							
						case 11:
							this.mapaDeAtividades.setDia11(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia11(atividades);
							this.mapaDeAtividades.setDisplayImage11("true");
							break;
							
						case 12:
							this.mapaDeAtividades.setDia12(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia12(atividades);
							this.mapaDeAtividades.setDisplayImage12("true");
							break;
							
						case 13:
							this.mapaDeAtividades.setDia13(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia13(atividades);
							this.mapaDeAtividades.setDisplayImage13("true");
							break;
							
						case 14:
							this.mapaDeAtividades.setDia14(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia14(atividades);
							this.mapaDeAtividades.setDisplayImage14("true");
							break;
							
						case 15:
							this.mapaDeAtividades.setDia15(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia15(atividades);
							this.mapaDeAtividades.setDisplayImage15("true");
							break;
							
						case 16:
							this.mapaDeAtividades.setDia16(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia16(atividades);
							this.mapaDeAtividades.setDisplayImage16("true");
							break;
							
						case 17:
							this.mapaDeAtividades.setDia17(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia17(atividades);
							this.mapaDeAtividades.setDisplayImage17("true");
							break;
							
						case 18:
							this.mapaDeAtividades.setDia18(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia18(atividades);
							this.mapaDeAtividades.setDisplayImage18("true");
							break;
							
						case 19:
							this.mapaDeAtividades.setDia19(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia19(atividades);
							this.mapaDeAtividades.setDisplayImage19("true");
							break;
							
						case 20:
							this.mapaDeAtividades.setDia20(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia20(atividades);
							this.mapaDeAtividades.setDisplayImage20("true");
							break;
							
						case 21:
							this.mapaDeAtividades.setDia21(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia21(atividades);
							this.mapaDeAtividades.setDisplayImage21("true");
							break;
							
						case 22:
							this.mapaDeAtividades.setDia22(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia22(atividades);
							this.mapaDeAtividades.setDisplayImage22("true");
							break;
							
						case 23:
							this.mapaDeAtividades.setDia23(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia23(atividades);
							this.mapaDeAtividades.setDisplayImage23("true");
							break;
							
						case 24:
							this.mapaDeAtividades.setDia24(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia24(atividades);
							this.mapaDeAtividades.setDisplayImage24("true");
							break;
							
						case 25:
							this.mapaDeAtividades.setDia25(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia25(atividades);
							this.mapaDeAtividades.setDisplayImage25("true");
							break;
							
						case 26:
							this.mapaDeAtividades.setDia26(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia26(atividades);
							this.mapaDeAtividades.setDisplayImage26("true");
							break;
							
						case 27:
							this.mapaDeAtividades.setDia27(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia27(atividades);
							this.mapaDeAtividades.setDisplayImage27("true");
							break;
							
						case 28:
							this.mapaDeAtividades.setDia28(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia28(atividades);
							this.mapaDeAtividades.setDisplayImage28("true");
							break;
							
						case 29:
							this.mapaDeAtividades.setDia29(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia29(atividades);
							this.mapaDeAtividades.setDisplayImage29("true");
							break;
							
						case 30:
							this.mapaDeAtividades.setDia30(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia30(atividades);
							this.mapaDeAtividades.setDisplayImage30("true");
							break;
							
						case 31:
							this.mapaDeAtividades.setDia31(Normalizer.normalize(atividades.getTipoAtividade(),
									Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
							this.mapaDeAtividades.setAtividadeDia31(atividades);
							this.mapaDeAtividades.setDisplayImage31("true");
							break;
		
						default:
							break;
					}
					
					
				}
				
				this.listaMapaDeAtividades.add(this.mapaDeAtividades);
				listaAtividades.clear();
				
			}
			
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "O conceito de mapa de atividades, apenas permite pesquisa dentro de um único mês.", ""));
		}
		/*for(int x = 0; x < this.listaMapaDeAtividades.size(); x++)
		{
			System.out.println("Mapa de Atividades: " + this.listaMapaDeAtividades.get(x).getNegocio()
							  +"\n-> Dia 7: " + this.listaMapaDeAtividades.get(x).getDia7()
							  +"\n-> Dia 10: " + this.listaMapaDeAtividades.get(x).getDia10());
		}*/
		return null;
	}
	
	public String visualizar()
	{
		this.negocioCliente = this.atividades.getNegocios().getCliente().getNome();
		
		
		return null;
	}
	
	private static String formatDia(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("d");
		
		return dateFormat.format(data);
	}
	
	private static String formatMes(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("M");
		
		return dateFormat.format(data);
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<MapaDeAtividades> getListaMapaDeAtividades() {
		return listaMapaDeAtividades;
	}

	public void setListaMapaDeAtividades(List<MapaDeAtividades> listaMapaDeAtividades) {
		this.listaMapaDeAtividades = listaMapaDeAtividades;
	}

	public AtividadesFiltro getAtividadesFiltro() {
		return atividadesFiltro;
	}

	public void setAtividadesFiltro(AtividadesFiltro atividadesFiltro) {
		this.atividadesFiltro = atividadesFiltro;
	}

	public MapaDeAtividades getMapaDeAtividades() {
		return mapaDeAtividades;
	}

	public void setMapaDeAtividades(MapaDeAtividades mapaDeAtividades) {
		this.mapaDeAtividades = mapaDeAtividades;
	}

	public Atividades getAtividades() {
		return atividades;
	}

	public void setAtividades(Atividades atividades) {
		this.atividades = atividades;
	}

	public String getNegocioCliente() {
		return negocioCliente;
	}

	public void setNegocioCliente(String negocioCliente) {
		this.negocioCliente = negocioCliente;
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

}
