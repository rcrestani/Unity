package movimentacao.projetoNCE.status;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class StatusRequisicaoDAOHibernate implements StatusRequisicaoDAO
{
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(StatusRequisicao status)
	{
		this.session.saveOrUpdate(status);
	}
	
	public void excluir(StatusRequisicao status)
	{
		this.session.delete(status);
	}
	
	public StatusRequisicao carregar(Integer id)
	{
		return (StatusRequisicao) this.session.get(StatusRequisicao.class, id);
	}
	
	public StatusRequisicao buscarPorNome(String nomeStatus)
	{
		Criteria criteria = this.session.createCriteria(StatusRequisicao.class);
		
		criteria.add(Restrictions.eq("nomeStatus", nomeStatus));
		
		return (StatusRequisicao) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusRequisicao> listar()
	{
		return this.session.createCriteria(StatusRequisicao.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> listarAtivos(String text)
	{
		String hql = "select nomeStatus from nce_statusRequisicao where nomeStatus like :text and ativo = true";
		Query consulta = this.session.createQuery(hql);
		consulta.setString("text", "%"+text+"%");
		
		return (List<String>)consulta.list();
	}

}
