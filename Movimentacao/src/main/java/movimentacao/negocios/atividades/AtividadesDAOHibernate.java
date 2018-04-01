package movimentacao.negocios.atividades;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;
import movimentacao.negocios.Negocios;
import movimentacao.negocios.NegociosRN;

public class AtividadesDAOHibernate implements AtividadesDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Atividades atividades) 
	{
		this.session.saveOrUpdate(atividades);
	}

	public void excluir(Atividades atividades) 
	{
		this.session.delete(atividades);
	}

	@SuppressWarnings("unchecked")
	public List<Atividades> listar(AtividadesFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(Atividades.class);
		
		if(StringUtils.isNotEmpty( filtro.getOrdenacao() ) && filtro.getOrdenacao().equals("recentes"))
		{
			criteria.addOrder(Order.desc("prazo"));
		}
		else if(StringUtils.isNotEmpty( filtro.getOrdenacao() ) && filtro.getOrdenacao().equals("antigas"))
		{
			criteria.addOrder(Order.asc("prazo"));
		}
		else
		{
			criteria.addOrder(Order.asc("finalizado"));
			criteria.addOrder(Order.asc("prazo"));
		}
		
		if(filtro.getInicial() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("prazo", filtro.getInicial()));
		}
		else if(filtro.getFim() != null && filtro.getInicial() == null)
		{
			criteria.add(Restrictions.le("prazo", filtro.getFim()));
		}
		else if(filtro.getInicial() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("prazo", filtro.getInicial() , filtro.getFim()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getCliente() ))
		{
			Cliente cliente = new Cliente();
			ClienteRN clienteRN = new ClienteRN();
			cliente = clienteRN.buscarPorNome(filtro.getCliente());
			NegociosRN negociosRN = new NegociosRN();
			
			criteria.add(Restrictions.eq("negocios", negociosRN.buscaPorCliente(cliente) ));
		}
		
		if(StringUtils.isNotEmpty( filtro.getStatus() ) && filtro.getStatus().equals("true"))
		{
			criteria.add(Restrictions.eq("finalizado", true));
		}
		else if(StringUtils.isNotEmpty( filtro.getStatus() ) && filtro.getStatus().equals("false"))
		{
			criteria.add(Restrictions.eq("finalizado", false));
		}
		
		if(StringUtils.isNotEmpty( filtro.getTipoAtividade() ))
		{
			criteria.add(Restrictions.eq("tipoAtividade", filtro.getTipoAtividade() ));
		}
		
		if(filtro.getUsuario() != null )
		{
			criteria.add(Restrictions.eq("usuario", filtro.getUsuario() ));
		}
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Atividades> listarPendentes()
	{
		Criteria criteria = this.session.createCriteria(Atividades.class);
		
		Date dataHoje = new Date();
		
		criteria.add(Restrictions.eq("finalizado", false));
		
		criteria.add(Restrictions.le("prazo", dataHoje));
		
		criteria.addOrder(Order.asc("prazo"));
		
		return criteria.list();
	}
	
	public Atividades carregar(Integer id) 
	{
		return (Atividades) this.session.get(Atividades.class, id);
	}
	
	public Atividades ultimaAtividade(Negocios negocios)
	{
		Criteria criteria = this.session.createCriteria(Atividades.class);
		
		criteria.add(Restrictions.eq("negocios", negocios));
		criteria.addOrder(Order.desc("prazo"));
		criteria.setMaxResults(1);
		
		return (Atividades)criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Atividades> atividadesDoDia()
	{
		Date dataHoje = new Date();
		//Consulta busca as atividades pendentes do dia=========================================================
		String hql = "from atividades where date(prazo) = :dataHoje and finalizado = false order by prazo asc";
		Query consulta = this.session.createQuery(hql);
		consulta.setDate("dataHoje", dataHoje);
		
		return (List<Atividades>)consulta.list();
	}
	
}
