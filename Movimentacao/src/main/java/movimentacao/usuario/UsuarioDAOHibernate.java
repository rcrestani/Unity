package movimentacao.usuario;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
	
	@SuppressWarnings("unchecked")
	public List<String> completeText(String query)
	{
		String hql = "select nome from usuario where nome like :text and ativo = true and cliente = 'DGR SISTEMAS' order by nome asc";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+query+"%");
		
		return (List<String>)consulta.list();
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
	public List<String> completeNome(String text)
	{
		String hql = "select nome from usuario where nome like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeLogin(String text)
	{
		String hql = "select login from usuario where login like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeEmail(String text)
	{
		String hql = "select email from usuario where email like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeCliente(String text)
	{
		String hql = "select cliente from usuario where cliente like :text group by cliente";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodosPaginado(UsuarioFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(UsuarioFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(UsuarioFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(Usuario.class);
		boolean status = Boolean.parseBoolean(filtro.getStatus());
		
		if(StringUtils.isNotEmpty(filtro.getStatus()))
		{
			
			criteria.add(Restrictions.eq("ativo", status));
		}
		
		if(StringUtils.isNotEmpty( filtro.getNome()))
		{
			criteria.add(Restrictions.eq("nome", filtro.getNome()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getLogin()))
		{
			criteria.add(Restrictions.eq("login", filtro.getLogin()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getEmail()))
		{
			criteria.add(Restrictions.eq("email", filtro.getEmail()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getCliente()))
		{
			criteria.add(Restrictions.eq("cliente", filtro.getCliente()));
		}
		
		return criteria;
	}

}
