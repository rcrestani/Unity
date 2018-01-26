package movimentacao.controleAcesso;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
	
	@SuppressWarnings("unchecked")
	public List<String> completeLogin(String text)
	{
		String hql = "select usuario from logAcesso where usuario like :text group by usuario";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<LogAcesso> buscarTodosPaginado(LogAcessoFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null)
		{
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		}
		else if (filtro.getPropriedadeOrdenacao() != null)
		{
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		else if (filtro.getInicio() == null && filtro.getFim() == null && filtro.getPropriedadeOrdenacao() == null)
		{
			criteria.addOrder(Order.desc("dataLogin"));
		}
		
		return criteria.list();
	}
	
	public int qtdeFiltrados(LogAcessoFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(LogAcessoFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(LogAcesso.class);
		
		if(filtro.getInicio() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("dataLogin", filtro.getInicio()));
		}
		else if(filtro.getFim() != null && filtro.getInicio() == null)
		{
			criteria.add(Restrictions.le("dataLogin", filtro.getFim()));
		}
		else if(filtro.getInicio() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("dataLogin", filtro.getInicio() , filtro.getFim()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getLogin()))
		{
			criteria.add(Restrictions.eq("usuario", filtro.getLogin()));
		}
		
		return criteria;
	}

}
