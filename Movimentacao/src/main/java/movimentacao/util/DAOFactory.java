package movimentacao.util;

import movimentacao.cartaoVisita.CartaoVisitaDAOHibernate;
import movimentacao.cliente.ClienteDAO;
import movimentacao.cliente.ClienteDAOHibernate;
import movimentacao.controleAcesso.*;
import movimentacao.itMov.ItMovDAOHibernate;
import movimentacao.mov.MovDAOHibernate;
import movimentacao.negocios.NegociosDAOHibernate;
import movimentacao.negocios.atividades.AtividadesDAOHibernate;
import movimentacao.negocios.contatos.ContatosDAOHibernate;
import movimentacao.produto.ProdutoDAOHibernate;
import movimentacao.produtoTask.ProdutoTaskDAOHibernate;
import movimentacao.usuario.*;

public class DAOFactory 
{
	public static UsuarioDAO criarUsuarioDAO()
	{
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return usuarioDAO;
	}
	
	public static LogAcessoDAO criarLogAcesso()
	{
		LogAcessoDAOHibernate logAcessoDAO = new LogAcessoDAOHibernate();
		logAcessoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return logAcessoDAO;
	}
	
	public static ClienteDAO criarClienteDAO()
	{
		ClienteDAOHibernate clienteDAO = new ClienteDAOHibernate();
		clienteDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return clienteDAO;
	}
	
	public static ProdutoDAOHibernate criarProdutoDAO()
	{
		ProdutoDAOHibernate produtoDAO = new ProdutoDAOHibernate();
		produtoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return produtoDAO;
	}
	
	public static MovDAOHibernate criarMovDAO()
	{
		MovDAOHibernate movDAO = new MovDAOHibernate();
		movDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return movDAO;
	}
	
	public static ItMovDAOHibernate criarItMovDAO()
	{
		ItMovDAOHibernate itMovDAO = new ItMovDAOHibernate();
		itMovDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return itMovDAO;
	}
	
	public static ProdutoTaskDAOHibernate criarProdutoTaskDAO()
	{
		ProdutoTaskDAOHibernate produtoTaskDAO = new ProdutoTaskDAOHibernate();
		produtoTaskDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return produtoTaskDAO;
	}
	
	public static AtividadesDAOHibernate criarAtividadesDAO()
	{
		AtividadesDAOHibernate atividadesDAO = new AtividadesDAOHibernate();
		atividadesDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return atividadesDAO;
	}
	
	public static NegociosDAOHibernate criarNegociosDAO()
	{
		NegociosDAOHibernate negociosDAO = new NegociosDAOHibernate();
		negociosDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return negociosDAO;
	}
	
	public static ContatosDAOHibernate criarContatosDAO()
	{
		ContatosDAOHibernate contatosDAO = new ContatosDAOHibernate();
		contatosDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return contatosDAO;
	}
	
	public static CartaoVisitaDAOHibernate criarCartaoVisitaDAO()
	{
		CartaoVisitaDAOHibernate cartaoVisitaDAO = new CartaoVisitaDAOHibernate();
		cartaoVisitaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return cartaoVisitaDAO;
	}
}
