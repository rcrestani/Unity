package movimentacao.web;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.rpc.ServiceException;

import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteServiceLocator;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SQLException;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteFiltro;
import movimentacao.cliente.ClienteRN;
import movimentacao.cliente.LazyClienteDataModel;

@ManagedBean(name = "clienteBean")
@ViewScoped
public class ClienteBean implements Serializable
{

	private static final long serialVersionUID = 8639933295383655773L;
	
	private Cliente cliente = new Cliente();
	private ClienteRN clienteRN = new ClienteRN();
	private LazyClienteDataModel lazyCliente;
	private ClienteFiltro filtro = new ClienteFiltro();
	private String cep;
	
	public LazyClienteDataModel getLazyCliente() {
		return lazyCliente;
	}

	
	@PostConstruct
	public void init()
	{
		lazyCliente = new LazyClienteDataModel(clienteRN, filtro);
	}

	
	
	public String salvar()
	{
		try
		{
			if(this.cliente.getCodigo() == null)
			{
				this.cliente.setStatus(true);
			}
			
			clienteRN.salvar(cliente);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , cliente.getNome() + " salvo com sucesso!", ""));
			return "/restrito/cliente.jsf";
		}
		catch (Exception e)
		{
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro!", "Cliente não foi gravado no Banco de Dados. Motivo: " + e.getMessage()));
		    return "/restrito/cliente.jsf";
		}
	}
	
	public String novo()
	{
		this.cliente = new Cliente();
		
		return null;
	}
	
	
	public String status()
	{
		if (this.cliente.isStatus())
		{
			this.cliente.setStatus(false);
		}
		else
		{
			this.cliente.setStatus(true);
		}
		
		ClienteRN clienteRN = new ClienteRN();
		clienteRN.salvar(this.cliente);
		
		return null;
	}
	
	public String editar()
	{
		this.cep = this.cliente.getCep();
		return null;
	}
	
	public String buscarEndereco() throws MalformedURLException, ServiceException, SQLException, SigepClienteException, RemoteException
	{
		AtendeClienteServiceLocator serviceCorreios = new AtendeClienteServiceLocator();
		AtendeCliente atendeCliente = serviceCorreios.getAtendeClientePort(
				new URL("https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl"));
		EnderecoERP correios = new EnderecoERP();
		correios = atendeCliente.consultaCEP(this.cep);
		
		this.cliente.setCep(correios.getCep());
		this.cliente.setEndereco(correios.getEnd());
		this.cliente.setBairro(correios.getBairro());
		this.cliente.setCidade(correios.getCidade());
		this.cliente.setUf(correios.getUf());
		
		return null;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteRN getClienteRN() {
		return clienteRN;
	}

	public void setClienteRN(ClienteRN clienteRN) {
		this.clienteRN = clienteRN;
	}


	public void setFiltro(ClienteFiltro filtro) {
		this.filtro = filtro;
	}


	public ClienteFiltro getFiltro() {
		return filtro;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}

}
