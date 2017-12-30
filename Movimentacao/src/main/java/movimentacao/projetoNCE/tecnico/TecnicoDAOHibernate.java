package movimentacao.projetoNCE.tecnico;

import java.util.List;

import org.hibernate.Session;

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

	@SuppressWarnings("unchecked")
	public List<Tecnico> listar()
	{
		return this.session.createCriteria(Tecnico.class).list();
	}

}
