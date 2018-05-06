package movimentacao.projetoAES;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import movimentacao.util.DateCalculator;

public class ControleFrotaDAOHibernate implements ControleFrotaDAO
{
	
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(ControleFrota controleFrota)
	{
		this.session.saveOrUpdate(controleFrota);
	}
	
	public ControleFrota consultaRegistroSaida(String vtr, Date data)
	{
		Criteria criteria = this.session.createCriteria(ControleFrota.class);
		
		DateCalculator dataCalc = new DateCalculator();
		Date dataAnterior = dataCalc.data14HorasAtras(data);
		
		criteria.add(Restrictions.between("saida", dataAnterior, data));
		criteria.add(Restrictions.eq("vtr", vtr));
		//criteria.add(Restrictions.eq("chegada", null));
		criteria.addOrder(Order.desc("saida"));
		criteria.setMaxResults(1);
		
		return (ControleFrota) criteria.uniqueResult();
	}
	
	public ControleFrota ultimoRegistro()
	{
		Criteria criteria = this.session.createCriteria(ControleFrota.class);
		
		criteria.addOrder(Order.desc("codColeta"));
		criteria.setMaxResults(1);
		
		return (ControleFrota) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<ControleFrota> listar()
	{
		return this.session.createCriteria(ControleFrota.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ControleFrota> consultaLista(ControleFrotaFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(ControleFrota.class);
		
		Calendar c = Calendar.getInstance();
		
		if(filtro.getFim() != null)
		{
			c.setTime(filtro.getFim());
			c.set(Calendar.HOUR , 23);
			c.set(Calendar.MINUTE , 59);
			c.set(Calendar.SECOND, 59);
		}
		
		if(filtro.getInicio() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("saida", filtro.getInicio()));
		}
		else if(filtro.getFim() != null && filtro.getInicio() == null)
		{
			criteria.add(Restrictions.le("saida", c.getTime()));
		}
		else if(filtro.getInicio() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("saida", filtro.getInicio() , c.getTime()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getColetor()))
		{
			criteria.add(Restrictions.eq("coletor", filtro.getColetor()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getMotorista()))
		{
			criteria.add(Restrictions.eq("motorista", filtro.getMotorista()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getVtr()))
		{
			criteria.add(Restrictions.eq("vtr", filtro.getVtr()));
		}
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeColetor(String text)
	{
		String hql = "select coletor from controleFrota where coletor like :text group by coletor";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeVtr(String text)
	{
		String hql = "select vtr from controleFrota where vtr like :text group by vtr";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeCondutor(String text)
	{
		String hql = "select motorista from controleFrota where motorista like :text group by motorista order by motorista";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ControleFrota> buscarTodosPaginado(ControleFrotaFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}
	
	public int qtdeFiltrados(ControleFrotaFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(ControleFrotaFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(ControleFrota.class);
		Calendar c = Calendar.getInstance();
		
		if(filtro.getFim() != null)
		{
			c.setTime(filtro.getFim());
			c.set(Calendar.HOUR , 23);
			c.set(Calendar.MINUTE , 59);
			c.set(Calendar.SECOND, 59);
		}
		
		if(filtro.getInicio() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("saida", filtro.getInicio()));
		}
		else if(filtro.getFim() != null && filtro.getInicio() == null)
		{			
			criteria.add(Restrictions.le("saida", c.getTime()));
		}
		else if(filtro.getInicio() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("saida", filtro.getInicio() , c.getTime()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getColetor()))
		{
			criteria.add(Restrictions.eq("coletor", filtro.getColetor()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getMotorista()))
		{
			criteria.add(Restrictions.eq("motorista", filtro.getMotorista()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getVtr()))
		{
			criteria.add(Restrictions.eq("vtr", filtro.getVtr()));
		}
		
		return criteria;
	}

}
