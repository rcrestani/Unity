package movimentacao.produto;


import java.util.ArrayList;
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

public class ProdutoDAOHibernate implements ProdutoDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar (Produto produto)
	{
		this.session.saveOrUpdate(produto);
	}
	
	public void excluir(Produto produto)
	{
		this.session.delete(produto);
	}
	
	public Produto carregar(Integer codigo)
	{
		return (Produto) this.session.get(Produto.class, codigo);
	}
	
	public Produto buscarPorDescricao(String descricao)
	{
		String hql = "select u from produto u where u.descricao = :descricao";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("descricao", descricao);
		
		return (Produto) consulta.uniqueResult();
	}
	
	public List<String> completeText(String query)
	{
		Criteria criteria = session.createCriteria(Produto.class);
		criteria.add(Restrictions.ilike("descricao", query, MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("descricao"));
		
		@SuppressWarnings("unchecked")
		List<Produto> produto = criteria.list();
		List<String> results = new ArrayList<String>();
		
        int n = produto.size();
        
        for(int i=0; i < n; i++)
        {
            Produto pdt = produto.get(i);
            results.add(pdt.getDescricao());
        }
         
        return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> listar()
	{
		
		return this.session.createCriteria(Produto.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> buscarTodosPaginado(ProdutoFiltro filtro)
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
	
	public int quantidadeFiltrados(ProdutoFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(ProdutoFiltro filtro)
	{
		
		Criteria criteria = session.createCriteria(Produto.class);
		boolean status = Boolean.parseBoolean(filtro.getStatus());
		
		
		if (StringUtils.isNotEmpty( filtro.getDescricao() ) && StringUtils.isNotEmpty( filtro.getStatus() ))
		{
		
				Criterion descricao = Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE);
				Criterion sts = Restrictions.eq("status", status);
				
				LogicalExpression andExp = Restrictions.and(descricao , sts);
				criteria.add(andExp);

		}
		else if(StringUtils.isNotEmpty( filtro.getDescricao() ))
		{
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		}
		else if(StringUtils.isNotEmpty( filtro.getStatus() ))
		{
			criteria.add(Restrictions.eq("status", status));
		}
		
		return criteria;
	}
	
}
