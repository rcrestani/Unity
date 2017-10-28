package movimentacao.negocios;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;

public class NegociosDAOHibernate implements NegociosDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Negocios negocios) 
	{
		this.session.saveOrUpdate(negocios);
	}

	public void excluir(Negocios negocios) 
	{
		this.session.delete(negocios);
	}

	public Negocios carregar(Integer codigo) 
	{
		return (Negocios) this.session.get(Negocios.class, codigo);
	}
	
	public Negocios buscaPorCliente(Cliente cliente) 
	{
		int codCliente = cliente.getCodigo();
		
		Query consulta = this.session.createQuery("from negocios where cod_cliente = :cod_cliente");
		consulta.setInteger("cod_cliente", codCliente);
		return (Negocios) consulta.uniqueResult();
	}

	public List<String> negociosPorCliente(String query)
	{	
		List<Negocios> negocios = new ArrayList<Negocios>();
        
		ClienteRN clienteRN = new ClienteRN();
        List<String> nomes = clienteRN.completeText(query);
		List<Cliente> cliente = new ArrayList<Cliente>();
		List<String> results = new ArrayList<String>();
        
        for(int x = 0; x < nomes.size(); x++)
        {
        	cliente.add(clienteRN.buscarPorNome(nomes.get(x)));
        }
        
		for(int i = 0; i < cliente.size(); i++)
		{
			if(buscaPorCliente(cliente.get(i)) != null)
			{
				negocios.add(buscaPorCliente(cliente.get(i)));
			}
		}
        
        for(int y=0; y < negocios.size(); y++)
        {
        	if(!negocios.get(y).getStatus().equals("Declined"))
        	{
	        	try
	        	{
	        		results.add(negocios.get(y).getCliente().getNome());
	        	}
	        	catch(Exception e)
	        	{
	        		System.out.println("Fim do FOR: " + e.getMessage());
	        	}
        	}
        }
         
        return results;
    }
	
	@SuppressWarnings("unchecked")
	public List<Negocios> listar() 
	{
		return this.session.createCriteria(Negocios.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Negocios> negociosPreVenda()
	{
		Criteria criteria = this.session.createCriteria(Negocios.class);
		
		criteria.add(Restrictions.ne("status", "Active Client"));
		criteria.add(Restrictions.ne("status", "Declined"));
		criteria.addOrder(Order.asc("dataHora"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Negocios> buscarTodosPaginado(NegociosFiltro filtro)
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
	public int quantidadeFiltrados(NegociosFiltro filtro)
	{
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	public Criteria criarCriteriaParaFiltro(NegociosFiltro filtro)
	{
		Criteria criteria = this.session.createCriteria(Negocios.class);
				
		if(filtro.getInicial() != null && filtro.getFim() == null)
		{
			criteria.add(Restrictions.ge("dataHora", filtro.getInicial()));
		}
		else if(filtro.getFim() != null && filtro.getInicial() == null)
		{
			criteria.add(Restrictions.le("dataHora", filtro.getFim()));
		}
		else if(filtro.getInicial() != null && filtro.getFim() != null)
		{
			criteria.add(Restrictions.between("dataHora", filtro.getInicial() , filtro.getFim()));
		}
		
		if(StringUtils.isNotEmpty( filtro.getCliente() ))
		{
			ClienteRN clienteRN = new ClienteRN();
			
			if(filtro.getCliente().length() > 3)
			{
				List<String> nomesClientes = negociosPorCliente(filtro.getCliente());
				List<Cliente> clientes = new ArrayList<Cliente>();
				
				for(int z = 0; z < nomesClientes.size(); z++)
				{
					clientes.add(clienteRN.buscarPorNome(nomesClientes.get(z)));
				}
				
				criteria.add(Restrictions.in("cliente", clientes));
			}
			
		}
		
		if(StringUtils.isNotEmpty( filtro.getStatus() ))
		{
			criteria.add(Restrictions.eq("status", filtro.getStatus()));
		}
		
		return criteria;
	}

}
