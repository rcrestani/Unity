package movimentacao.web;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.rpc.ServiceException;

import org.primefaces.event.SelectEvent;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteServiceLocator;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SQLException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.projetoNCE.empresa.EmpresaFiltro;
import movimentacao.projetoNCE.empresa.EmpresaRN;
import movimentacao.projetoNCE.tecnico.LazyTecnicoDataModel;
import movimentacao.projetoNCE.tecnico.Tecnico;
import movimentacao.projetoNCE.tecnico.TecnicoFiltro;
import movimentacao.projetoNCE.tecnico.TecnicoRN;
import movimentacao.projetoNCE.tecnico.notesTecnico.NotesTecnico;
import movimentacao.projetoNCE.tecnico.notesTecnico.NotesTecnicoRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean(name = "nceTecnicoBean")
@ViewScoped
public class NceTecnicoBean implements Serializable
{
	private static final long serialVersionUID = 3947328299896789030L;
	
	private Tecnico tecnico = new Tecnico();
	private TecnicoRN tecnicoRN = new TecnicoRN();
	private List<Tecnico> lista = new ArrayList<Tecnico>();
	
	private Empresa empresa = new Empresa();
	private EmpresaRN empresaRN = new EmpresaRN();
	private EmpresaFiltro empresaFiltro = new EmpresaFiltro();
	private String nomeEmpresa = "";
	private String cep = "";
	
	private NotesTecnico notesTecnico = new NotesTecnico();
	private NotesTecnicoRN notesTecnicoRN = new NotesTecnicoRN();
	private List<NotesTecnico> listarNotes = new ArrayList<NotesTecnico>();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	private UsuarioRN usuarioRN = new UsuarioRN();
	
	//VARIÁVEIS DE CONTROLE DE FORMULÁRIOS===========================================
	private boolean formNotes = false;
	//===============================================================================
	
	private TecnicoFiltro filtro = new TecnicoFiltro();
	private LazyTecnicoDataModel lazyTecnico;
	
	@PostConstruct
	public void init()
	{
		this.lazyTecnico = new LazyTecnicoDataModel(this.tecnicoRN, this.filtro);
	}

	public String salvar()
	{		
		boolean controle = true;
		if(this.tecnicoRN.tecnicoPorCPF(this.tecnico.getCpf()) == null
				|| this.tecnicoRN.carregar(this.tecnico.getId()) == null)
		{
			this.tecnico.setStatus(true);
			this.tecnico.setIdEmpresa(this.empresaRN.empresaPorNome(this.nomeEmpresa));
			this.tecnico.setDataHoraReg(new Date());
			this.tecnico.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
			try
			{
				this.tecnicoRN.salvar(this.tecnico);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , "Técnico Registrado com sucesso!", ""));
			}
			catch (Exception e)
			{
				controle = false;
				System.out.println("CATCH TECNICO: " + e.getMessage());
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_FATAL ,
			    		"Técnico não registrado!", "Dados inválidos. Verifique se o técnico já possui cadastro."));
			}
		}
		else if(this.tecnicoRN.tecnicoPorCPF(this.tecnico.getCpf()).getId() == this.tecnico.getId())
		{
			this.tecnico.setIdEmpresa(this.empresaRN.empresaPorNome(this.nomeEmpresa));
			this.tecnico.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
			try
			{
				this.tecnicoRN.salvar(this.tecnico);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , "Técnico Registrado com sucesso!", ""));
			}
			catch (Exception e)
			{
				controle = false;
				System.out.println("CATCH TECNICO: " + e.getMessage());
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_FATAL ,
			    		"Técnico não registrado!", "Dados inválidos. Verifique se o técnico já possui cadastro."));
			}
		}
		else
		{
			controle = false;
			System.out.println("ELSE TECNICO");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_FATAL ,
		    		"Técnico não registrado!", "Dados inválidos. Verifique se o técnico já possui cadastro."));
		}
		
		if(controle)
		{
			this.nomeEmpresa = "";
			this.tecnico = new Tecnico();
		}
		
		return null;
	}
	
	public String novo()
	{
		this.tecnico = new Tecnico();
		this.nomeEmpresa = "";
		return null;
	}
	
	public String editar()
	{
		try
		{
			this.nomeEmpresa = this.empresaRN.carregar(this.tecnico.getIdEmpresa().getId()).getRazaoSocial();

		}
		catch (Exception e)
		{
			System.out.println("ERRO EDITAR TÉCNICO: " + e.getMessage());
		}
				
		return null;
	}
	
	public String consulta()
	{
		this.empresa = this.empresaRN.empresaPorNome(this.empresaFiltro.getRazaoSocial());
		
		this.filtro.setIdEmpresa(this.empresa);
		
		return null;
	}
	
	public void tecnicoSelecionado() 
	{
		String nomeTecnico = this.tecnico.getNome();
		this.tecnico = new Tecnico();
		
		try
		{
			this.tecnico = this.tecnicoRN.tecnicoPorNome(nomeTecnico);
			this.nomeEmpresa = this.empresaRN.carregar(this.tecnico.getIdEmpresa().getId()).getRazaoSocial();
		}
		catch (Exception e)
		{
			this.tecnico = new Tecnico();
			this.tecnico.setNome(nomeTecnico);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados do Técnico!", ""));
		}
	}
	
	public void notificacaoCPF() 
	{
		if(this.tecnicoRN.tecnicoPorCPF(this.tecnico.getCpf()) != null
				&& this.tecnicoRN.tecnicoPorCPF(this.tecnico.getCpf()).getId() != this.tecnico.getId())
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro! Este CPF já pertence a outro Técnico!", ""));
		}
	}

