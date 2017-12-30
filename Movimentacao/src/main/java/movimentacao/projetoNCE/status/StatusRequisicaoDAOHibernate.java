package movimentacao.projetoNCE.status;

import java.util.List;

import org.hibernate.Session;


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
	
	@SuppressWarnings("unchecked")
	public List<StatusRequisicao> listar()
	{
		return this.session.createCriteria(StatusRequisicao.class).list();
	}

}
