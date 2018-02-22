package movimentacao.projetoNCE.controleSiteChave;

import org.hibernate.Session;

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

}
