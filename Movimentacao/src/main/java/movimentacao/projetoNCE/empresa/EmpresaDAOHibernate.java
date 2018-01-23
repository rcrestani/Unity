package movimentacao.projetoNCE.empresa;

import java.util.List;

import org.hibernate.Query;
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
	
	@SuppressWarnings("unchecked")
	public List<String> completeEmpresa(String text)
	{
		String hql = "select razaoSocial from nce_empresa where razaoSocial like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	public Empresa empresaPorNome(String nome)
	{
		String hql = "select e from nce_empresa e where e.razaoSocial = :nome";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("nome", nome);
		
		return (Empresa) consulta.uniqueResult();
	}
}
