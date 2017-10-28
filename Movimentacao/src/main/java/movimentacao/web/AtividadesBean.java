package movimentacao.web;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;
import movimentacao.negocios.Negocios;
import movimentacao.negocios.NegociosRN;
import movimentacao.negocios.atividades.Atividades;
import movimentacao.negocios.atividades.AtividadesFiltro;
import movimentacao.negocios.atividades.AtividadesRN;
import movimentacao.usuario.UsuarioRN;
import movimentacao.util.JavaMailApp;

@ManagedBean(name = "atividadesBean")
@ViewScoped
public class AtividadesBean implements Serializable
{
	private static final long serialVersionUID = 8587685782826135820L;
	
	private Atividades atividades = new Atividades();
	private AtividadesRN atividadesRN = new AtividadesRN();
	private List<Atividades> listaAtividades = new ArrayList<Atividades>();
	private Cliente cliente = new Cliente();
	private ClienteRN clienteRN = new ClienteRN();
	private String negocioCliente;
	private Negocios negocios = new Negocios();
	private NegociosRN negociosRN = new NegociosRN();
	
	public static AtividadesFiltro filtro = new AtividadesFiltro();
	private String nomeUsuario;
	private int qtde;
	private String bloquearCampo = "false";
	private String notification = "false";
	private int qtdeNotification;
	private String ocultarCampoUsuario = "true";
	private String habilitarListaUsuario = "false";
		
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	private UsuarioRN usuarioRN = new UsuarioRN();
	
	@PostConstruct
	public void init()
	{
		this.listaAtividades = atividadesRN.listar(filtro);
		this.qtde = this.listaAtividades.size();
		this.atividades.setUsuario(usuarioRN.buscarPorLogin(login));
		listaPendentes();
		AtividadesBean.filtro = new AtividadesFiltro();
	}
	
	public String salvar()
	{
		boolean email = false;
		this.cliente = clienteRN.buscarPorNome(negocioCliente);
		this.negocios = negociosRN.buscaPorCliente(this.cliente);
		this.atividades.setNegocios(this.negocios);
		
		if(this.atividades.getData() == null)
		{
			this.atividades.setData(new Date());
		}
		
		if(this.atividades.getPrazo() == null)
		{
			this.atividades.setPrazo(new Date());
		}
		
		if(this.habilitarListaUsuario.equals("true"))
		{
			this.atividades.setUsuario(usuarioRN.buscarPorNome(this.nomeUsuario));
			email = true;
		}
		else
		{
			this.atividades.setUsuario(usuarioRN.buscarPorLogin(login));
			email = false;
		}
		

		this.atividadesRN.salvar(this.atividades);
		
		AtividadesBean.filtro.setCliente(this.cliente.getNome());
		int qtdeAtividades = atividadesRN.listar(filtro).size();
		
		this.negocios.setQtdeAtividades(qtdeAtividades);
		this.negociosRN.salvar(this.negocios);
		
		AtividadesBean.filtro.setCliente("");
		
		if(email == true)
		{
			JavaMailApp javaMail = new JavaMailApp();
			
			javaMail.setDestinatario(this.atividades.getUsuario().getEmail());
			javaMail.setAssunto("Unity - Atividade Recebida");
			javaMail.setMsg("<center><h2>Unity - Atividade Recebida</h2></center>"
							+"<br>Olá " + this.atividades.getUsuario().getNome()
							+ "!<br>" + usuarioRN.buscarPorLogin(login).getNome() + " designou esta atividade para você fazer follow-up:"
							+ "<br><br><b>Cliente:</b> " + this.atividades.getNegocios().getCliente().getNome()
							+ "<br><b>Ação:</b> " + this.atividades.getTipoAtividade()
							+ "<br><b>Prazo:</b> " + formatDate(this.atividades.getPrazo())
							+ "<br><b>Obs:</b> " + this.atividades.getObs());
			
				new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							javaMail.sendMail();
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}.start();
				
		}
		
		return "/restrito/atividades.jsf";
	}
	
	public String editar()
	{
		this.negocioCliente = this.atividades.getNegocios().getCliente().getNome();
		this.bloquearCampo = "true";
		this.ocultarCampoUsuario = "true";
		this.habilitarListaUsuario = "false";
		return null;
	}
	
	public String novo()
	{
		this.bloquearCampo = "false";
		this.ocultarCampoUsuario = "true";
		this.habilitarListaUsuario = "false";
		this.negocioCliente = "";
		this.atividades = new Atividades();
		this.atividades.setUsuario(usuarioRN.buscarPorLogin(login));

		return null;
	}
	
	public String encaminhar()
	{
		this.ocultarCampoUsuario = "false";
		this.habilitarListaUsuario = "true";
		
		return null;
	}
	
	public String status()
	{
		if(this.atividades.isFinalizado())
		{
			this.atividades.setFinalizado(false);
		}
		else
		{
			this.atividades.setFinalizado(true);
		}
		
		AtividadesRN atividadesRN = new AtividadesRN();
		atividadesRN.salvar(this.atividades);
		
		listaPendentes();
		
		return null;
	}
	
	public String filtroLista()
	{		
		AtividadesBean.filtro.setUsuario(this.usuarioRN.buscarPorNome(this.nomeUsuario));
		
		AtividadesRN atividadesRN = new AtividadesRN();
		this.listaAtividades = atividadesRN.listar(AtividadesBean.filtro);
		this.qtde = this.listaAtividades.size();
		
		return "/restrito/atividades.jsf";
	}
	
	public String listaPendentes()
	{
		this.qtdeNotification = atividadesRN.listarPendentes().size();
		
		if(this.qtdeNotification > 0)
		{
			this.notification = "true";
		}
		else
		{
			this.notification = "false";
		}
		
		return null;
	}
	
	public String carregarPendentes()
	{
		this.listaAtividades.clear();
		
		this.listaAtividades = atividadesRN.listarPendentes();
		
		this.qtde = this.listaAtividades.size();
		
		return null;
	}
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm");
		
		return dateFormat.format(data);
	}
	
	//Getters and Setters==============================================================================================
	public Atividades getAtividades() {
		return atividades;
	}

	public void setAtividades(Atividades atividades) {
		this.atividades = atividades;
	}

	public List<Atividades> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<Atividades> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public String getNegocioCliente() {
		return negocioCliente;
	}

	public void setNegocioCliente(String negocioCliente) {
		this.negocioCliente = negocioCliente;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public AtividadesFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(AtividadesFiltro filtro) {
		AtividadesBean.filtro = filtro;
	}
	public String getBloquearCampo() {
		return bloquearCampo;
	}

	public void setBloquearCampo(String bloquearCampo) {
		this.bloquearCampo = bloquearCampo;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public int getQtdeNotification() {
		return qtdeNotification;
	}

	public void setQtdeNotification(int qtdeNotification) {
		this.qtdeNotification = qtdeNotification;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public UsuarioRN getUsuarioRN() {
		return usuarioRN;
	}

	public void setUsuarioRN(UsuarioRN usuarioRN) {
		this.usuarioRN = usuarioRN;
	}

	public String getBloquerCampoUsuario() {
		return ocultarCampoUsuario;
	}

	public void setOcultarCampoUsuario(String ocultarCampoUsuario) {
		this.ocultarCampoUsuario = ocultarCampoUsuario;
	}

	public String getHabilitarListaUsuario() {
		return habilitarListaUsuario;
	}

	public void setHabilitarListaUsuario(String habilitarListaUsuario) {
		this.habilitarListaUsuario = habilitarListaUsuario;
	}
	
}
