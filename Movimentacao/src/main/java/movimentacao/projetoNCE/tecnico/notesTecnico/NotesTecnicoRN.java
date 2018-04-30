package movimentacao.projetoNCE.tecnico.notesTecnico;

import java.util.List;

import movimentacao.util.DAOFactory;

public class NotesTecnicoRN
{
	private NotesTecnicoDAO notesTecnicoDAO;
	
	public NotesTecnicoRN()
	{
		this.notesTecnicoDAO = DAOFactory.criarNotesTecnicoDAO();
	}
	
	public void salvar(NotesTecnico notesTecnico)
	{
		this.notesTecnicoDAO = DAOFactory.criarNotesTecnicoDAO();
		this.notesTecnicoDAO.salvar(notesTecnico);
	}
	
	
	public void excluir(NotesTecnico notesTecnico)
	{
		this.notesTecnicoDAO = DAOFactory.criarNotesTecnicoDAO();
		this.notesTecnicoDAO.excluir(notesTecnico);
	}
	
	public NotesTecnico carregar(Integer id)
	{
		return this.notesTecnicoDAO.carregar(id);
	}
	
	public List<NotesTecnico> listar()
	{
		this.notesTecnicoDAO = DAOFactory.criarNotesTecnicoDAO();
		return this.notesTecnicoDAO.listar();
	}
	
	public List<NotesTecnico> listarPorTecnico(Integer idTecnico)
	{
		this.notesTecnicoDAO = DAOFactory.criarNotesTecnicoDAO();
		return this.notesTecnicoDAO.listarPorTecnico(idTecnico);
	}

}
