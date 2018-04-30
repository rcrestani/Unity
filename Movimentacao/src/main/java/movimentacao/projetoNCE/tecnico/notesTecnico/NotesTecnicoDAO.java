package movimentacao.projetoNCE.tecnico.notesTecnico;

import java.util.List;

public interface NotesTecnicoDAO
{
	public void salvar(NotesTecnico notesTecnico);
	public void excluir(NotesTecnico notesTecnico);
	public NotesTecnico carregar(Integer id);
	public List<NotesTecnico> listar();
	public List<NotesTecnico> listarPorTecnico(Integer idTecnico);

}
