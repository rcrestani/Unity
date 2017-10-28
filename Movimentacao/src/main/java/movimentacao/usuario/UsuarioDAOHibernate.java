package movimentacao.usuario;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

public class UsuarioDAOHibernate implements UsuarioDAO{

	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Usuario usuario)
	{
		this.session.save(usuario);
	}
	
	public void atualizar(Usuario usuario)
	{	
		this.session.update(usuario);
	}
	
	public void atualizarEvict(Usuario usuario)
	{
		this.session.evict(usuario);
	}
	
	public void excluir(Usuario usuario)
	{
		this.session.delete(usuario);
	}
	
	public Usuario carregar(Integer codigo)
	{
		return (Usuario) this.session.get(Usuario.class, codigo);
	}
	
	public List<String> completeText(String query)
	{
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.ilike("nome", query, MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("nome"));
		
		@SuppressWarnings("unchecked")
		List<Usuario> usuario = criteria.list();
		List<String> results = new ArrayList<String>();
		
        int n = usuario.size();
        
        for(int i=0; i < n; i++)
        {
            Usuario usr = usuario.get(i);
            results.add(usr.getNome());
        }
         
        return results;
    }
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listar()
	{
		return this.session.createCriteria(Usuario.class).list();
	}
	
	public Usuario buscarPorLogin(String login)
	{
		String hql = "select u from usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("login", login);
		
		return (Usuario) consulta.uniqueResult();
	}
	
	public Usuario buscarPorNome(String nome)
	{
		String hql = "select u from usuario u where u.nome = :nome";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("nome", nome);
		
		return (Usuario) consulta.uniqueResult();
	}
	
	 @SuppressWarnings("unchecked")
	public List<Usuario> buscarTodosPaginado(int first, int pageSize, String sortField, SortOrder sortOrder)
	 {
		 List<Usuario> data = new ArrayList<Usuario>();
		 Criteria criteria = this.session.createCriteria(Usuario.class);

		 if (sortField != null)
		 {
			 boolean sort = SortOrder.ASCENDING.equals(sortOrder);
			 
			 if(sort == true)
			 {
				 criteria.addOrder(Order.asc(sortField));
			 }
			 else
			 {
				 criteria.addOrder(Order.desc(sortField));
			 }
				 
		 }
		 
		 data = criteria.list();

		 if (pegarQuantidadeDeUsuarios() > pageSize)
		 {
			 try
			 {
				 return data.subList(first, first + pageSize);
			 }
			 catch(IndexOutOfBoundsException e) 
			 {
	                return data.subList(first, first + (pegarQuantidadeDeUsuarios() % pageSize));
	         }
		 }
		 
		 return data;
	}

	public int pegarQuantidadeDeUsuarios()
	{
		int rows =  this.session.createCriteria(Usuario.class).list().size();
		
		return rows;  
	}

}
