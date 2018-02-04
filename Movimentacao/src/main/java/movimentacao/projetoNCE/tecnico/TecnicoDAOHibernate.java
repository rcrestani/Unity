package movimentacao.projetoNCE.tecnico;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class TecnicoDAOHibernate implements TecnicoDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Tecnico tecnico)
	{
		this.session.saveOrUpdate(tecnico);
	}

	public void excluir(Tecnico tecnico)
	{
		this.session.delete(tecnico);
	}

	public Tecnico carregar(Integer id)
	{
		return (Tecnico) this.session.get(Tecnico.class, id);
	}
	
	public Tecnico tecnicoPorCPF(String cpf)
	{
		Criteria criteria = this.session.createCriteria(Tecnico.class);
		
		criteria.add(Restrictions.eq("cpf", cpf));
		
		return (Tecnico) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tecnico> listar()
	{
		return this.session.createCriteria(Tecnico.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeTecnico(String text)
	{
		String hql = "select nome from nce_tecnico where nome like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	public Tecnico tecnicoPorNome(String nome)
	{
		String hql = "select t from nce_tecnico t where t.nome = :nome";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("nome", nome);
		
		return (Tecnico) consulta.uniqueResult();
	}
}
