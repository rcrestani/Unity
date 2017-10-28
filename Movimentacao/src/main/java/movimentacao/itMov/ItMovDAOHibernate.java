package movimentacao.itMov;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import movimentacao.mov.Mov;

public class ItMovDAOHibernate implements ItMovDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(ItMov itMov) 
	{
		this.session.saveOrUpdate(itMov);
	}

	public void excluir(ItMov itMov) 
	{
		this.session.delete(itMov);
	}

	
	public ItMov carregar(Integer id) 
	{
		return (ItMov) this.session.get(ItMov.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ItMov> listar() 
	{
		return this.session.createCriteria(ItMov.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ItMov> itensMov(Mov mov)
	{
		Criteria criteria = this.session.createCriteria(ItMov.class);
		
		criteria.add(Restrictions.eq("id_mov", mov));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ItMov> tipoMov(String tipoMovi)
	{
		Criteria criteria = this.session.createCriteria(ItMov.class);
		
		criteria.add(Restrictions.eq("tipoMovi", tipoMovi));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ItMov> itMovFechamento(Mov mov , String tipoMovi) 
	{
		Criteria criteria = this.session.createCriteria(ItMov.class);
		
		criteria.add(Restrictions.eq("id_mov", mov));
		criteria.add(Restrictions.eq("tipoMovi", tipoMovi));
		
		return criteria.list();
	}

}
