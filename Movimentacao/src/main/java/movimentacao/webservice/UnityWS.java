package movimentacao.webservice;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.*;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;

import movimentacao.cartaoVisita.CartaoVisita;
import movimentacao.cartaoVisita.CartaoVisitaFiltro;
import movimentacao.cartaoVisita.CartaoVisitaRN;
import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;
import movimentacao.negocios.Negocios;
import movimentacao.negocios.NegociosRN;
import movimentacao.negocios.atividades.Atividades;
import movimentacao.negocios.atividades.AtividadesRN;
import movimentacao.produto.Produto;
import movimentacao.produto.ProdutoRN;
import movimentacao.produtoTask.ProdutoTask;
import movimentacao.produtoTask.ProdutoTaskRN;
import movimentacao.projetoAES.ControleFrota;
import movimentacao.projetoAES.ControleFrotaRN;
import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.ControleChaveRN;
import movimentacao.projetoNCE.emails.NivelEmail;
import movimentacao.projetoNCE.emails.NivelEmailRN;

@WebService
public class UnityWS 
{
	@WebMethod
	public List<NomesClientes> listaClientes()
	{
		ClienteRN clienteRN = new ClienteRN();
		NomesClientes nomesClientes = null;
		List<Cliente> clientes = clienteRN.listar();
		List<NomesClientes> listaNomesClientes = new ArrayList<NomesClientes>();
		for(Cliente cliente:clientes)
		{
			nomesClientes = new NomesClientes();
			nomesClientes.setNomesClientes(cliente.getNome());
			listaNomesClientes.add(nomesClientes);
		}
		
		return listaNomesClientes;
	}
	
	@WebMethod
	public String novoCliente(String nome)
	{
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		cliente.setStatus(true);
		
		ClienteRN clienteRN = new ClienteRN();
		clienteRN.salvar(cliente);
		
		String msg = "Cliente " + cliente.getNome() + " salvo com sucesso!";
		
		return msg;
	}
	
	@WebMethod
	public List<Produto> listaProduto()
	{
		ProdutoRN produtoRN = new ProdutoRN();
		List<Produto> lista = produtoRN.listar();
		
		return lista;
	}
	
	@WebMethod
	public List<ProdutoTask> listaProdutoTask()
	{
		ProdutoTaskRN produtoTaskRN = new ProdutoTaskRN();
		
		List<ProdutoTask> lista = produtoTaskRN.listar();
		
		return lista;
	}
	
	@WebMethod
	public List<Negocios> negociosPreVenda()
	{
		NegociosRN negociosRN = new NegociosRN();
		
		List<Negocios> lista = negociosRN.negociosPreVenda();
		
		return lista;
	}
	
	@WebMethod
	public Atividades ultimaAtividade(Negocios negocios)
	{
		AtividadesRN atividadesRN = new AtividadesRN();
		
		 return atividadesRN.ultimaAtividade(negocios);
	}
	
	@WebMethod
	public List<AtividadesDoDia> ativDoDia()
	{
		AtividadesRN atividadesRN = new AtividadesRN();
		
		List<Atividades> lista = atividadesRN.atividadesDoDia();
		
		List<AtividadesDoDia> listaWS = new ArrayList<AtividadesDoDia>();
		AtividadesDoDia atividadesDoDia;
		
		for(int x = 0; x < lista.size(); x++)
		{
			atividadesDoDia = new AtividadesDoDia();
			atividadesDoDia.setCliente(lista.get(x).getNegocios().getCliente().getNome());
			atividadesDoDia.setAcao(lista.get(x).getTipoAtividade());
			atividadesDoDia.setPrazo(lista.get(x).getPrazo());
			atividadesDoDia.setValor(lista.get(x).getNegocios().getValor());
			atividadesDoDia.setObs(lista.get(x).getObs());
			
			//Setando os dados do usuario no objeto usuarioWS que possui apenas as variáveis necessárias para o webservice
			UsuarioWS usuarioWS = new UsuarioWS();
			usuarioWS.setCodigo(lista.get(x).getUsuario().getCodigo());
			usuarioWS.setNome(lista.get(x).getUsuario().getNome());
			usuarioWS.setEmail(lista.get(x).getUsuario().getEmail());
			usuarioWS.setTelegramId(lista.get(x).getUsuario().getTelegramId());
			
			atividadesDoDia.setUsuario(usuarioWS);
			
			listaWS.add(atividadesDoDia);
		}
		
		return listaWS;
	}
	
