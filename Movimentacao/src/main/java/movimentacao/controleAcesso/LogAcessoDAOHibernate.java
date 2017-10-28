package movimentacao.controleAcesso;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class LogAcessoDAOHibernate implements LogAcessoDAO
{
private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(LogAcesso logAcesso)
	{
			
		this.session.saveOrUpdate(logAcesso);
	}
	
	public void excluir(LogAcesso logAcesso)
	{
		this.session.delete(logAcesso);
	}
	
	public LogAcesso carregar(Integer codigo)
	{
		return (LogAcesso) this.session.get(LogAcesso.class, codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<LogAcesso> listar()
	{
		return this.session.createCriteria(LogAcesso.class).list();
	}
	
	public LogAcesso buscaPorSessionId(String sessionId)
	{
		Criteria criteria = session.createCriteria(LogAcesso.class);
		criteria.add(Restrictions.eq("sessionId",sessionId));
		/*criteria.addOrder(Order.desc("codigo"));
		criteria.setMaxResults(1);*/
		
		return (LogAcesso) criteria.uniqueResult();
	}

}
