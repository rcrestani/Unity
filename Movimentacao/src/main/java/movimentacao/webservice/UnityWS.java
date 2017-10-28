package movimentacao.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.*;

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

}
