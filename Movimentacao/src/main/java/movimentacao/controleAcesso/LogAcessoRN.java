package movimentacao.controleAcesso;

import java.util.List;
import movimentacao.util.DAOFactory;

public class LogAcessoRN 
{
	private LogAcessoDAO logAcessoDAO;
	private LogAcessoFiltro filtro;
	
	public LogAcessoRN()
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
	}
	
	public LogAcesso carregar(Integer codigo)
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		return this.logAcessoDAO.carregar(codigo);
	}
	
	public LogAcesso buscaPorSessionId(String sessionId)
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		return this.logAcessoDAO.buscaPorSessionId(sessionId);
	}
	
	public void salvar(LogAcesso logAcesso)
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		this.logAcessoDAO.salvar(logAcesso);
	}
	
	public void excluir(LogAcesso logAcesso)
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		this.logAcessoDAO.excluir(logAcesso);
	}
	
	public List<LogAcesso> listar()
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		return this.logAcessoDAO.listar();
	}

	public List<String> completeLogin(String text)
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		return this.logAcessoDAO.completeLogin(text);
	}
	
	public List<LogAcesso> buscarTodosPaginado(LogAcessoFiltro filtro)
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		return this.logAcessoDAO.buscarTodosPaginado(filtro);
	}
	
	public int qtdeFiltrados(LogAcessoFiltro filtro)
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
		return this.logAcessoDAO.qtdeFiltrados(filtro);
	}
	
	public LogAcessoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(LogAcessoFiltro filtro) {
		this.filtro = filtro;
	}

}
