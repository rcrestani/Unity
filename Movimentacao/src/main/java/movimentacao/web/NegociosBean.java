package movimentacao.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;
import movimentacao.negocios.LazyNegociosDataModel;
import movimentacao.negocios.Negocios;
import movimentacao.negocios.NegociosFiltro;
import movimentacao.negocios.NegociosRN;
import movimentacao.negocios.atividades.Atividades;
import movimentacao.negocios.atividades.AtividadesRN;
import movimentacao.negocios.contatos.Contatos;
import movimentacao.negocios.contatos.ContatosRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean(name = "negociosBean")
@ViewScoped
public class NegociosBean implements Serializable
{
	private static final long serialVersionUID = 7846094803339633003L;
	
	private Negocios negocios = new Negocios();
	private NegociosRN negociosRN = new NegociosRN();
	private Atividades atividades = new Atividades();
	private AtividadesRN atividadesRN = new AtividadesRN();
	private int qtdeAtividades;
	private UsuarioRN usuarioRN = new UsuarioRN();
	private Cliente cliente = new Cliente();
	private ClienteRN clienteRN = new ClienteRN();
	private Contatos contatos = new Contatos();
	private ContatosRN contatosRN = new ContatosRN();
	private List<Contatos> listaContatos = new ArrayList<Contatos>();
	private String exibirContatos = "false";
	private List<String> produtos = new ArrayList<String>();
	
	private String bloquearCampo = "false";
	private boolean responsavel = false;
	public float total;
	private List<Negocios> listaNegocios = new ArrayList<Negocios>();
	
	private LazyNegociosDataModel lazyNegocios;
	private NegociosFiltro filtro = new NegociosFiltro();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	
	@PostConstruct
	public void init()
	{
		lazyNegocios = new LazyNegociosDataModel(negociosRN, filtro);
		negocios.setUsuario(usuarioRN.buscarPorLogin(login));
		valorTotal();
	}
	
