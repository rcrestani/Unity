package movimentacao.projetoNCE.empresa;

import java.util.List;

import org.hibernate.Session;

public class EmpresaDAOHibernate implements EmpresaDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Empresa empresa)
	{
		this.session.saveOrUpdate(empresa);
	}

	public void excluir(Empresa empresa)
	{
		this.session.delete(empresa);
	}

	public Empresa carregar(Integer id)
	{
		return (Empresa) this.session.get(Empresa.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> listar()
	{
		return this.session.createCriteria(Empresa.class).list();
	}

}
