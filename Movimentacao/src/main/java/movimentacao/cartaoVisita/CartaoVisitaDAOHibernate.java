package movimentacao.cartaoVisita;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class CartaoVisitaDAOHibernate implements CartaoVisitaDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void salvar(CartaoVisita cartaoVisita) 
	{
		this.session.saveOrUpdate(cartaoVisita);
	}

	public void excluir(CartaoVisita cartaoVisita) 
	{
		this.session.delete(cartaoVisita);
	}

	public CartaoVisita carregar(Integer id) 
	{
		
		return (CartaoVisita) this.session.get(CartaoVisita.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<CartaoVisita> listar(CartaoVisitaFiltro filtro) 
	{
		Criteria criteria = this.session.createCriteria(CartaoVisita.class);
				
		return criteria.list();
	}
}
