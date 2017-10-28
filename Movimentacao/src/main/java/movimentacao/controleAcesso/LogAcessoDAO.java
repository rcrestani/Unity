package movimentacao.controleAcesso;

import java.util.List;

public interface LogAcessoDAO 
{
	public void salvar(LogAcesso logAcesso);
	public void excluir(LogAcesso logAcesso);
	public LogAcesso carregar(Integer codigo);
	public LogAcesso buscaPorSessionId(String sessionId);
	public List<LogAcesso> listar();

}