	@WebMethod
	public boolean cartaoPorId(Integer id , Integer telegramId)
	{
		TelegramBot bot = TelegramBotAdapter.build("344152536:AAGkvoBIMyNaMyfu7ijntUmVMAFv2OYVn4A");
		SendResponse sendResponse;
		CartaoVisitaRN cartaoRN = new CartaoVisitaRN();
		CartaoVisita cartaoVisita = cartaoRN.carregar(id);
		
		sendResponse = bot.execute(new SendDocument(telegramId,
				new File("/opt/unityImages/cartoesVisita/" + cartaoVisita.getArquivoImagemFrente())));
		sendResponse = bot.execute(new SendDocument(telegramId,
				new File("/opt/unityImages/cartoesVisita/" + cartaoVisita.getArquivoImagemVerso())));
		
		//verificação de mensagem enviada com sucesso
		 return sendResponse.isOk();
		
	}
	
	@WebMethod
	public boolean listaCartaoVisita(String texto , Integer telegramId)
	{
		TelegramBot bot = TelegramBotAdapter.build("344152536:AAGkvoBIMyNaMyfu7ijntUmVMAFv2OYVn4A");
		SendResponse sendResponse;
		BaseResponse baseResponse;
		
		CartaoVisitaFiltro filtro = new CartaoVisitaFiltro();
		CartaoVisitaRN cartaoVisitaRN = new CartaoVisitaRN();
		
		String partes[] = texto.split(" ");
		
		filtro.setNome(partes[1]);
		
		List<CartaoVisita> lista = cartaoVisitaRN.listar(filtro);
		if(lista.size() == 0)
		{
			filtro.setEmpresa(partes[1]);
			lista = cartaoVisitaRN.listar(filtro);
		}
		
		sendResponse = bot.execute(new SendMessage(telegramId,"Cartões Encontrado:"));
		for(int x = 0; x < lista.size(); x++)
		{
			//envio de "Escrevendo" antes de enviar a resposta
			baseResponse = bot.execute(new SendChatAction(telegramId, ChatAction.find_location));
			//verificação de ação de chat foi enviada com sucesso
			System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
			
			//envio da mensagem de resposta
			
			sendResponse = bot.execute(new SendMessage(telegramId, 
					"ID: " + lista.get(x).getId() + " - " + lista.get(x).getNome() + " - " + lista.get(x).getEmpresa()));
		}
		
		sendResponse = bot.execute(new SendMessage(telegramId,
				"Escolha digitando CVID [espaço] Número correspondente:"));
		
		//verificação de mensagem enviada com sucesso
		return sendResponse.isOk();
	}
	
	//Métodos da classe controle frota================================================
	@WebMethod
	public void salvarControleFrota(ControleFrota controleFrota)
	{
		ControleFrotaRN controleFrotaRN = new ControleFrotaRN();
		
		controleFrotaRN.salvar(controleFrota);
	}
	
	@WebMethod
	public ControleFrota consultaRegistroSaida(String vtr , Date data)
	{
		ControleFrotaRN controleFrotaRN = new ControleFrotaRN();
		
		return controleFrotaRN.consultaRegistroSaida(vtr, data);
	}
	
