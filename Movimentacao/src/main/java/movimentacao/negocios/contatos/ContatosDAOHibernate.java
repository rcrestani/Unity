package movimentacao.negocios.contatos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import movimentacao.negocios.Negocios;

public class ContatosDAOHibernate implements ContatosDAO 
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Contatos contatos) 
	{
		this.session.saveOrUpdate(contatos);
	}

	public void excluir(Contatos contatos) 
	{
		this.session.delete(contatos);
	}

	@SuppressWarnings("unchecked")
	public List<Contatos> listar() 
	{
		return this.session.createCriteria(Contatos.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<Contatos> contatosPorNegocios(Negocios negocios) 
	{
		Criteria criteria = this.session.createCriteria(Contatos.class);
		
		criteria.add(Restrictions.eq("negocios", negocios));
		
		return (List<Contatos>)criteria.list();
	}

}