//MÉTODOS PARA O BEAN NOTESTECNICO==========================================================
	public String salvarNotes()
	{
		this.notesTecnico.setIdTecnico(this.tecnico.getId());
		this.notesTecnico.setIdUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.notesTecnico.setDataHoraReg(new Date());
		
		if(this.tecnico.isStatus())
		{
			this.notesTecnico.setAcao("desbloqueou");
			this.tecnico.setTempBlock(null);
		}
		else
		{
			this.notesTecnico.setAcao("bloqueou");
			this.tecnico.setTempBlock(null);
		}
		
		try
		{
			editar();
			salvar();
			this.notesTecnicoRN.salvar(this.notesTecnico);
			this.formNotes = false;
			
			this.listarNotes.add(this.notesTecnico);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Alteração do status registrada com sucesso!", ""));
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao registar esta alteração do status!", ""));
		}
				
		return null;
	}
	
	public String novaAcao()
	{
		this.notesTecnico = new NotesTecnico();
		this.formNotes = true;
		
		return null;
	}
	
	public String listarNotes()
	{
		this.listarNotes.clear();
		this.listarNotes = this.notesTecnicoRN.listarPorTecnico(this.tecnico.getId());
		
		return null;
	}
	
//FIM NOTESTECNICO==========================================================================
	
//MÉTODOS PARA O BEAN EMPRESA===============================================================
	public String salvarEmpresa()
	{
		this.empresa.setCep(this.cep);
		this.empresa.setDataHoraReg(new Date());
		this.empresa.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.empresaRN.salvar(this.empresa);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Empresa Registrada com sucesso!", ""));
		
		this.cep = "";
		this.empresa = new Empresa();
		
		return null;
	}
	
	public void buscarEndereco(ActionEvent actionEvent) throws MalformedURLException, ServiceException, SQLException, SigepClienteException, RemoteException
	{
		AtendeClienteServiceLocator serviceCorreios = new AtendeClienteServiceLocator();
		AtendeCliente atendeCliente = serviceCorreios.getAtendeClientePort(
				new URL("https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl"));
		EnderecoERP correios = new EnderecoERP();
		correios = atendeCliente.consultaCEP(this.cep);
		
		this.cep = correios.getCep();
		this.empresa.setEndereco(correios.getEnd());
		this.empresa.setBairro(correios.getBairro());
		this.empresa.setCidade(correios.getCidade());
		this.empresa.setUf(correios.getUf());
		
	}
	
	public void empresaSelecionada(SelectEvent event) 
	{
		String nomeEmpresa = this.empresa.getRazaoSocial();
		this.empresa = new Empresa();
		
		try
		{
			this.empresa = this.empresaRN.empresaPorNome(nomeEmpresa);
			this.cep = this.empresa.getCep();
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
		}
		
	}
//FIM MÉTODOS EMPRESA==========================================================================================
	
	public Tecnico getTecnico() {
		return tecnico;
	}


	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}


	public List<Tecnico> getLista() {
		return lista;
	}


	public void setLista(List<Tecnico> lista) {
		this.lista = lista;
	}


	public TecnicoRN getTecnicoRN() {
		return tecnicoRN;
	}


	public void setTecnicoRN(TecnicoRN tecnicoRN) {
		this.tecnicoRN = tecnicoRN;
	}


	public EmpresaRN getEmpresaRN() {
		return empresaRN;
	}


	public void setEmpresaRN(EmpresaRN empresaRN) {
		this.empresaRN = empresaRN;
	}


	public String getNomeEmpresa() {
		return nomeEmpresa;
	}


	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public TecnicoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(TecnicoFiltro filtro) {
		this.filtro = filtro;
	}

	public LazyTecnicoDataModel getLazyTecnico() {
		return lazyTecnico;
	}

	public NotesTecnico getNotesTecnico() {
		return notesTecnico;
	}

	public void setNotesTecnico(NotesTecnico notesTecnico) {
		this.notesTecnico = notesTecnico;
	}

	public NotesTecnicoRN getNotesTecnicoRN() {
		return notesTecnicoRN;
	}

	public void setNotesTecnicoRN(NotesTecnicoRN notesTecnicoRN) {
		this.notesTecnicoRN = notesTecnicoRN;
	}

	public List<NotesTecnico> getListarNotes() {
		return listarNotes;
	}

	public void setListarNotes(List<NotesTecnico> listarNotes) {
		this.listarNotes = listarNotes;
	}

	public boolean isFormNotes() {
		return formNotes;
	}

	public void setFormNotes(boolean formNotes) {
		this.formNotes = formNotes;
	}

	public EmpresaFiltro getEmpresaFiltro() {
		return empresaFiltro;
	}

	public void setEmpresaFiltro(EmpresaFiltro empresaFiltro) {
		this.empresaFiltro = empresaFiltro;
	}
	
	
}
