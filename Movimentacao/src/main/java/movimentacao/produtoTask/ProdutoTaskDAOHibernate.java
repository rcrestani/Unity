package movimentacao.produtoTask;

import java.util.List;

import org.hibernate.Session;

public class ProdutoTaskDAOHibernate implements ProdutoTaskDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar (ProdutoTask produtoTask)
	{
		this.session.saveOrUpdate(produtoTask);
	}
	
	public void excluir(ProdutoTask produtoTask)
	{
		this.session.delete(produtoTask);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdutoTask> listar()
	{
		
		return this.session.createCriteria(ProdutoTask.class).list();
	}
	
	public ProdutoTask carregar(Integer id)
	{
		return (ProdutoTask) this.session.get(ProdutoTask.class, id);
	}

}
