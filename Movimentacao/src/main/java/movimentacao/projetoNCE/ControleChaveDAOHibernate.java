package movimentacao.projetoNCE;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
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
	public List<ControleChave> listar()
	{
		return this.session.createCriteria(ControleChave.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<ControleChave> listarAberto()
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
		else if(filtro.getDataAbertura() == null && filtro.getDataFechamento() == null)
		{
			criteria.addOrder(Order.desc("dataAbertura"));
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
		
		return criteria;
	}
}
