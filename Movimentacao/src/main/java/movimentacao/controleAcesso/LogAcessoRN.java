package movimentacao.controleAcesso;

import java.util.List;

import movimentacao.util.DAOFactory;

public class LogAcessoRN 
{
	private LogAcessoDAO logAcessoDAO;
	
	public LogAcessoRN()
	{
		this.logAcessoDAO = DAOFactory.criarLogAcesso();
	}
	
	public LogAcesso carregar(Integer codigo)
	{
		return this.logAcessoDAO.carregar(codigo);
	}
	
	public LogAcesso buscaPorSessionId(String sessionId)
	{
		return this.logAcessoDAO.buscaPorSessionId(sessionId);
	}
	
	public void salvar(LogAcesso logAcesso)
	{
		this.logAcessoDAO.salvar(logAcesso);
	}
	
	public void excluir(LogAcesso logAcesso)
	{
		this.logAcessoDAO.excluir(logAcesso);
	}
	
	public List<LogAcesso> listar()
	{
		return this.logAcessoDAO.listar();
	}

}
