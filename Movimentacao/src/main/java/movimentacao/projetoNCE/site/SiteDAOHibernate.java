package movimentacao.projetoNCE.site;

import java.util.List;

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

}
