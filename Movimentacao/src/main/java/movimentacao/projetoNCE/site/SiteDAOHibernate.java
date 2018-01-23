package movimentacao.projetoNCE.site;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class SiteDAOHibernate implements SiteDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void salvar(Site site)
	{
		this.session.saveOrUpdate(site);
	}

	public void excluir(Site site)
	{
		this.session.delete(site);
	}

	public Site carregar(Integer id)
	{
		return (Site) this.session.get(Site.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Site> listar()
	{
		return this.session.createCriteria(Site.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeSite(String text)
	{
		String hql = "select idCodAtual from nce_site where idCodAtual like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	public Site sitePorIdCodAtual(String idCodAtual)
	{
		String hql = "select s from nce_site s where s.idCodAtual = :id";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("id", idCodAtual);
		
		return (Site) consulta.uniqueResult();
	}
}
