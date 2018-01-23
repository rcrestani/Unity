package movimentacao.projetoNCE.coordenador;

import java.util.List;

import org.hibernate.Session;

public class CoordenadorDAOHibernate implements CoordenadorDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}

	public void salvar(Coordenador coordenador)
	{
		this.session.saveOrUpdate(coordenador);
	}

	public void excluir(Coordenador coordenador)
	{
		this.session.delete(coordenador);
	}

	public Coordenador carregar(Integer id)
	{
		return (Coordenador) this.session.get(Coordenador.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Coordenador> listar()
	{
		return this.session.createCriteria(Coordenador.class).list();
	}

}
