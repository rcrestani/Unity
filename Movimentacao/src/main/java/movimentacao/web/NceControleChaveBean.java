package movimentacao.web;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.ControleChaveRN;
import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.projetoNCE.empresa.EmpresaRN;
import movimentacao.projetoNCE.tecnico.Tecnico;
import movimentacao.projetoNCE.tecnico.TecnicoRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean(name = "nceControleChaveBean")
@ViewScoped
public class NceControleChaveBean implements Serializable
{
	private static final long serialVersionUID = 7741636814187218597L;
	
	private ControleChave controleChave = new ControleChave();
	private ControleChaveRN controleChaveRN = new ControleChaveRN();
	private UsuarioRN usuarioRN = new UsuarioRN();
	private Tecnico tecnico = new Tecnico();
	private TecnicoRN tecnicoRN = new TecnicoRN();
	private Empresa empresa = new Empresa();
	private EmpresaRN empresaRN = new EmpresaRN();
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	private String usuario = "";
	private String nomeTecnico = "";
	private String nomeEmpresa = "";
	private String cep = "";
	
	//Variaveis de controle de formulario===============================
	private boolean formControleChave = false;
	//==================================================================
	
	public String salvar()
	{
		int ultimoId = this.controleChaveRN.ultimoRegistro().getId() + 1;
		this.controleChave.setIdAno(String.format("%d",  ultimoId) + "/" + formatYear(new Date()));
		
		this.tecnico = this.tecnicoRN.tecnicoPorNome(this.nomeTecnico);
		this.controleChave.setIdTecnico(this.tecnico);
		
		this.controleChave.setDataHoraReg(new Date());
		this.controleChave.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.controleChaveRN.salvar(this.controleChave);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Requisição Registrada com sucesso!", ""));
		
		this.formControleChave = false;
		return null;
	}
	
	public String novo()
	{
		this.controleChave = new ControleChave();
		this.controleChave.setDataAbertura(new Date());
		this.formControleChave = true;
		this.usuario = this.usuarioRN.buscarPorLogin(this.login).getNome();
		this.tecnico = new Tecnico();
		this.nomeTecnico = "";
		return null;
	}
	
	//MÉTODOS PARA O BEAN TECNICO===============================================================
	public String salvarTecnico()
	{
		this.tecnico.setIdEmpresa(this.empresaRN.empresaPorNome(this.nomeEmpresa));
		this.tecnico.setDataHoraReg(new Date());
		this.tecnico.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.tecnicoRN.salvar(this.tecnico);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Técnico Registrado com sucesso!", ""));
		
		this.nomeEmpresa = "";
		this.tecnico = new Tecnico();
		return null;
	}
	
	public void tecnicoSelecionado(SelectEvent event) 
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
		}
		
	}
	//==========================================================================================
	
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
	
	private String formatYear(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		
		return dateFormat.format(data);
	}
	
	public ControleChave getControleChave() {
		return controleChave;
	}
	public void setControleChave(ControleChave controleChave) {
		this.controleChave = controleChave;
	}

	public boolean isFormControleChave() {
		return formControleChave;
	}

	public void setFormControleChave(boolean formControleChave) {
		this.formControleChave = formControleChave;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public TecnicoRN getTecnicoRN() {
		return tecnicoRN;
	}

	public void setTecnicoRN(TecnicoRN tecnicoRN) {
		this.tecnicoRN = tecnicoRN;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
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

	public EmpresaRN getEmpresaRN() {
		return empresaRN;
	}

	public void setEmpresaRN(EmpresaRN empresaRN) {
		this.empresaRN = empresaRN;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	
}
