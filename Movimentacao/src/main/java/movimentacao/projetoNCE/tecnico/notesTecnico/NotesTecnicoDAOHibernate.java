package movimentacao.projetoNCE.tecnico.notesTecnico;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class NotesTecnicoDAOHibernate implements NotesTecnicoDAO
{
	
	private Session session;
	
	public void setSession(Session session)
	{
		this.session = session;
	}
	
	public void salvar(NotesTecnico notesTecnico)
	{
		this.session.saveOrUpdate(notesTecnico);
	}
	
	public void excluir(NotesTecnico notesTecnico)
	{
		this.session.delete(notesTecnico);
	}
	
	public NotesTecnico carregar(Integer id)
	{
		return (NotesTecnico) this.session.get(NotesTecnico.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<NotesTecnico> listar()
	{
		return this.session.createCriteria(NotesTecnico.class).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<NotesTecnico> listarPorTecnico(Integer idTecnico)
	{
		Criteria criteria = this.session.createCriteria(NotesTecnico.class);
		
		criteria.add(Restrictions.eq("idTecnico", idTecnico));
		criteria.addOrder(Order.desc("dataHoraReg"));
		
		return criteria.list();
	}
}
