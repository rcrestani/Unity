package movimentacao.web;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteServiceLocator;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SQLException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.ControleChaveFiltro;
import movimentacao.projetoNCE.ControleChaveRN;
import movimentacao.projetoNCE.LazyControleChaveDataModel;
import movimentacao.projetoNCE.chave.Chave;
import movimentacao.projetoNCE.chave.ChaveRN;
import movimentacao.projetoNCE.controleSiteChave.ControleSiteChave;
import movimentacao.projetoNCE.controleSiteChave.ControleSiteChaveRN;
import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.projetoNCE.empresa.EmpresaRN;
import movimentacao.projetoNCE.site.Site;
import movimentacao.projetoNCE.site.SiteRN;
import movimentacao.projetoNCE.tecnico.Tecnico;
import movimentacao.projetoNCE.tecnico.TecnicoRN;
import movimentacao.usuario.Usuario;
import movimentacao.usuario.UsuarioRN;
import movimentacao.util.DateCalculator;

@ManagedBean(name = "nceControleChaveBean")
@ViewScoped
public class NceControleChaveBean implements Serializable
{
	private static final long serialVersionUID = 7741636814187218597L;
	
	private ControleChave controleChave = new ControleChave();
	private ControleChaveRN controleChaveRN = new ControleChaveRN();
	private UsuarioRN usuarioRN = new UsuarioRN();
	private Tecnico tecnico;
	private TecnicoRN tecnicoRN = new TecnicoRN();
	private Empresa empresa = new Empresa();
	private EmpresaRN empresaRN = new EmpresaRN();
	private Site site = new Site();
	private SiteRN siteRN = new SiteRN();
	private List<Site> listaSites;
	private Chave chave = new Chave();
	private ChaveRN chaveRN = new ChaveRN();
	private List<Chave> listaChaves = new ArrayList<Chave>();
	private int totalListaChaves = 0;
	private List<ControleSiteChave> listaSiteChave = new ArrayList<ControleSiteChave>();
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
	private String usuario = "";
	private String cpfTecnico = "";
	private String nomeEmpresa = "";
	private String cep = "";
	private Date data48hsAtras;
	private String rowColorVencido = "";
	private String rowColorAberto = "";
	private String tempoAberto = "";
	private String placeHolderObs = "";//Dica para quando for usuário da recepção abrindo requisição.
	
	//Variaveis de controle de formulario===============================
	private boolean formControleChave = false;
	private boolean formSiteChave = false;
	private boolean siteChaveRequisitado = false;
	private boolean stopPoll = false;
	//==================================================================
	private ControleChaveFiltro filtro = new ControleChaveFiltro();
	private LazyControleChaveDataModel lazyControleChave;
	
	public LazyControleChaveDataModel getLazyControleChave() {
		return lazyControleChave;
	}

	@PostConstruct
	public void init()
	{
		this.lazyControleChave = new LazyControleChaveDataModel(this.controleChaveRN, this.filtro);
		calcTempoAberto();
	}
	
	public String recuperarId()
	{
		if(this.controleChave.getId() == null)
		{	
			//controle do numero da requisicao é gerado pela requisicao já gerada na atribuição dos sites==========
			int ultimoId = this.controleChaveRN.ultimoRegistro().getId() + 1;
			this.controleChave.setIdAno(String.format("%d",  ultimoId) + "/" + formatYear(new Date()) );
			
			this.controleChave.setUsuarioAbertura(this.usuarioRN.buscarPorLogin(this.login));
			this.controleChaveRN.salvar(this.controleChave);
			
			int id = this.controleChave.getId();
			this.controleChave = new ControleChave();
			this.controleChave = this.controleChaveRN.carregar(id);
		}
		
		return null;
	}
	
