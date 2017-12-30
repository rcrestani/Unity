package movimentacao.projetoNCE;

import java.util.List;

import org.hibernate.Session;

public class ControleChaveDAOHibernate implements ControleChaveDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void salvar(ControleChave controleChave)
	{
		this.session.saveOrUpdate(controleChave);
	}

	public void excluir(ControleChave controleChave)
	{
		this.session.delete(controleChave);
	}

	public ControleChave carregar(Integer id)
	{
		return (ControleChave) this.session.get(ControleChave.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ControleChave> listar()
	{
		return this.session.createCriteria(ControleChave.class).list();
	}

}
