package movimentacao.projetoNCE.controleSiteChave;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import movimentacao.projetoNCE.ControleChave;

public class ControleSiteChaveDAOHibernate implements ControleSiteChaveDAO
{	
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(ControleSiteChave controleSiteChave)
	{
		this.session.saveOrUpdate(controleSiteChave);
	}
	
	@SuppressWarnings("unchecked")
	public List<ControleSiteChave> sitesChavesPorReq(ControleChave controleChave)
	{
		Criteria criteria = this.session.createCriteria(ControleSiteChave.class);
		
		criteria.add(Restrictions.eq("idReq", controleChave));
		
		return criteria.list();
	}

}