	public String salvarAtendimento()
	{				
		Usuario user = new Usuario();
		user = this.usuarioRN.buscarPorLogin(this.login);
		//Controle da data e usuario de atendimento pela verificacao da permissao==========================================
		if(user.getPermissao().contains("ROLE_CONTROLE_CHAVE_OPER"))
		{
			this.controleChave.setUsuarioAtendimento(this.usuarioRN.buscarPorLogin(this.login));
			
			if(this.controleChave.getDataAtendimento() == null) {this.controleChave.setDataAtendimento(new Date());}
		}
		else if(user.getPermissao().contains("ROLE_CONTROLE_CHAVE_ADM"))
		{
			this.controleChave.setUsuarioAtendimento(this.usuarioRN.buscarPorLogin(this.login));
			
			if(this.controleChave.getDataAtendimento() == null) {this.controleChave.setDataAtendimento(new Date());}
		}
		
		//Verificando se pelo menos um site foi selecionado========================================
		if(this.listaSiteChave.size() > 0)
		{
			if(this.controleChave.getIdAno() == null)
			{	
				//controle do numero da requisicao é gerado pela requisicao já gerada na atribuição dos sites==========
				int ultimoId = this.controleChaveRN.ultimoRegistro().getId() + 1;
				this.controleChave.setIdAno(String.format("%d",  ultimoId) + "/" + formatYear(new Date()) );
			}
			
			this.controleChaveRN.salvar(this.controleChave);
			
			//Setando o ID da requisicao em cada chave, para fins de rastreio das chaves em campo=======================
			for(int x = 0;x < this.listaChaves.size();x++)
			{
				this.listaChaves.get(x).setIdControleChave(this.controleChave);
				
				this.chaveRN.salvar(this.listaChaves.get(x));
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Requisição Registrada com sucesso!", ""));
			
			this.formControleChave = false;
			this.listaSiteChave.clear();
			this.listaChaves.clear();
			this.site = new Site();
			this.chave = new Chave();
			this.tecnico = new Tecnico();
			
			return null;
		}
		else if(user.getPermissao().contains("ROLE_CONTROLE_CHAVE_RECEP"))//Se for usuario da recepcao, permitir salvar requisicao sem os sites.
		{
			if(this.controleChave.getIdAno() == null)
			{	
				//controle do numero da requisicao é gerado pela requisicao já gerada na atribuição dos sites==========
				int ultimoId = this.controleChaveRN.ultimoRegistro().getId() + 1;
				this.controleChave.setIdAno(String.format("%d",  ultimoId) + "/" + formatYear(new Date()) );
			}
			
			this.controleChave.setUsuarioAbertura(user);
			this.controleChaveRN.salvar(this.controleChave);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Requisição Registrada com sucesso!", ""));
			
			this.formControleChave = false;
			this.listaSiteChave.clear();
			this.listaChaves.clear();
			this.site = new Site();
			this.chave = new Chave();
			this.tecnico = new Tecnico();
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Obrigatório informar qual o site e as chaves requisitadas", ""));
		}
		
		this.stopPoll = false;
		return null;
	}
	
	public String novo()
	{
		this.controleChave = new ControleChave();
		
		Usuario user = new Usuario();
		user = this.usuarioRN.buscarPorLogin(this.login);
		
		if(user.getPermissao().contains("ROLE_CONTROLE_CHAVE_RECEP"))//Se for usuario da recepcao, setar dica na observação.
		{
			this.placeHolderObs = "Qual site o técnico necessita acesso?";
		}
		
		this.formControleChave = true;
		this.formSiteChave = false;
		this.siteChaveRequisitado = false;
		this.stopPoll = true;
		this.controleChave.setDataAbertura(new Date());
		this.usuario = this.usuarioRN.buscarPorLogin(this.login).getNome();
		this.tecnico = new Tecnico();
		this.cpfTecnico = "";
		this.empresa = new Empresa();
		this.site = new Site();
		this.chave = new Chave();
		this.nomeEmpresa = "";
		this.cep = "";
		
		return null;
	}
	
	public String atenderRequisicao()
	{		
		this.cpfTecnico = this.controleChave.getIdTecnico().getCpf();
		this.usuario = this.usuarioRN.buscarPorLogin(this.controleChave.getUsuarioAbertura().getLogin()).getNome();
		
		this.formControleChave = true;
		this.formSiteChave = false;
		this.siteChaveRequisitado = false;
		this.stopPoll = true;
		this.site = new Site();
		this.chave = new Chave();
		this.nomeEmpresa = "";
		this.cep = "";
		
		return null;
	}
	
	public void calcTempoAberto()
	{
		DateCalculator calcDate = new DateCalculator();
		List<ControleChave> listControle = new ArrayList<ControleChave>();
		listControle = this.controleChaveRN.listarAberto();
		
		for(ControleChave controlChave:listControle)
		{
			try
			{
				controlChave.setTempoAberto(calcDate.calculaHoras(controlChave.getDataAbertura(), new Date()));
				
				this.controleChaveRN.salvar(controlChave);
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
			
		}
		
	}
	
	//Método para informar que o prazo de devolução das chaves passou das 48 horas estipuladas=========================
	public String prazoVencido(ControleChave controleChave)
	{
		DateCalculator calcDate = new DateCalculator();
		this.data48hsAtras = calcDate.data48HorasAtras(new Date());
		
		try
		{
			if(controleChave.getDataAbertura().before(this.data48hsAtras) && controleChave.getDataFechamento() == null)
			{
				this.rowColorVencido = "#CD3333;font-weight: bold;";
			}
			else
			{
				this.rowColorVencido = "";
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
		return this.rowColorVencido;
	}
	
	//Método para informar que a abertura passou de 15 minutos do prazo estipulado====================================
	public String aberturaVencida(ControleChave controleChave)
	{
		DateCalculator calcDate = new DateCalculator();
		this.data48hsAtras = calcDate.data15MinAtras(new Date());
		
		try
		{
			if(controleChave.getDataAbertura().before(this.data48hsAtras) && controleChave.getDataFechamento() == null)
			{
				this.rowColorAberto = "#CD3333;font-weight: bold;";
			}
			else
			{
				this.rowColorAberto = "";
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
		return this.rowColorAberto;
	}
	
	//Método para controlar a exibição do botão atender da datatable===========================
	public boolean botaoAtender(ControleChave controleChave)
	{
		this.controleChave = controleChave;
		if(this.controleChave.getDataAtendimento() == null)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	//MÉTODOS PARA O BEAN TECNICO===============================================================
	public String salvarTecnico()
	{
		this.tecnico.setIdEmpresa(this.empresaRN.empresaPorNome(this.nomeEmpresa));
		this.tecnico.setDataHoraReg(new Date());
		this.tecnico.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		
		boolean controle = true;
		try
		{
			this.tecnicoRN.salvar(this.tecnico);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Técnico Registrado com sucesso!", ""));
		}
		catch (Exception e)
		{
			controle = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_FATAL ,
		    		"Técnico não registrado!", "Dados inválidos. Verifique se o técnico já possui cadastro."));
		}
		
		if(controle)
		{
			this.cpfTecnico = "";
			this.nomeEmpresa = "";
			this.tecnico = new Tecnico();
		}
		
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados do Técnico!", ""));
		}
	}
	
	public void tecnicoPorCPF() 
	{
		//executar apenas se o cpf com a mascará conter 14 caracteres====================================
		 if (this.cpfTecnico.length() == 14)
		 {
			 //teste da busca, se retornar null, instancia novo tecnico============
			 if(this.tecnicoRN.tecnicoPorCPF(this.cpfTecnico) != null)
			 {
				 this.controleChave.setIdTecnico(this.tecnicoRN.tecnicoPorCPF(this.cpfTecnico));
			 }
			 else
			 {
				 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR , "Técnico não encontrado", ""));
			 }
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
	
	//MÉTODOS PARA O BEAN SITE=====================================================================================
	public String salvarSite()
	{
		this.site.setDataHoraReg(new Date());
		this.site.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
				
		this.siteRN.salvar(this.site);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Site Registrado com sucesso!", ""));
		
		this.cep = "";
		this.site = new Site();
		
		return null;
	}
	
	public void buscarEnderecoSite(ActionEvent actionEvent) throws MalformedURLException, ServiceException, SQLException, SigepClienteException, RemoteException
	{
		AtendeClienteServiceLocator serviceCorreios = new AtendeClienteServiceLocator();
		AtendeCliente atendeCliente = serviceCorreios.getAtendeClientePort(
				new URL("https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl"));
		EnderecoERP correios = new EnderecoERP();
		correios = atendeCliente.consultaCEP(this.cep);
		
		this.cep = correios.getCep();
		this.site.setEndereco(correios.getEnd());
		this.site.setBairro(correios.getBairro());
		this.site.setCidade(correios.getCidade());
		this.site.setUf(correios.getUf());
		
	}
	
	public void siteSelecionado()
	{
		String siteIdAtual = this.site.getIdCodAtual();
		
		if(this.siteRN.sitePorIdCodAtual(siteIdAtual) == null)
		{
			this.site = new Site();
			this.site.setIdCodAtual(siteIdAtual);
		}
		else
		{
			try
			{
				this.site = this.siteRN.sitePorIdCodAtual(siteIdAtual);
				this.cep = this.site.getCep();
			}
			catch (Exception e)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
			}
		}
		
	}
	//FIM MÉTODOS SITE=============================================================================================
	
	//MÉTODOS PARA O FORM SITECHAVE================================================================================
	public void fecharDialogSiteChave(CloseEvent event)
	{
		//Pegando as chaves selecionadas e setando false na selecao, caso o usuario fechar o Dialog sem salvar=====
		for(int y = 0; y < this.listaChaves.size(); y++)
		{
			if(this.listaChaves.get(y).isSelecao())
			{
				this.listaChaves.get(y).setSelecao(false);
				this.chaveRN.salvar(this.listaChaves.get(y));
			}
			
		}
		
		this.listaChaves.clear();
		this.site = new Site();
		this.chave = new Chave();
		this.tecnico = new Tecnico();
	}

	public String atribuirSite()
	{
		recuperarId();
		List<Chave> listaChave  = new ArrayList<Chave>();
		
		/*Pegando as chaves selecionadas e setando o idControleChave e os booleanos transito para true e selecao para false
		 * Selecao deve ser setada para false para não interferir nas novas requisições
		 * Também setando a lista de chaves auxiliar para fazer a concatenação de exibição no formulário*/
		for(int y = 0; y < this.listaChaves.size(); y++)
		{
			if(this.listaChaves.get(y).isSelecao())
			{
				this.listaChaves.get(y).setTransito(true);
				this.listaChaves.get(y).setSelecao(false);
				this.listaChaves.get(y).setIdControleChave(this.controleChave);
				this.chaveRN.salvar(this.listaChaves.get(y));
				
				listaChave.add(this.listaChaves.get(y));
			}
			
		}
		
		//Atribuir site e as chaves, se existirem chaves selecionadas=================================================================
		if(listaChave.size() > 0)
		{
			//Objeto auxiliar para a exibição dos sites e suas respectivas chaves concatenadas no form cadastro=======================
			ControleSiteChaveRN siteChaveRN = new ControleSiteChaveRN();
			ControleSiteChave siteChave = new ControleSiteChave();
			siteChave.setIdReq(this.controleChave);
			siteChave.setSiteIdCodAtual(this.site.getIdCodAtual());
				
			//Concatenando as chaves para exibição no form cadastro=============================
			String chavesConcatenadas = "";
			for(int x = 0; x < listaChave.size(); x++)
			{	
				chavesConcatenadas = chavesConcatenadas + listaChave.get(x).getNome();
				
				if(x < listaChave.size() - 1)
				{
					chavesConcatenadas = chavesConcatenadas + ", ";
				}
				
			}
			
			//Atualizando e salvando o objeto com as chaves concatenadas====================
			siteChave.setListaChaves(chavesConcatenadas);
			siteChaveRN.salvar(siteChave);
			
			this.listaSiteChave.add(siteChave);
			this.siteChaveRequisitado = true;
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Deve-se selecionar pelo menos uma chave para atribuir na requisição!", ""));
		}
		
		this.formSiteChave = false;
		this.listaChaves.clear();
		this.site = new Site();
		this.chave = new Chave();
		
		return null;
	}
	
	public void atribuirNovoSite(SelectEvent event)
	{
		this.listaChaves.clear();
		System.out.println("========>teste de execução");
	}
	
	//Método utilizado pelo componente autoComplete==========================
	public void siteRequisitado(SelectEvent event)
	{
		String siteIdAtual = this.site.getIdCodAtual();
		this.site = new Site();
		
		//Variavel auxiliar para a consulta das chaves válidas abaixo
		boolean consultaSite = false;
		
		//Try Catch para tratar a consulta do site requisitado====================
		try
		{
			this.site = this.siteRN.sitePorIdCodAtual(siteIdAtual);
			this.formSiteChave = true;
			consultaSite = true;
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao Localizar dados completos!", ""));
		}
		
		//Condicional para pegar apenas as chaves válidas============================
		if(consultaSite == true)
		{
			this.listaChaves = this.chaveRN.listaPorSiteStatusTrue(this.site);
			this.totalListaChaves = this.listaChaves.size();
		}
		
	}
	
	public String salvarChave()
	{
		this.chave.setSelecao(false);
		this.chave.setTransito(false);
		this.chave.setStatus(true);
		this.chave.setIdSite(this.site);
		this.chave.setDataHoraReg(new Date());
		this.chave.setUsuario(this.usuarioRN.buscarPorLogin(this.login));
		
		this.chaveRN.salvar(this.chave);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	    		FacesMessage.SEVERITY_INFO , "Chave Registrada com sucesso!", ""));
		
		this.listaChaves.add(this.chave);
		this.chave = new Chave();
		
		return null;
	}
	
	public String selecionarChave()
	{
		if(this.chave.isSelecao())
		{
			this.chave.setSelecao(false);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Chave Não Selecionada!", ""));
		}
		else
		{
			this.chave.setSelecao(true);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Chave Selecionada!", ""));
		}
		
		this.chaveRN.salvar(this.chave);
		
		
		
		return null;
	}
	//FIM MÉTIDOS SITECHAVE========================================================================================
	
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

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public SiteRN getSiteRN() {
		return siteRN;
	}

	public void setSiteRN(SiteRN siteRN) {
		this.siteRN = siteRN;
	}

	public boolean isFormSiteChave() {
		return formSiteChave;
	}

	public void setFormSiteChave(boolean formSiteChave) {
		this.formSiteChave = formSiteChave;
	}

	public Chave getChave() {
		return chave;
	}

	public void setChave(Chave chave) {
		this.chave = chave;
	}

	public ChaveRN getChaveRN() {
		return chaveRN;
	}

	public void setChaveRN(ChaveRN chaveRN) {
		this.chaveRN = chaveRN;
	}

	public List<Chave> getListaChaves() {
		return listaChaves;
	}

	public void setListaChaves(List<Chave> listaChaves) {
		this.listaChaves = listaChaves;
	}

	public int getTotalListaChaves() {
		return totalListaChaves;
	}

	public void setTotalListaChaves(int totalListaChaves) {
		this.totalListaChaves = totalListaChaves;
	}

	public List<Site> getListaSites() {
		return listaSites;
	}

	public void setListaSites(List<Site> listaSites) {
		this.listaSites = listaSites;
	}

	public List<ControleSiteChave> getListaSiteChave() {
		return listaSiteChave;
	}

	public void setListaSiteChave(List<ControleSiteChave> listaSiteChave) {
		this.listaSiteChave = listaSiteChave;
	}

	public boolean isSiteChaveRequisitado() {
		return siteChaveRequisitado;
	}

	public void setSiteChaveRequisitado(boolean siteChaveRequisitado) {
		this.siteChaveRequisitado = siteChaveRequisitado;
	}

	public ControleChaveFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(ControleChaveFiltro filtro) {
		this.filtro = filtro;
	}

	public Date getData48hsAtras() {
		return data48hsAtras;
	}

	public void setData48hsAtras(Date data48hsAtras) {
		this.data48hsAtras = data48hsAtras;
	}

	public String getRowColorVencido() {
		return rowColorVencido;
	}

	public void setRowColorVencido(String rowColorVencido) {
		this.rowColorVencido = rowColorVencido;
	}

	public String getTempoAberto() {
		return tempoAberto;
	}

	public void setTempoAberto(String tempoAberto) {
		this.tempoAberto = tempoAberto;
	}

	public String getRowColorAberto() {
		return rowColorAberto;
	}

	public void setRowColorAberto(String rowColorAberto) {
		this.rowColorAberto = rowColorAberto;
	}

	public boolean isStopPoll() {
		return stopPoll;
	}

	public void setStopPoll(boolean stopPoll) {
		this.stopPoll = stopPoll;
	}

	public String getCpfTecnico() {
		return cpfTecnico;
	}

	public void setCpfTecnico(String cpfTecnico) {
		this.cpfTecnico = cpfTecnico;
	}

	public String getPlaceHolderObs() {
		return placeHolderObs;
	}

	public void setPlaceHolderObs(String placeHolderObs) {
		this.placeHolderObs = placeHolderObs;
	}
	
}
