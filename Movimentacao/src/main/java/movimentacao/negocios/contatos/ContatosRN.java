package movimentacao.negocios.contatos;

import java.util.List;

import movimentacao.negocios.Negocios;
import movimentacao.util.DAOFactory;

public class ContatosRN 
{
	private ContatosDAO contatosDAO;
	
	public ContatosRN()
	{
		this.contatosDAO = DAOFactory.criarContatosDAO();
	}
	
	public void salvar(Contatos contatos)
	{
		this.contatosDAO = DAOFactory.criarContatosDAO();
		this.contatosDAO.salvar(contatos);
	}
	
	public void excluir(Contatos contatos)
	{
		this.contatosDAO = DAOFactory.criarContatosDAO();
		this.contatosDAO.excluir(contatos);
	}
	
	public List<Contatos> listar()
	{
		this.contatosDAO = DAOFactory.criarContatosDAO();
		
		return this.contatosDAO.listar();
	}
	
	public List<Contatos> contatosPorNegocios(Negocios negocios)
	{
		this.contatosDAO = DAOFactory.criarContatosDAO();
		
		return this.contatosDAO.contatosPorNegocios(negocios);
	}
}
