package movimentacao.projetoNCE.chave;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.site.Site;

public class ChaveDAOHibernate implements ChaveDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void salvar(Chave chave)
	{
		this.session.saveOrUpdate(chave);
	}
	
	public void excluir(Chave chave)
	{
		this.session.delete(chave);
	}

	public Chave carregar(Integer id)
	{
		return (Chave) this.session.get(Chave.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Chave> listar()
	{
		return this.session.createCriteria(Chave.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeChave(String text)
	{
		String hql = "select nome from nce_chave where nome like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	public Chave chavePorNome(String nome)
	{
		Criteria criteria = this.session.createCriteria(Chave.class);
		
		criteria.add(Restrictions.eq("nome", nome));
		
		return (Chave) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Chave> listaPorSiteStatusTrue(Site site)
	{
		//A query abaixo com JOIN, serve para buscar valores de um element collection da classe/entidade==============
		String hql = "select c from nce_chave c join c.idSite s where s = :idSite";
		Query listaIdChaves = this.session.createQuery(hql);
		listaIdChaves.setInteger("idSite", site.getId());
		
		return listaIdChaves.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Chave> listaPorSelecaoTrue(ControleChave controleChave)
	{
		Criteria criteria = this.session.createCriteria(Chave.class);
		
		criteria.add(Restrictions.eq("idControleChave", controleChave));
		criteria.add(Restrictions.eq("selecao", true));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Chave> buscarTodosPaginado(ChaveFiltro filtro)
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
		else
		{
			criteria.addOrder(Order.asc("nome"));
		}
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(ChaveFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	

	private Criteria criarCriteriaParaFiltro(ChaveFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(Chave.class);
		
		if(filtro.getInicio() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("dataHoraReg", filtro.getInicio()));
		}
		else if(filtro.getFim() != null && filtro.getInicio() == null)
		{
			criteria.add(Restrictions.le("dataHoraReg", filtro.getFim()));
		}
		else if(filtro.getInicio() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("dataHoraReg", filtro.getInicio() , filtro.getFim()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getNome()))
		{
			criteria.add(Restrictions.eq("nome", filtro.getNome()));
		}
		
		return criteria;
	}
}
