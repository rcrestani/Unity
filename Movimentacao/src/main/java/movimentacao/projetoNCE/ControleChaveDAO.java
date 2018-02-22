package movimentacao.projetoNCE;

import java.util.List;

public interface ControleChaveDAO
{
	public void salvar(ControleChave controleChave);
	public void excluir(ControleChave controleChave);
	public ControleChave carregar(Integer id);
	public ControleChave ultimoRegistro();
	public List<ControleChave> listar();
	public List<ControleChave> listarAberto();
	public List<ControleChave> listarNovas();
	public List<ControleChave> buscarTodosPaginado(ControleChaveFiltro filtro);
	public int qtdeFiltrados(ControleChaveFiltro filtro);
}
