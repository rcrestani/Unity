package movimentacao.projetoNCE.tecnico;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class TecnicoDAOHibernate implements TecnicoDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Tecnico tecnico)
	{
		this.session.clear();
		this.session.saveOrUpdate(tecnico);
	}
	
	public void atualizarEvict(Tecnico tecnico)
	{
		this.session.evict(tecnico);
	}
	
	public void excluir(Tecnico tecnico)
	{
		this.session.delete(tecnico);
	}
	
	public Tecnico carregar(Integer id)
	{
		return (Tecnico) this.session.get(Tecnico.class, id);
	}
	
	public Tecnico tecnicoPorCPF(String cpf)
	{
		Criteria criteria = this.session.createCriteria(Tecnico.class);
		
		criteria.add(Restrictions.eq("cpf", cpf));
		
		return (Tecnico) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeNome(String text)
	{
		String hql = "select nome from nce_tecnico where nome like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tecnico> listar()
	{
		return this.session.createCriteria(Tecnico.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> completeTecnico(String text)
	{
		String hql = "select nome from nce_tecnico where nome like :text";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}
	
	public Tecnico tecnicoPorNome(String nome)
	{
		String hql = "select t from nce_tecnico t where t.nome = :nome";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("nome", nome);
		
		return (Tecnico) consulta.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tecnico> buscarTodosPaginado(TecnicoFiltro filtro)
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
		else
		{
			criteria.addOrder(Order.asc("nome"));
		}
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(TecnicoFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(TecnicoFiltro filtro)
	{
		Criteria criteria = session.createCriteria(Tecnico.class);
		
		if(filtro.getInicio() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("dataHoraReg", filtro.getInicio()));
		}
		else if(filtro.getFim() != null && filtro.getInicio() == null)
		{
			criteria.add(Restrictions.le("dataHoraReg", filtro.getFim()));
		}
		else if(filtro.getInicio() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("dataHoraReg", filtro.getInicio() , filtro.getFim()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getNome()))
		{
			criteria.add(Restrictions.eq("nome", filtro.getNome()));
		}
		
		try
		{
			if(StringUtils.isNotEmpty( filtro.getIdEmpresa().getRazaoSocial()))
			{
				criteria.add(Restrictions.eq("idEmpresa", filtro.getIdEmpresa()));
			}
		}
		catch (Exception e)
		{
			System.out.println("ERRO FILTRO TÉCNICO POR EMPRESA: " + e.getMessage());
		}
		
		if(StringUtils.isNotEmpty( filtro.getStatus() ) && filtro.getStatus().equals("true"))
		{
			criteria.add(Restrictions.eq("status", true));
		}
		else if(StringUtils.isNotEmpty( filtro.getStatus() ) && filtro.getStatus().equals("false"))
		{
			criteria.add(Restrictions.eq("status", false));
		}
		
		return criteria;
	}
	
}
