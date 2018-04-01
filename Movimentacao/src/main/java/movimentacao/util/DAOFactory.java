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
import movimentacao.projetoAES.ControleFrotaDAOHibernate;
import movimentacao.projetoNCE.ControleChaveDAOHibernate;
import movimentacao.projetoNCE.anotacao.AnotacaoDAOHibernate;
import movimentacao.projetoNCE.chave.ChaveDAOHibernate;
import movimentacao.projetoNCE.controleSiteChave.ControleSiteChaveDAOHibernate;
import movimentacao.projetoNCE.coordenador.CoordenadorDAOHibernate;
import movimentacao.projetoNCE.emails.NivelEmailDAOHibernate;
import movimentacao.projetoNCE.empresa.EmpresaDAOHibernate;
import movimentacao.projetoNCE.site.SiteDAOHibernate;
import movimentacao.projetoNCE.status.StatusRequisicaoDAOHibernate;
import movimentacao.projetoNCE.tecnico.TecnicoDAOHibernate;
import movimentacao.projetoNCE.tecnico.notesTecnico.NotesTecnicoDAOHibernate;
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
	
	public static ControleFrotaDAOHibernate criarControleFrotaDAO()
	{
		ControleFrotaDAOHibernate controleFrotaDAO = new ControleFrotaDAOHibernate();
		controleFrotaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return controleFrotaDAO;
	}
	
	public static StatusRequisicaoDAOHibernate criarStatusRequisicaoDAO()
	{
		StatusRequisicaoDAOHibernate statusRequisicaoDAO = new StatusRequisicaoDAOHibernate();
		statusRequisicaoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return statusRequisicaoDAO;
	}
	
	public static EmpresaDAOHibernate criarEmpresaDAO()
	{
		EmpresaDAOHibernate empresaDAO = new EmpresaDAOHibernate();
		empresaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return empresaDAO;
	}
	
	public static TecnicoDAOHibernate criarTecnicoDAO()
	{
		TecnicoDAOHibernate tecnicoDAO = new TecnicoDAOHibernate();
		tecnicoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return tecnicoDAO;
	}
	
	public static NotesTecnicoDAOHibernate criarNotesTecnicoDAO()
	{
		NotesTecnicoDAOHibernate notesTecnicoDAO = new NotesTecnicoDAOHibernate();
		notesTecnicoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return notesTecnicoDAO;
	}
	
	public static CoordenadorDAOHibernate criarCoordenadorDAO()
	{
		CoordenadorDAOHibernate coordenadorDAO = new CoordenadorDAOHibernate();
		coordenadorDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return coordenadorDAO;
	}
	
	public static SiteDAOHibernate criarSiteDAO()
	{
		SiteDAOHibernate siteDAO = new SiteDAOHibernate();
		siteDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return siteDAO;
	}
	
	public static ChaveDAOHibernate criarChaveDAO()
	{
		ChaveDAOHibernate chaveDAO = new ChaveDAOHibernate();
		chaveDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return chaveDAO;
	}
	
	public static ControleChaveDAOHibernate criarControleChaveDAO()
	{
		ControleChaveDAOHibernate controleChaveDAO = new ControleChaveDAOHibernate();
		controleChaveDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return controleChaveDAO;
	}
	
	public static ControleSiteChaveDAOHibernate criarControleSiteChaveDAO()
	{
		ControleSiteChaveDAOHibernate control = new ControleSiteChaveDAOHibernate();
		control.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return control;
	}
	
	public static NivelEmailDAOHibernate criarNivelEmailDAO()
	{
		NivelEmailDAOHibernate nivelEmailDAO = new NivelEmailDAOHibernate();
		nivelEmailDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return nivelEmailDAO;
	}
	
	public static AnotacaoDAOHibernate criarAnotacaoDAO()
	{
		AnotacaoDAOHibernate anotacaoDAO = new AnotacaoDAOHibernate();
		anotacaoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		
		return anotacaoDAO;
	}
}
