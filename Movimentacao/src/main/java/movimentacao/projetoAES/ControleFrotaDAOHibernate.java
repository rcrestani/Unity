package movimentacao.projetoAES;

import org.hibernate.Session;

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

}
