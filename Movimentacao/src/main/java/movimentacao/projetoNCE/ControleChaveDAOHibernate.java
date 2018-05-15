package movimentacao.projetoNCE;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class ControleChaveDAOHibernate implements ControleChaveDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void salvar(ControleChave controleChave)
	{
		this.session.clear();
		this.session.saveOrUpdate(controleChave);
	}

	public void excluir(ControleChave controleChave)
	{
		this.session.delete(controleChave);
	}

	public ControleChave carregar(Integer id)
	{
		return (ControleChave) this.session.get(ControleChave.class, id);
	}
	
	public ControleChave ultimoRegistro()
	{
		Criteria criteria = this.session.createCriteria(ControleChave.class);
		
		criteria.addOrder(Order.desc("id"));
		criteria.setMaxResults(1);
		
		return (ControleChave) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeIdAno(String text)
	{
		String hql = "select idAno from nce_controleChave where idAno like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ControleChave> listar()
	{
		return this.session.createCriteria(ControleChave.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<ControleChave> listarAberto()
	{
		Criteria criteria = this.session.createCriteria(ControleChave.class);
		
		criteria.add(Restrictions.isNotNull("dataAtendimento"));
		criteria.add(Restrictions.isNull("dataFechamento"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ControleChave> listarNovas()
	{
		Criteria criteria = this.session.createCriteria(ControleChave.class);
		
		criteria.add(Restrictions.isNull("dataFechamento"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ControleChave> buscarTodosPaginado(ControleChaveFiltro filtro)
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
	
	public int qtdeFiltrados(ControleChaveFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(ControleChaveFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(ControleChave.class);
		
		if(filtro.getDataAbertura() != null && filtro.getDataFechamento() == null)
		{
			criteria.add(Restrictions.ge("dataAbertura", filtro.getDataAbertura()));
		}
		else if(filtro.getDataFechamento() != null && filtro.getDataAbertura() == null)
		{
			criteria.add(Restrictions.le("dataAbertura", filtro.getDataFechamento()));
		}
		else if(filtro.getDataAbertura() != null && filtro.getDataFechamento() != null)
		{
			criteria.add(Restrictions.between("dataAbertura", filtro.getDataAbertura() , filtro.getDataFechamento()));
		}
		else if(filtro.getDataAbertura() == null && filtro.getDataFechamento() == null
												 && filtro.getIdAno() == null
												 && filtro.getIdTecnico() == null
												 && filtro.getStatusFechamento() == null
												 && filtro.getReqFechada() == null)
		{
			criteria.addOrder(Order.asc("dataAbertura"));
			criteria.add(Restrictions.isNull("dataAtendimento"));
			criteria.add(Restrictions.isNull("dataFechamento"));
		}
		
		if(StringUtils.isNotEmpty( filtro.getCrq()))
		{
			criteria.add(Restrictions.eq("crq", filtro.getCrq()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getProjeto()))
		{
			criteria.add(Restrictions.eq("projeto", filtro.getProjeto()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getIdAno()))
		{
			criteria.add(Restrictions.eq("idAno", filtro.getIdAno()));
		}
		
		if(filtro.getIdTecnico() != null)
		{
			criteria.add(Restrictions.eq("idTecnico", filtro.getIdTecnico()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getStatusFechamento() ) && filtro.getStatusFechamento().equals("true"))
		{
			criteria.add(Restrictions.eq("statusFechamento", true));
		}
		else if(StringUtils.isNotEmpty( filtro.getStatusFechamento() ) && filtro.getStatusFechamento().equals("false"))
		{
			criteria.add(Restrictions.eq("statusFechamento", false));
		}
		
		if(StringUtils.isNotEmpty( filtro.getReqFechada() ) && filtro.getReqFechada().equals("true"))
		{
			criteria.add(Restrictions.eq("reqFechada", true));
		}
		else if(StringUtils.isNotEmpty( filtro.getReqFechada() ) && filtro.getReqFechada().equals("false"))
		{
			criteria.add(Restrictions.eq("reqFechada", false));
		}
		
		return criteria;
	}
}
