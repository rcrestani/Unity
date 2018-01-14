package movimentacao.projetoNCE.chave;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
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
	
	public Chave chavePorId(Integer id)
	{
		String hql = "select c from nce_chave c where c.id = :id";
		Query consulta = this.session.createQuery(hql);
		consulta.setInteger("id", id);
		
		return (Chave) consulta.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Chave> listaPorSiteStatusFalse(Site site)
	{
		Criteria criteria = this.session.createCriteria(Chave.class);
		
		criteria.add(Restrictions.eq("idSite", site));
		//criteria.add(Restrictions.eq("status", false));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Chave> listaPorSiteStatusTrue(Site site , ControleChave controleChave)
	{
		Criteria criteria = this.session.createCriteria(Chave.class);
		
		criteria.add(Restrictions.eq("idSite", site));
		criteria.add(Restrictions.eq("idControleChave", controleChave));
		criteria.add(Restrictions.eq("status", true));
		
		return criteria.list();
	}
}