	@WebMethod
	public ControleFrota ultimoRegistro()
	{
		ControleFrotaRN controleFrotaRN = new ControleFrotaRN();
		
		return controleFrotaRN.ultimoRegistro();
	}
	//=================================================================================
	
	//Métodos da classe controleChave==================================================
	@WebMethod
	public List<ControleChaveWS> nceListReqOpen()
	{
		ControleChaveRN controleChaveRN = new ControleChaveRN();
		
		List<ControleChave> lista =  controleChaveRN.listarAberto();
		List<ControleChaveWS> listaWS = new ArrayList<ControleChaveWS>();
		
		for(ControleChave controleChave:lista)
		{
			ControleChaveWS controleChaveWS = new ControleChaveWS();
			controleChaveWS.setId(controleChave.getId());
			controleChaveWS.setIdAno(controleChave.getIdAno());
			controleChaveWS.setNomeTecnico(controleChave.getIdTecnico().getNome());
			controleChaveWS.setCpfTecnico(controleChave.getIdTecnico().getCpf());
			controleChaveWS.setCelularTecnico(controleChave.getIdTecnico().getCelular());
			controleChaveWS.setDataAbertura(controleChave.getDataAbertura());
			controleChaveWS.setDataAtendimento(controleChave.getDataAtendimento());
			controleChaveWS.setCrq(controleChave.getCrq());
			controleChaveWS.setAtividade(controleChave.getAtividade());
			controleChaveWS.setStatus(controleChave.getStatus().getNomeStatus());
			controleChaveWS.setObs(controleChave.getObs());
			
			try
			{
				//Setando os dados do usuario no objeto usuarioWS que possui apenas as variáveis necessárias para o webservice
				UsuarioWS usuarioWSAbertura = new UsuarioWS();
				usuarioWSAbertura.setCodigo(controleChave.getUsuarioAbertura().getCodigo());
				usuarioWSAbertura.setNome(controleChave.getUsuarioAbertura().getNome());
				controleChaveWS.setUsuarioAbertura(usuarioWSAbertura);
				
				//Setando os dados do usuario no objeto usuarioWS que possui apenas as variáveis necessárias para o webservice
				UsuarioWS usuarioWSAtendimento = new UsuarioWS();
				usuarioWSAtendimento.setCodigo(controleChave.getUsuarioAtendimento().getCodigo());
				usuarioWSAtendimento.setNome(controleChave.getUsuarioAtendimento().getNome());
				controleChaveWS.setUsuarioAtendimento(usuarioWSAtendimento);
				
				//Setando os dados do usuario no objeto usuarioWS que possui apenas as variáveis necessárias para o webservice
				UsuarioWS usuarioWSFechamento = new UsuarioWS();
				usuarioWSFechamento.setCodigo(controleChave.getUsuarioFechamento().getCodigo());
				usuarioWSFechamento.setNome(controleChave.getUsuarioFechamento().getNome());
				controleChaveWS.setUsuarioFechamento(usuarioWSFechamento);
			}
			catch (Exception e)
			{
				System.out.println("=================ERRO LISTA DE REQUISIÇÕES========================");
				e.printStackTrace();
			}
			
			
			listaWS.add(controleChaveWS);
			
		}
		
		return listaWS;

	}
	
	@WebMethod
	public List<NivelEmailWS> nceListEmailLevel()
	{
		NivelEmailRN nivelEmailRN = new NivelEmailRN();
		
		List<NivelEmail> lista = nivelEmailRN.listar();
		List<NivelEmailWS> listaWS = new ArrayList<NivelEmailWS>();
		
		for(NivelEmail nivelEmail:lista)
		{
			NivelEmailWS nivelEmailWS = new NivelEmailWS();
			
			nivelEmailWS.setNivel(nivelEmail.getNivel());
			nivelEmailWS.setEmail(nivelEmail.getEmail());
			
			listaWS.add(nivelEmailWS);
		}
		
		return listaWS;
	}

	//=================================================================================

}