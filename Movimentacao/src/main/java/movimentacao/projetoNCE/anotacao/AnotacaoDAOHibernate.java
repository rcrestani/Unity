package movimentacao.projetoNCE.anotacao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import movimentacao.projetoNCE.ControleChave;

public class AnotacaoDAOHibernate implements AnotacaoDAO
{
private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(Anotacao anotacao)
	{
		this.session.saveOrUpdate(anotacao);
	}
	
	@SuppressWarnings("unchecked")
	public List<Anotacao> listar()
	{
		return this.session.createCriteria(Anotacao.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Anotacao> listarPorReq(ControleChave controleChave)
	{
		Criteria criteria = this.session.createCriteria(Anotacao.class);
		
		criteria.add(Restrictions.eq("idReq", controleChave));
		
		return criteria.list();
	}

}
