package movimentacao.controleAcesso;

import java.util.List;

public interface LogAcessoDAO 
{
	public void salvar(LogAcesso logAcesso);
	public void excluir(LogAcesso logAcesso);
	public LogAcesso carregar(Integer codigo);
	public List<LogAcesso> listar();
	public LogAcesso buscaPorSessionId(String sessionId);
	public List<String> completeLogin(String text);
	public List<LogAcesso> buscarTodosPaginado(LogAcessoFiltro filtro);
	public int qtdeFiltrados(LogAcessoFiltro filtro);
}
