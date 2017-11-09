package movimentacao.telegramBot;

import java.io.File;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import movimentacao.cartaoVisita.CartaoVisita;
import movimentacao.cartaoVisita.CartaoVisitaFiltro;
import movimentacao.cartaoVisita.CartaoVisitaRN;

public class Robot 
{
	public void telegramBot()
	{
    	CartaoVisita cartaoVisita = new CartaoVisita();
		CartaoVisitaRN cartaoVisitaRN = new CartaoVisitaRN();
		CartaoVisitaFiltro filtro = new CartaoVisitaFiltro();
		
		//Criação do objeto bot com as informações de acesso
		TelegramBot bot = TelegramBotAdapter.build("344152536:AAGkvoBIMyNaMyfu7ijntUmVMAFv2OYVn4A");
		//bot.execute(new SendMessage(125735555 , "\n\n*TESTE UNITY* \n\nP/ ENVIAR MENSAGEM\n\n=="));
		
		System.out.println("TelegramBot Ativado!");
		
		//objeto responsável por receber as mensagens
		GetUpdatesResponse updatesResponse;
		//objeto responsável por gerenciar o envio de respostas
		SendResponse sendResponse;
		//objeto responsável por gerenciar o envio de ações do chat
		BaseResponse baseResponse;
				
		//controle de off-set, isto é, a partir deste ID será lido as mensagens pendentes na fila
		int m=0;
    	
    	
		//loop infinito pode ser alterado por algum timer de intervalo curto
		while (true)
		{
	    	//executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
			
			//lista de mensagens
			List<Update> updates = updatesResponse.updates();
			
			//análise de cada ação da mensagem
			for (Update update : updates) {
				
				//atualização do off-set
				m = update.updateId()+1;
				
				String partes[] = update.message().text().split(" ");
				String itemMenu = partes[0];
				
				if(update.message().text().equals("menu"))
				{
					System.out.println("ID: " + update.message().chat().id());
					//envio de "Escrevendo" antes de enviar a resposta
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					//verificação de ação de chat foi enviada com sucesso
					//System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
					
					//envio da mensagem de resposta
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Bem-vindo ao Unity-Bot!"));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Menu, escolha a opção apenas digitando o número:"));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"1 - Pesquisar Cartões de Visita"));
					//verificação de mensagem enviada com sucesso
					System.out.println("Mensagem Enviada?" +sendResponse.isOk());
					sendResponse = null;
				}
				else if(update.message().text().equals("1"))
				{
					//envio de "Escrevendo" antes de enviar a resposta
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					//verificação de ação de chat foi enviada com sucesso
					System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
					
					//envio da mensagem de resposta
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Cartões de Visita:"));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Digite CV [espaço] e digite o nome ou a Empresa, pode ser qualquer parte destes"));
					//sendResponse = bot.execute(new SendDocument(update.message().chat().id(), new File("/home/rcrestani/Pictures/Batman.jpg")));
					//verificação de mensagem enviada com sucesso
					System.out.println("Mensagem Enviada?" +sendResponse.isOk());
					sendResponse = null;
				}
				else if(itemMenu.equals("CV"))
				{
					filtro.setNome(partes[1]);
					List<CartaoVisita> lista = cartaoVisitaRN.listar(filtro);
					if(lista.size() == 0)
					{
						filtro.setEmpresa(partes[1]);
						lista = cartaoVisitaRN.listar(filtro);
					}
										
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Cartões Encontrado:"));
					for(int x = 0; x < lista.size(); x++)
					{
						//envio de "Escrevendo" antes de enviar a resposta
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.find_location));
						//verificação de ação de chat foi enviada com sucesso
						System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
						
						//envio da mensagem de resposta
						
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), 
								"ID: " + lista.get(x).getId() + " - " + lista.get(x).getNome() + " - " + lista.get(x).getEmpresa()));
						
					}
					
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
							"Escolha digitando CVID [espaço] Número correspondente:"));
					//verificação de mensagem enviada com sucesso
					System.out.println("Mensagem Enviada?" +sendResponse.isOk());
					
					sendResponse = null;
				}
				else if(itemMenu.equals("CVID"))
				{
					boolean control = false;
					Integer id = null;
					try
					{
						id = Integer.parseInt(partes[1]);
						control = true;
					}
					catch(Exception e)
					{
						control = false;
						e.getMessage();
					}
					
					if(control == true)
					{
						cartaoVisita = cartaoVisitaRN.carregar(id);
						sendResponse = bot.execute(new SendDocument(update.message().chat().id(),
								new File("/opt/unityImages/cartoesVisita/" + cartaoVisita.getArquivoImagemFrente())));
						sendResponse = bot.execute(new SendDocument(update.message().chat().id(),
								new File("/opt/unityImages/cartoesVisita/" + cartaoVisita.getArquivoImagemVerso())));
						//verificação de mensagem enviada com sucesso
						System.out.println("Mensagem Enviada?" +sendResponse.isOk());
					}
					
				}
				
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
            
	}  
		
		

}
