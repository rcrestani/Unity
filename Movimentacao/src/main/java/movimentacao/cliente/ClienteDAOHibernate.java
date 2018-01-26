package movimentacao.cliente;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class ClienteDAOHibernate implements ClienteDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Cliente cliente)
	{
		this.session.saveOrUpdate(cliente);
	}
	
	public void excluir(Cliente cliente)
	{
		this.session.delete(cliente);
	}
	
	public Cliente carregar(Integer codigo)
	{
		return (Cliente) this.session.get(Cliente.class, codigo);
	}
	
	public Cliente buscarPorNome(String nome)
	{
		String hql = "select u from cliente u where u.nome = :nome";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("nome", nome);
		
		return (Cliente) consulta.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeText(String query) 
	{
		String hql = "select nome from cliente where nome like :text and status = true order by nome asc";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+query+"%");
		
		return (List<String>)consulta.list();
    }
	
	@SuppressWarnings({ "null", "unchecked" })
	public List<String> buscaPorExcecao(String cliente)
	{
		Criteria criteria = this.session.createCriteria(Cliente.class);
		criteria.add(Restrictions.ne("nome", cliente));
		List<Cliente> clientes = criteria.list();
		
		List<String> results = null;
		
		for(int x = 0; x < clientes.size(); x++)
		{
			results.add(clientes.get(x).getNome());
		}
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> buscaPorParticipacao(String filtro)
	{
		Criteria criteria = this.session.createCriteria(Cliente.class);
		criteria.add(Restrictions.ilike("nome", filtro, MatchMode.ANYWHERE));
		
		return criteria.list(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> listar()
	{
		return this.session.createCriteria(Cliente.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> buscarTodosPaginado(ClienteFiltro filtro)
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

	public int quantidadeFiltrados(ClienteFiltro filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(ClienteFiltro filtro) {
		
		Criteria criteria = session.createCriteria(Cliente.class);
		boolean status = Boolean.parseBoolean(filtro.getStatus());
		
		
		if (StringUtils.isNotEmpty( filtro.getNome() ) && StringUtils.isNotEmpty( filtro.getStatus() ))
		{
		
				Criterion nome = Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE);
				Criterion sts = Restrictions.eq("status", status);
				
				LogicalExpression andExp = Restrictions.and(nome , sts);
				criteria.add(andExp);

		}
		else if(StringUtils.isNotEmpty( filtro.getNome() ))
		{
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		else if(StringUtils.isNotEmpty( filtro.getStatus() ))
		{
			criteria.add(Restrictions.eq("status", status));
		}
		
		
		return criteria;
	}

}
