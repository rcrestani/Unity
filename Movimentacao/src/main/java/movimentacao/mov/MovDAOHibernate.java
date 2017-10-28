package movimentacao.mov;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;

public class MovDAOHibernate implements MovDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Mov mov)
	{
		this.session.saveOrUpdate(mov);
	}
	
	public void excluir(Mov mov)
	{
		this.session.delete(mov);
	}
	
	public Mov carregar(Integer id)
	{
		return (Mov) this.session.get(Mov.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Mov> listar()
	{
		return this.session.createCriteria(Mov.class).list();
	}
	
	public Mov buscaPorTalao(Integer talao)
	{
		Criteria criteria = this.session.createCriteria(Mov.class);
		criteria.add(Restrictions.eq("talao" , talao));
		
		return (Mov) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Mov> buscarTodosPaginado(MovFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) 
		{
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} 
		else if (filtro.getPropriedadeOrdenacao() != null) 
		{
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(MovFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(MovFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(Mov.class);
		
		
		
		if(filtro.getInicial() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("data", filtro.getInicial()));
		}
		else if(filtro.getFim() != null && filtro.getInicial() == null)
		{
			criteria.add(Restrictions.le("data", filtro.getFim()));
		}
		else if(filtro.getInicial() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("data", filtro.getInicial() , filtro.getFim()));
		}
		
		if(filtro.getTalao() != null)
		{
			criteria.add(Restrictions.eq("talao", filtro.getTalao()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getCliente() ))
		{
			ClienteRN clienteRN = new ClienteRN();
			
			criteria.add(Restrictions.eq("cliente", clienteRN.buscarPorNome(filtro.getCliente())));
		}
		
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<Mov> movFechamento(Date dataInicial , Date dataFinal)
	{
		Criteria criteria = this.session.createCriteria(Mov.class);
		
		criteria.add(Restrictions.between("data", dataInicial , dataFinal));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Mov> movFechamentoCliente(Cliente cliente , Date dataInicial , Date dataFinal)
	{
		Criteria criteria = this.session.createCriteria(Mov.class);
		
		criteria.add(Restrictions.eq("cliente" , cliente));
		criteria.add(Restrictions.between("data", dataInicial , dataFinal));
		
		return criteria.list();
	}
}