	public String salvar()
	{
		try
		{
			if(this.negocios.getId() == null)
			{
				UsuarioRN usuarioRN = new UsuarioRN();
				this.negocios.setUsuario(usuarioRN.buscarPorLogin(login));
				
				ClienteRN clienteRN = new ClienteRN();
				this.negocios.setCliente(clienteRN.buscarPorNome(cliente.getNome()));
				
				this.negocios.setDataHora(new Date());
				
				this.negocios.getProdutos().clear();
				for(int x = 0; x < produtos.size() ; x++)
				{
					this.negocios.getProdutos().add(produtos.get(x));
				}
				
				this.produtos.clear();
								
				negociosRN.salvar(this.negocios);
				
				valorTotal();
				return "/restrito/negocios.jsf";
				
				
			}
			else
			{
				if(responsavel == true)
				{
					UsuarioRN usuarioRN = new UsuarioRN();
					this.negocios.setUsuario(usuarioRN.buscarPorLogin(login));
				}
				
				this.negocios.getProdutos().clear();
				for(int x = 0; x < produtos.size() ; x++)
				{
					this.negocios.getProdutos().add(produtos.get(x));
				}
				
				this.produtos.clear();
				
				negociosRN.salvar(this.negocios);
				
				valorTotal();
				return "/restrito/negocios.jsf";
			}

			
		}
		catch(Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro! " + "O negócio não foi salvo no Banco de Dados. Motivo: " + e.getMessage(), ""));
			
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public String addContato()
	{
		this.exibirContatos = "true";
		Contatos contatos = new Contatos();
		contatos.setNome("Novo");
		this.listaContatos.add(contatos);
		
		return null;
	}
	
	public String onCellEdit(CellEditEvent event)
	{		
		//Método para salvar qualquer registro inserido ou alterado em qualquer célula=========================
		
		for(int x = 0; x < this.listaContatos.size(); x++)
		{
			if(x == event.getRowIndex())
			{
				if(event.getColumn().getAriaHeaderText().equals("Nome"))
				{
					this.listaContatos.get(x).setNome(event.getNewValue().toString());
				}
				else if(event.getColumn().getAriaHeaderText().equals("Email"))
				{
					this.listaContatos.get(x).setEmail(event.getNewValue().toString());
				}
				else if(event.getColumn().getAriaHeaderText().equals("Telefone"))
				{
					this.listaContatos.get(x).setTelefone(event.getNewValue().toString());
				}
			}
		}
		
		for(Contatos novosContatos:this.listaContatos)
		{
			novosContatos.setNegocios(this.negocios);
			this.contatosRN.salvar(novosContatos);
		}
		
		/*this.contatos.setNegocios(this.negocios);
		this.contatosRN.salvar(this.contatos);
		this.listaContatos.clear();
		this.listaContatos = contatosRN.contatosPorNegocios(this.negocios);*/
		this.exibirContatos = "true";
		
		return null;
	}
	
	public String limpaForm()
	{
		this.listaContatos.clear();
		this.exibirContatos = "false";
		this.responsavel = false;
		this.bloquearCampo = "false";
		this.negocios = new Negocios();
		negocios.setUsuario(usuarioRN.buscarPorLogin(login));
		this.cliente = new Cliente();
		
		if(produtos != null)
		{
			produtos.clear();
		}
		return null;
	}

	public String editar()
	{
		this.responsavel = false;
		this.bloquearCampo = "true";
		this.produtos.clear();
		
		this.cliente.setNome(this.negocios.getCliente().getNome());
		//Recarregando os produtos do negócios, pois a função LazyDataModel não carrega a lista de produtos========================
		Negocios negociosProdutos = negociosRN.carregar(this.negocios.getId());
		this.negocios.setProduto(negociosProdutos.getProdutos());
		this.produtos.addAll(this.negocios.getProdutos());
		
		this.listaContatos.clear();
		this.exibirContatos = "true";
		this.listaContatos = contatosRN.contatosPorNegocios(this.negocios);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String valorTotal()
	{
		//Pegando a listagem dos objetos exibidos em tela pelo DataLazyModel, para calcular o valor total dos Negócios================
		listaNegocios = negociosRN.criarCriteriaParaFiltro(filtro).list();
		
		float valorTotal = 0;
		
		for(Negocios negociosValorTotal:listaNegocios)
		{
			valorTotal += negociosValorTotal.getValor();
		}
				
		this.total = valorTotal;

	//=============================================================================================================================
		return null;
	}
	
	public String viewAtividades()
	{
		AtividadesBean.filtro.setCliente(this.negocios.getCliente().getNome());
		
		return "/restrito/atividades";
	}

	public List<String> clienteSemNegocios(String query)
	{
		List<String> clientesComNegocios = negociosRN.negociosPorCliente(query);
		List<String> clientesSemNegocios = clienteRN.completeText(query);
		
		for(int x = 0; x < clientesComNegocios.size(); x++)
		{
			for(int y = 0; y < clientesSemNegocios.size(); y++)
			{
				if(clientesComNegocios.get(x).equals(clientesSemNegocios.get(y)))
				{
					clientesSemNegocios.remove(clientesComNegocios.get(x));
				}
			}
		}
		
		return clientesSemNegocios;
	}
	
	
	public Negocios getNegocios() {
		return negocios;
	}
	
	public void setNegocios(Negocios negocios) {
		this.negocios = negocios;
	}

	public NegociosRN getNegociosRN() {
		return negociosRN;
	}

	public void setNegociosRN(NegociosRN negociosRN) {
		this.negociosRN = negociosRN;
	}

	public Atividades getAtividades() {
		return atividades;
	}

	public void setAtividades(Atividades atividades) {
		this.atividades = atividades;
	}

	public AtividadesRN getAtividadesRN() {
		return atividadesRN;
	}

	public void setAtividadesRN(AtividadesRN atividadesRN) {
		this.atividadesRN = atividadesRN;
	}

	public UsuarioRN getUsuarioRN() {
		return usuarioRN;
	}

	public void setUsuarioRN(UsuarioRN usuarioRN) {
		this.usuarioRN = usuarioRN;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<String> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<String> produtos) {
		this.produtos = produtos;
	}

	public NegociosFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(NegociosFiltro filtro) {
		this.filtro = filtro;
	}

	public LazyNegociosDataModel getLazyNegocios() {
		return lazyNegocios;
	}

	public boolean isResponsavel() {
		return responsavel;
	}

	public void setResponsavel(boolean responsavel) {
		this.responsavel = responsavel;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<Negocios> getListaNegocios() {
		return listaNegocios;
	}

	public void setListaNegocios(List<Negocios> listaNegocios) {
		this.listaNegocios = listaNegocios;
	}

	public int getQtdeAtividades() {
		return qtdeAtividades;
	}

	public void setQtdeAtividades(int qtdeAtividades) {
		this.qtdeAtividades = qtdeAtividades;
	}

	public String getBloquearCampo() {
		return bloquearCampo;
	}

	public void setBloquearCampo(String bloquearCampo) {
		this.bloquearCampo = bloquearCampo;
	}

	public Contatos getContatos() {
		return contatos;
	}

	public void setContatos(Contatos contatos) {
		this.contatos = contatos;
	}

	public List<Contatos> getListaContatos() {
		return listaContatos;
	}

	public void setListaContatos(List<Contatos> listaContatos) {
		this.listaContatos = listaContatos;
	}

	public String getExibirContatos() {
		return exibirContatos;
	}

	public void setExibirContatos(String exibirContatos) {
		this.exibirContatos = exibirContatos;
	}
	
}
