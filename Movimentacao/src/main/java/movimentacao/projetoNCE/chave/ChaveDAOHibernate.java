package movimentacao.projetoNCE.chave;

import java.util.List;

import org.hibernate.Session;

public class ChaveDAOHibernate implements ChaveDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void salvar(Chave chave)
	{
		this.session.saveOrUpdate(chave);
	}

	public void excluir(Chave chave)
	{
		this.session.delete(chave);
	}

	public Chave carregar(Integer id)
	{
		return (Chave) this.session.get(Chave.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Chave> listar()
	{
		return this.session.createCriteria(Chave.class).list();
	}
}
