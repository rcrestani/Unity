package movimentacao.web;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.primefaces.model.StreamedContent;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteServiceLocator;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SQLException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.ControleChaveFiltro;
import movimentacao.projetoNCE.ControleChaveProtocolo;
import movimentacao.projetoNCE.ControleChaveRN;
import movimentacao.projetoNCE.LazyControleChaveDataModel;
import movimentacao.projetoNCE.anotacao.Anotacao;
import movimentacao.projetoNCE.anotacao.AnotacaoRN;
import movimentacao.projetoNCE.chave.Chave;
import movimentacao.projetoNCE.chave.ChaveRN;
import movimentacao.projetoNCE.controleSiteChave.ControleSiteChave;
import movimentacao.projetoNCE.controleSiteChave.ControleSiteChaveRN;
import movimentacao.projetoNCE.emails.NivelEmail;
import movimentacao.projetoNCE.emails.NivelEmailRN;
import movimentacao.projetoNCE.empresa.Empresa;
import movimentacao.projetoNCE.empresa.EmpresaRN;
import movimentacao.projetoNCE.site.Site;
import movimentacao.projetoNCE.site.SiteRN;
import movimentacao.projetoNCE.status.StatusRequisicao;
import movimentacao.projetoNCE.status.StatusRequisicaoRN;
import movimentacao.projetoNCE.tecnico.Tecnico;
import movimentacao.projetoNCE.tecnico.TecnicoRN;
import movimentacao.usuario.Usuario;
import movimentacao.usuario.UsuarioRN;
import movimentacao.util.DateCalculator;
import movimentacao.util.JavaMailApp;
import movimentacao.util.UtilException;
import movimentacao.web.util.RelatorioUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	private List<Chave> listaGeralChaves = new ArrayList<Chave>();
	private int totalListaChaves = 0;
	private ControleSiteChaveRN siteChaveRN = new ControleSiteChaveRN();
	private ControleSiteChave siteChave = new ControleSiteChave();
	private List<ControleSiteChave> listaSiteChave = new ArrayList<ControleSiteChave>();
	private StatusRequisicao status = new StatusRequisicao();
	private StatusRequisicaoRN statusRN = new StatusRequisicaoRN();
	private String nomeStatus = "";
	private Anotacao anotacao = new Anotacao();
	private AnotacaoRN anotacaoRN = new AnotacaoRN();
	private List<Anotacao> listaAnotacao = new ArrayList<Anotacao>();
	private String usuario = "";
	private String nomeTecnico = "";
	private String cpfTecnico = "";
	private String nomeEmpresa = "";
	private String cep = "";
	private Date data48hsAtras;
	private Date data15MinAtras;
	private String rowColorVencido = "";
	private String tempoAberto = "";
	private String placeHolderObs = "";//Dica para quando for usuário da recepção abrindo requisição.
	
	//Variaveis de controle de formulario===============================
	private boolean formControleChave = false;
	private boolean formSiteChave = false;
	private boolean siteChaveRequisitado = false;
	private boolean stopPoll = false;
	private boolean campoStatus = false;
	private boolean formAnotacao = false;
	private boolean formFechamento = false;
	//==================================================================
	//Variaveis para gerar relatorios===================================
	private StreamedContent protocoloEntrega;
	private int tipoRelatorio;
	//==================================================================
	//Dados da sessão===================================================
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
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
	
	public String consultar()
	{
		this.tecnico = this.tecnicoRN.tecnicoPorNome(this.nomeTecnico);
		this.filtro.setIdTecnico(this.tecnico);
		
		return null;
	}
	
	public String salvarAtendimento()
	{				
		Usuario user = new Usuario();
		user = this.usuarioRN.buscarPorLogin(this.login);
		
		//Controle da data e usuario de atendimento pela verificacao da permissao==========================================
		if(user.getPermissao().contains("ROLE_CONTROLE_CHAVE_OPER") || user.getPermissao().contains("ROLE_CONTROLE_CHAVE_ADM"))
		{
			this.controleChave.setUsuarioAtendimento(this.usuarioRN.buscarPorLogin(this.login));
			
			if(this.controleChave.getDataAtendimento() == null) {this.controleChave.setDataAtendimento(new Date());}
		}
		
		//Verificando se pelo menos um site foi selecionado========================================
		if(this.listaSiteChave.size() > 0)
		{	
			//Verificando a função do status, se for de liberação apenas salva a requisição, senão, seta com fechamento do usuário e data/hora
			this.status = this.statusRN.buscarPorNome(this.nomeStatus);
			if(this.status.isFuncao())
			{
				this.controleChave.setStatus(this.status);
				this.controleChaveRN.salvar(this.controleChave);
				
				//Salvando o ControleSiteChave que é o histórico de movimentações das chaves
				for(ControleSiteChave siteChaveAux:this.listaSiteChave)
				{
					siteChaveAux.setIdReq(this.controleChave);
					this.siteChaveRN.salvar(siteChaveAux);
				}
				
				/*Pegando as chaves selecionadas e setando o idControleChave e os booleanos transito para true e selecao para false
				 * Selecao deve ser setada para false para não interferir nas novas requisições
				 */
				for(Chave chaveAux:this.listaGeralChaves)
				{					
					Chave chaveNova = new Chave();
					/*Foi inserido este DO WHILE para resolver o bug de update do último index da listaGeralchaves,
					pois o hibernate não estava atualizando o último registro no BD.*/
					do
					{
						chaveNova = this.chaveRN.chavePorNome(chaveAux.getNome());
						
						chaveNova.setTransito(true);
						chaveNova.setSelecao(false);
						chaveNova.setIdControleChave(this.controleChave);
						this.chaveRN.salvar(chaveNova);
						
						chaveNova = this.chaveRN.chavePorNome(chaveNova.getNome());
					
					}while(chaveNova.getIdControleChave() == null);
				}
				
				//Salvando o id/ano na requisição, mas verificando se é null, pois a requisição pode já estar registrada pela recepção
				if(this.controleChave.getIdAno() == null)
				{	
					this.controleChave.setIdAno(String.format("%d",  this.controleChave.getId()) + "/" + formatYear(new Date()) );
					this.controleChaveRN.salvar(this.controleChave);
				}
				
			}
			else
			{
				//Removendo a seleção das chaves em função do status da requisição ser de bloqueio=======================
				for(int x = 0;x < this.listaChaves.size();x++)
				{
					if(this.listaChaves.get(x).isSelecao())
					{
						this.listaChaves.get(x).setSelecao(false);
						this.listaChaves.get(x).setTransito(false);
						this.listaChaves.get(x).setIdControleChave(null);
						this.chaveRN.salvar(this.listaChaves.get(x));
					}
					
				}
				
				this.controleChave.setUsuarioFechamento(user);
				this.controleChave.setDataFechamento(new Date());
				this.controleChave.setStatus(this.status);
				this.controleChaveRN.salvar(this.controleChave);
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Requisição Registrada com sucesso!", ""));
			
			this.formControleChave = false;
			this.stopPoll = false;
			this.listaSiteChave.clear();
			this.site = new Site();
			this.chave = new Chave();
			this.tecnico = new Tecnico();
			this.nomeStatus = "";
			
			return null;
		}
		else if(user.getPermissao().contains("ROLE_CONTROLE_CHAVE_RECEP"))//Se for usuario da recepcao, permitir salvar requisicao sem os sites.
		{
			/*if(this.controleChave.getIdAno() == null)
			{	
				//controle do numero da requisicao é gerado pela requisicao já gerada na atribuição dos sites==========
				int ultimoId = this.controleChaveRN.ultimoRegistro().getId() + 1;
				this.controleChave.setIdAno(String.format("%d",  ultimoId) + "/" + formatYear(new Date()) );
			}*/
			
			this.controleChave.setUsuarioAbertura(user);
			this.controleChaveRN.salvar(this.controleChave);
			
			//Salvando o id/ano na requisição
			this.controleChave.setIdAno(String.format("%d",  this.controleChave.getId()) + "/" + formatYear(new Date()) );
			this.controleChaveRN.salvar(this.controleChave);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Requisição Registrada com sucesso!", ""));
			
			this.formControleChave = false;
			this.stopPoll = false;
			this.listaSiteChave.clear();
			this.site = new Site();
			this.chave = new Chave();
			this.tecnico = new Tecnico();
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Obrigatório informar qual o site e as chaves requisitadas", ""));
		}
		
		return null;
	}
	
	public String novo()
	{
		this.controleChave = new ControleChave();
		
		Usuario user = new Usuario();
		user = this.usuarioRN.buscarPorLogin(this.login);
		
		//Se for usuario da recepcao: setar dica na observação/bloquear campo status, setar com status liberado e adicionar status ao controleChave
		if(user.getPermissao().contains("ROLE_CONTROLE_CHAVE_RECEP"))
		{
			this.placeHolderObs = "Qual site o técnico necessita acesso?";
			
			this.campoStatus = true;
			
			//Pegando o status ID 1 de liberação da requisição
			this.status = this.statusRN.carregar(1);
			
			this.nomeStatus = this.status.getNomeStatus();
			this.controleChave.setStatus(this.status);
		}
		
		this.formControleChave = true;
		this.formSiteChave = false;
		this.listaChaves.clear();
		this.listaGeralChaves.clear();
		this.siteChaveRequisitado = false;
		this.stopPoll = true;
		this.controleChave.setDataAbertura(new Date());
		this.controleChave.setUsuarioAbertura(this.usuarioRN.buscarPorLogin(this.login));
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
		try
		{
			this.cpfTecnico = this.controleChave.getIdTecnico().getCpf();
		}
		catch (Exception e)
		{
			System.out.println("AÇÃO ATENDER REQUISIÇÃO: ERRO AO BUSCAR TÉCNICO PARA EXIBIR O CPF NO ATENDIMENTO DA REQUISIÇÃO\n"
					+ e.getMessage());
		}
		
		this.usuario = this.usuarioRN.buscarPorLogin(this.controleChave.getUsuarioAbertura().getLogin()).getNome();
		
		this.formControleChave = true;
		this.campoStatus = false;
		this.nomeStatus = this.controleChave.getStatus().getNomeStatus();
		this.formSiteChave = false;
		this.listaChaves.clear();
		this.listaGeralChaves.clear();
		this.siteChaveRequisitado = false;
		this.stopPoll = true;
		this.site = new Site();
		this.chave = new Chave();
		this.nomeEmpresa = "";
		this.cep = "";
		
		return null;
	}
	
	public String carregarFechamento()
	{
		ControleSiteChaveRN siteChaveRN = new ControleSiteChaveRN();
		this.listaSiteChave.clear();
		this.listaSiteChave = siteChaveRN.sitesChavesPorReq(this.controleChave);
		
		if(this.controleChave.getDataFechamento() != null)
		{
			this.formFechamento = true;
		}
		else
		{
			this.formFechamento = false;
		}
		
		return null;
	}
	
	private void liberarChaves()
	{
		ControleSiteChaveRN siteChaveRN = new ControleSiteChaveRN();
		List<ControleSiteChave> siteChave = siteChaveRN.sitesChavesPorReq(this.controleChave);
		
		
		
		for(ControleSiteChave sc:siteChave)
		{
			String [] chavesSplit = sc.getListaChaves().split(", ");
			List<String> chavesList = Arrays.asList(chavesSplit);
			
			for(String chaves:chavesList)
			{
				Chave chaveAux = new Chave();
				
				/*Foi inserido este DO WHILE para resolver o bug de update do último index da chavesList,
				pois o hibernate não estava atualizando o último registro no BD.*/
				do
				{
					chaveAux = this.chaveRN.chavePorNome(chaves);
					chaveAux.setIdControleChave(null);
					chaveAux.setSelecao(false);
					chaveAux.setTransito(false);
					
					this.chaveRN.salvar(chaveAux);
					
					//Instanciando novo objeto para recarregar a chave processada
					Chave chaveAux2 = new Chave();
					chaveAux2 = this.chaveRN.chavePorNome(chaves);
					
					//Passando a nova chave recarregada para a chave processada
					//Assim o while faz o teste com os dados carregados do BD
					chaveAux = chaveAux2;
					
				} while (chaveAux.getIdControleChave() != null);
			}

		}
	}
	
	public String fecharRequisicao()
	{
		if(this.controleChave.getDataFechamento() == null)
		{
			DateCalculator calcDate = new DateCalculator();
			this.data48hsAtras = calcDate.data48HorasAtras(new Date());
			
			boolean devForaPrazo = this.controleChave.getDataAbertura().before(this.data48hsAtras)
									&& this.controleChave.getDataFechamento() == null;
			
			JavaMailApp jMail = new JavaMailApp();
			NivelEmailRN nivelEmailRN = new NivelEmailRN();
			List<NivelEmail> listaNivelEmail = new ArrayList<NivelEmail>();
			String nivel1 = "";
			
			if(this.controleChave.isStatusFechamento() && devForaPrazo == false)
			{
				liberarChaves();
				this.controleChave.setUsuarioFechamento(this.usuarioRN.buscarPorLogin(this.login));
				this.controleChave.setDataFechamento(new Date());
				this.controleChave.setReqFechada(true);
				
				this.controleChaveRN.salvar(this.controleChave);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , "Requisição encerrada com sucesso!", ""));
			}
			else if(this.controleChave.isStatusFechamento() == false && devForaPrazo)
			{
				this.tecnico = this.tecnicoRN.carregar(this.controleChave.getIdTecnico().getId());
				this.tecnico.setTempBlock(calcDate.dataAdd72Horas(new Date()));
				this.tecnico.setStatus(false);
				
				this.tecnicoRN.salvar(this.tecnico);
				
				this.controleChave.setUsuarioFechamento(this.usuarioRN.buscarPorLogin(this.login));
				this.controleChave.setDataFechamento(new Date());
				this.controleChave.setReqFechada(true);
				
				this.controleChaveRN.salvar(this.controleChave);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , "Requisição encerrada com sucesso!", ""));
				
				listaNivelEmail = nivelEmailRN.listar();
				for(int x = 0; x < listaNivelEmail.size(); x++)
				{
					if(listaNivelEmail.get(x).getNivel().equals("nivel1"))
					{
						nivel1 = nivel1 + listaNivelEmail.get(x).getEmail();
						if(x < listaNivelEmail.size() - 1) {nivel1 = nivel1 + ", ";}
					}
				}
				
				jMail.setDestinatario(nivel1);
				jMail.setAssunto("Alerta! - Requisição " + this.controleChave.getIdAno() + " encerrada com pendências e fora do prazo");
				jMail.setMsg("<center><h2>Requisição Encerrada com Pendências e fora do Prazo</h2></center>"
							+"<br>Olá," 
							+"<br><br>A requisição " + this.controleChave.getIdAno() + ", foi encerrada com pendências e fora do prazo das 48 horas:"
							+"<br><br>"
							+ "<br><b>Fechado por:</b> " + this.controleChave.getUsuarioFechamento().getNome()
							+ "<br><b>Data Fechamento:</b> " + formatDateHour(this.controleChave.getDataFechamento())
							+ "<br><b>Técnico:</b> " + this.controleChave.getIdTecnico().getNome()
							+ "<br><b>Celular:</b> " + this.controleChave.getIdTecnico().getCelular()
							+ "<br><b>CPF:</b> " + this.controleChave.getIdTecnico().getCpf()
							+ "<br><b>CRQ:</b> " + this.controleChave.getCrq()
							+ "<br><b>Tempo Aberto:</b> " + this.controleChave.getTempoAberto()
							+ "<br><b>Obs:</b> " + this.controleChave.getObsFechamento()
							+ "<br><br>Atenciosamente,"
							+ "<br><br><b><i>Performancelab Sistemas</i></b>");
				
				new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							jMail.sendMail();
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}.start();
			}
			else if(this.controleChave.isStatusFechamento() == false)
			{
				this.controleChave.setUsuarioFechamento(this.usuarioRN.buscarPorLogin(this.login));
				this.controleChave.setDataFechamento(new Date());
				this.controleChave.setReqFechada(true);
				
				this.controleChaveRN.salvar(this.controleChave);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , "Requisição encerrada com sucesso!", ""));
				
				listaNivelEmail = nivelEmailRN.listar();
				for(int x = 0; x < listaNivelEmail.size(); x++)
				{
					if(listaNivelEmail.get(x).getNivel().equals("nivel1"))
					{
						nivel1 = nivel1 + listaNivelEmail.get(x).getEmail();
						if(x < listaNivelEmail.size() - 1) {nivel1 = nivel1 + ", ";}
					}
				}
				
				jMail.setDestinatario(nivel1);
				jMail.setAssunto("Alerta! - Requisição " + this.controleChave.getIdAno() + " encerrada com pendências");
				jMail.setMsg("<center><h2>Requisição Encerrada com Pendências</h2></center>"
							+"<br>Olá," 
							+"<br><br>A requisição " + this.controleChave.getIdAno() + ", foi encerrada com pendências:"
							+"<br><br>"
							+ "<br><b>Fechado por:</b> " + this.controleChave.getUsuarioFechamento().getNome()
							+ "<br><b>Data Fechamento:</b> " + formatDateHour(this.controleChave.getDataFechamento())
							+ "<br><b>Técnico:</b> " + this.controleChave.getIdTecnico().getNome()
							+ "<br><b>Celular:</b> " + this.controleChave.getIdTecnico().getCelular()
							+ "<br><b>CPF:</b> " + this.controleChave.getIdTecnico().getCpf()
							+ "<br><b>CRQ:</b> " + this.controleChave.getCrq()
							+ "<br><b>Tempo Aberto:</b> " + this.controleChave.getTempoAberto()
							+ "<br><b>Obs:</b> " + this.controleChave.getObsFechamento()
							+ "<br><br>Atenciosamente,"
							+ "<br><br><b><i>Performancelab Sistemas</i></b>");
				
				new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							jMail.sendMail();
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}.start();
			}
			else if(devForaPrazo)
			{
				liberarChaves();
				this.tecnico = this.tecnicoRN.carregar(this.controleChave.getIdTecnico().getId());
				this.tecnico.setTempBlock(calcDate.dataAdd72Horas(new Date()));
				this.tecnico.setStatus(false);
				
				this.tecnicoRN.salvar(this.tecnico);
				
				this.controleChave.setUsuarioFechamento(this.usuarioRN.buscarPorLogin(this.login));
				this.controleChave.setDataFechamento(new Date());
				this.controleChave.setReqFechada(true);
				
				this.controleChaveRN.salvar(this.controleChave);
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
			    		FacesMessage.SEVERITY_INFO , "Requisição encerrada com sucesso!", ""));
				
				listaNivelEmail = nivelEmailRN.listar();
				for(int x = 0; x < listaNivelEmail.size(); x++)
				{
					if(listaNivelEmail.get(x).getNivel().equals("nivel1"))
					{
						nivel1 = nivel1 + listaNivelEmail.get(x).getEmail();
						if(x < listaNivelEmail.size() - 1) {nivel1 = nivel1 + ", ";}
					}
				}
				
				jMail.setDestinatario(nivel1);
				jMail.setAssunto("Alerta! - Requisição " + this.controleChave.getIdAno() + " encerrada fora do prazo");
				jMail.setMsg("<center><h2>Requisição Encerrada fora do Prazo</h2></center>"
							+"<br>Olá," 
							+"<br><br>A requisição " + this.controleChave.getIdAno() + ", foi encerrada fora do prazo das 48 horas:"
							+"<br><br>"
							+ "<br><b>Fechado por:</b> " + this.controleChave.getUsuarioFechamento().getNome()
							+ "<br><b>Data Fechamento:</b> " + formatDateHour(this.controleChave.getDataFechamento())
							+ "<br><b>Técnico:</b> " + this.controleChave.getIdTecnico().getNome()
							+ "<br><b>Celular:</b> " + this.controleChave.getIdTecnico().getCelular()
							+ "<br><b>CPF:</b> " + this.controleChave.getIdTecnico().getCpf()
							+ "<br><b>CRQ:</b> " + this.controleChave.getCrq()
							+ "<br><b>Tempo Aberto:</b> " + this.controleChave.getTempoAberto()
							+ "<br><b>Obs:</b> " + this.controleChave.getObsFechamento()
							+ "<br><br>Atenciosamente,"
							+ "<br><br><b><i>Performancelab Sistemas</i></b>");
				
				new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							jMail.sendMail();
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}.start();
			}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR ,
		    		"Nenhuma alteração registrada! Esta requisição já foi fechada por "
		    		+ this.controleChave.getUsuarioFechamento().getNome(), ""));
		}
		
		this.listaSiteChave.clear();
		return null;
	}
	
	public void fecharDialogFechamento(CloseEvent event)
	{
		this.listaSiteChave.clear();
	}
	
	//Método para gerar relatório e fornecer download do arquivo===========================
	public StreamedContent getProtocoloEntrega() throws IOException
	{
		ControleChaveProtocolo protocolo;
		List<ControleChaveProtocolo> listaProtocolo = new ArrayList<ControleChaveProtocolo>();
		int qtdeChaves = 0;
		ControleSiteChaveRN siteChaveRN = new ControleSiteChaveRN();
		
		List<ControleSiteChave> listaSiteChave = new ArrayList<ControleSiteChave>();
		listaSiteChave = siteChaveRN.sitesChavesPorReq(this.controleChave);
		
		for(ControleSiteChave siteChaveQtde:listaSiteChave)
		{
			String [] chavesSplit = siteChaveQtde.getListaChaves().split(", ");
			qtdeChaves = qtdeChaves + chavesSplit.length;
		}
		
		for(ControleSiteChave siteChave:listaSiteChave)
		{
			protocolo = new ControleChaveProtocolo();
			
			protocolo.setIdAno(this.controleChave.getIdAno());
			protocolo.setData(this.controleChave.getDataAtendimento());
			protocolo.setTecnico(this.controleChave.getIdTecnico().getNome());
			protocolo.setEmpresa(this.controleChave.getIdTecnico().getIdEmpresa().getRazaoSocial());
			protocolo.setRg(this.controleChave.getIdTecnico().getRg());
			protocolo.setCelular(this.controleChave.getIdTecnico().getCelular());
			protocolo.setSite(this.siteRN.sitePorIdCodAtual(siteChave.getSiteIdCodAtual()).getIdCodAtual());
			protocolo.setChave(siteChave.getListaChaves());
			protocolo.setCrq(this.controleChave.getCrq());
			protocolo.setTotalChaves(qtdeChaves);
			protocolo.setUsuarioAtendimento(this.controleChave.getUsuarioAtendimento().getNome());
			protocolo.setHoraAbertura(formatTime(this.controleChave.getDataAbertura()));
			protocolo.setHoraAtendimento(formatTime(this.controleChave.getDataAtendimento()));
			
			listaProtocolo.add(protocolo);
		}
		
		/*for(ControleChaveProtocolo ptcl:listaProtocolo)
		{
			System.out.println("PROTOCOLO DE ENTREGA DE CHAVES"
								+"\nData: " + formatDateHour(ptcl.getData())
								+"\nTécnico: " + ptcl.getTecnico()
								+"\nEmpresa: " + ptcl.getEmpresa()
								+"\nSite: " + ptcl.getSite()
								+"\nChaves: " + ptcl.getChave()
								+"\nTotal Chaves: " + ptcl.getTotalChaves()
								+"\nUsuario: " + ptcl.getUsuarioAtendimento()
								+"\nHora Abertura: " + ptcl.getHoraAbertura()
								+"\nHora Atendimento: " + ptcl.getHoraAtendimento()
								+"\n================================================================");
		}*/
		
		this.tipoRelatorio = 1;
		String nomeRelatorioJasper = "NceProtocoloReq";
		String nomeRelatorioSaida = "NCE-Protocolo_Requisicao-" + this.controleChave.getId();
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		JRBeanCollectionDataSource cBean = new JRBeanCollectionDataSource(listaProtocolo);
		
		try
		{
			this.protocoloEntrega = relatorioUtil.geraRelatorio(nomeRelatorioJasper, nomeRelatorioSaida, this.tipoRelatorio, cBean);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Protocolo da Requisição: " + this.controleChave.getIdAno() + " gerado com sucesso!", ""));
		}
		catch (UtilException e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao gerar o Protocolo da Requisição: " + this.controleChave.getIdAno(), ""));
			
			e.printStackTrace();
		}
		
		return this.protocoloEntrega;
	}
	
	public void calcTempoAberto()
	{
		DateCalculator calcDate = new DateCalculator();
		List<ControleChave> listControle = new ArrayList<ControleChave>();
		listControle = this.controleChaveRN.listarNovas();
		
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
	
	//Método para informar que o prazo de abertura ou devolução expirou========================================================
	public String controlePrazos(ControleChave controleChave)
	{
		DateCalculator calcDate = new DateCalculator();
		this.data15MinAtras = calcDate.data15MinAtras(new Date());
		this.data48hsAtras = calcDate.data48HorasAtras(new Date());
		
		try
		{
			if(controleChave.getDataAbertura().before(this.data15MinAtras) && controleChave.getDataAtendimento() == null)
			{
				this.rowColorVencido = "#CD3333;font-weight: bold;";
			}
			else if(controleChave.getDataAbertura().before(this.data48hsAtras) && controleChave.getDataFechamento() == null)
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
	
	//Método para controlar a exibição do botão fechar da datatable===========================
	public boolean botaoFechar(ControleChave controleChave)
	{
		this.controleChave = controleChave;
		
		if(this.controleChave.getDataAtendimento() != null)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	//Método para controlar a cor do botão fechar da datatable===========================
	public boolean CorBotaoFechar(ControleChave controleChave)
	{
		this.controleChave = controleChave;
		
		if(this.controleChave.getDataFechamento() != null)
		{
			//Botão cor verde: turnOff_true.png
			return true;
		}
		else
		{
			//Botão cor vermelho: turnOff_false.png
			return false;
		}
		
	}
	//MÉTODOS PARA O BEAN TECNICO===============================================================
	public String salvarTecnico()
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
			this.tecnico = new Tecnico();
			this.tecnico.setNome(nomeTecnico);
			
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
			 if(this.tecnicoRN.tecnicoPorCPF(this.cpfTecnico) != null && this.tecnicoRN.tecnicoPorCPF(this.cpfTecnico).isStatus())
			 {
				 this.controleChave.setIdTecnico(this.tecnicoRN.tecnicoPorCPF(this.cpfTecnico));
			 }
			 else
			 {
				 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				    		FacesMessage.SEVERITY_ERROR , "Técnico não encontrado ou bloqueado!", ""));
			 }
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
	public String atribuirSite()
	{	
		//Removendo as chaves que não foram selecionadas da lista de chaves
		for(int y = 0; y < this.listaChaves.size(); y++)
		{
			if(this.listaChaves.get(y).isSelecao() == false)
			{
				this.listaChaves.remove(this.listaChaves.get(y));
			}
			
		}
		
		//Atualizar a lista geral de chaves, para salvar as chaves corretamente ao salvar a requisição
		this.listaGeralChaves.addAll(this.listaChaves);
		
		/*Atribuir site e as chaves se existirem chaves selecionadas, 
		para o objeto ControleSiteChave que é o histórico de movimentações das chaves.*/
		this.siteChave = new ControleSiteChave();
		this.siteChave.setSiteIdCodAtual(this.site.getIdCodAtual());
		
		if(this.listaChaves.size() > 0)
		{		
			//Concatenando as chaves para exibição no form cadastro=============================
			String chavesConcatenadas = "";
			for(int x = 0; x < this.listaChaves.size(); x++)
			{	
				chavesConcatenadas = chavesConcatenadas + this.listaChaves.get(x).getNome();
				
				if(x < this.listaChaves.size() - 1)
				{
					chavesConcatenadas = chavesConcatenadas + ", ";
				}
				
			}
			
			//Atualizando o objeto com as chaves concatenadas====================
			this.siteChave.setListaChaves(chavesConcatenadas);
			
			//List auxiliar para exibir os sites e as chaves no formulário de cadastro das requisições
			this.listaSiteChave.add(this.siteChave);
			this.siteChaveRequisitado = true;
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Deve-se selecionar pelo menos uma chave para atribuir na requisição!", ""));
		}
		
		this.listaChaves.clear();
		this.formSiteChave = false;
		this.site = new Site();
		this.chave = new Chave();
		
		return null;
	}
	
	public void atribuirNovoSite(SelectEvent event)
	{
		this.listaChaves.clear();
	}
	
	//Método utilizado pelo componente autoComplete==========================
	public void siteRequisitado(SelectEvent event)
	{
		this.listaChaves.clear();
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
				
		return null;
	}
	//FIM MÉTODOS SITECHAVE========================================================================================
	
	//MÉTODOS DAS ANOTAÇÕES DAS REQUISIÇÕES========================================================================
	public String novaAnotacao()
	{
		this.anotacao = new Anotacao();
		this.formAnotacao = true;
		
		return null;
	}
	
	public String salvarAnotacao()
	{
		this.anotacao.setIdUsuario(this.usuarioRN.buscarPorLogin(this.login));
		this.anotacao.setDataHoraReg(new Date());
		this.anotacao.setIdReq(this.controleChave);
		
		try
		{
			this.anotacaoRN.salvar(this.anotacao);
			this.formAnotacao = false;
			
			//Adicionando a anotação salva na lista de anotações que será recarregada no dialog form================
			this.listaAnotacao.add(this.anotacao);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Anotação registrada com sucesso!", ""));
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR , "Erro ao registrar anotação!" , ""));
		}
		
		return null;
		
	}
	
	public String carregarListaAnotacao()
	{
		this.listaAnotacao.clear();
		this.listaAnotacao = this.anotacaoRN.listarPorReq(this.controleChave);
		
		return null;
	}
	//FIM MÉTODOS ANOTAÇÕES========================================================================================
	
	private String formatYear(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		
		return dateFormat.format(data);
	}
	
	private String formatDateHour(Date date) 
	{
	    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - H:mm");
	    
	    return dateFormat.format(date);
	}
	
	private String formatTime(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("H:mm");
		
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

	public Date getData15MinAtras() {
		return data15MinAtras;
	}

	public void setData15MinAtras(Date data15MinAtras) {
		this.data15MinAtras = data15MinAtras;
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

	public StatusRequisicaoRN getStatusRN() {
		return statusRN;
	}

	public void setStatusRN(StatusRequisicaoRN statusRN) {
		this.statusRN = statusRN;
	}

	public String getNomeStatus() {
		return nomeStatus;
	}

	public void setNomeStatus(String nomeStatus) {
		this.nomeStatus = nomeStatus;
	}

	public boolean isCampoStatus() {
		return campoStatus;
	}

	public void setCampoStatus(boolean campoStatus) {
		this.campoStatus = campoStatus;
	}

	public StatusRequisicao getStatus() {
		return status;
	}

	public void setStatus(StatusRequisicao status) {
		this.status = status;
	}

	public Anotacao getAnotacao() {
		return anotacao;
	}

	public void setAnotacao(Anotacao anotacao) {
		this.anotacao = anotacao;
	}

	public List<Anotacao> getListaAnotacao() {
		return listaAnotacao;
	}

	public void setListaAnotacao(List<Anotacao> listaAnotacao) {
		this.listaAnotacao = listaAnotacao;
	}

	public boolean isFormAnotacao() {
		return formAnotacao;
	}

	public void setFormAnotacao(boolean formAnotacao) {
		this.formAnotacao = formAnotacao;
	}

	public boolean isFormFechamento() {
		return formFechamento;
	}

	public void setFormFechamento(boolean formFechamento) {
		this.formFechamento = formFechamento;
	}

	public ControleChaveRN getControleChaveRN() {
		return controleChaveRN;
	}

	public void setControleChaveRN(ControleChaveRN controleChaveRN) {
		this.controleChaveRN = controleChaveRN;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

}
