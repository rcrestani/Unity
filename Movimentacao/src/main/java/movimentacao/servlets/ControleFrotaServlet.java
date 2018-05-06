package movimentacao.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import movimentacao.projetoAES.ControleFrota;
import movimentacao.projetoAES.ListaControleFrota;
import movimentacao.util.HibernateUtil;
import movimentacao.util.JAXBUtil;
import movimentacao.util.ServletUtil;

@WebServlet("/controleFrotaWS/*")
public class ControleFrotaServlet extends HttpServlet
{
	private static final long serialVersionUID = -4732490376026408424L;
		
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req , HttpServletResponse resp) throws ServletException , IOException
	{
		//Bloco para abrir a sessao do hibernate==================================
		Session sessionTask = null;
		sessionTask = HibernateUtil.getSessionFactory().openSession();
		System.out.println("STATUS SESSION CONTROLE FROTA: " + sessionTask.isOpen());
		Transaction transacaoTask = sessionTask.beginTransaction();
		List<ControleFrota> controleFrota = (List<ControleFrota>)sessionTask.createCriteria(ControleFrota.class).list();
    	transacaoTask.commit();
    	sessionTask.close();
    	//========================================================================
		
		ListaControleFrota lista = new ListaControleFrota();
		lista.setControleFrota(controleFrota);
		
		String xml = JAXBUtil.toXML(lista);
		
		ServletUtil.writeXML(resp, xml);
	}
}
