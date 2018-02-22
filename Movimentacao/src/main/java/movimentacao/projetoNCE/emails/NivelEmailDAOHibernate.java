package movimentacao.projetoNCE.emails;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class NivelEmailDAOHibernate implements NivelEmailDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(NivelEmail nivelEmail)
	{
		this.session.saveOrUpdate(nivelEmail);
	}
	
	public void excluir(NivelEmail nivelEmail)
	{
		this.session.delete(nivelEmail);
	}
	
	@SuppressWarnings("unchecked")
	public List<NivelEmail> listar()
	{
		Criteria criteria = this.session.createCriteria(NivelEmail.class);
		
		criteria.addOrder(Order.asc("nivel"));
		criteria.addOrder(Order.asc("email"));
		
		return criteria.list();
	}

}
