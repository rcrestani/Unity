package movimentacao.projetoNCE.tecnico;

import java.util.List;

public interface TecnicoDAO
{
	public void salvar(Tecnico tecnico);
	public void atualizarEvict(Tecnico tecnico);
	public void excluir(Tecnico tecnico);
	public Tecnico carregar (Integer id);
	public Tecnico tecnicoPorCPF(String cpf);
	public List<String> completeNome(String text);
	public List<Tecnico> listar();
	public List<String> completeTecnico(String text);
	public Tecnico tecnicoPorNome(String nome);
	public List<Tecnico> buscarTodosPaginado(TecnicoFiltro filtro);
	public int quantidadeFiltrados(TecnicoFiltro filtro);
}
