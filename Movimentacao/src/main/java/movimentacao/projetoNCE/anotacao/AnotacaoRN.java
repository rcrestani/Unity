package movimentacao.projetoNCE.anotacao;

import java.util.List;

import movimentacao.projetoNCE.ControleChave;
import movimentacao.util.DAOFactory;

public class AnotacaoRN
{
	private AnotacaoDAO anotacaoDAO;
	
	public AnotacaoRN()
	{
		this.anotacaoDAO = DAOFactory.criarAnotacaoDAO();
	}
	
	public void salvar(Anotacao anotacao)
	{
		this.anotacaoDAO = DAOFactory.criarAnotacaoDAO();
		this.anotacaoDAO.salvar(anotacao);
	}
	
	public List<Anotacao> listar()
	{
		this.anotacaoDAO = DAOFactory.criarAnotacaoDAO();
		return this.anotacaoDAO.listar();
	}
	
	public List<Anotacao> listarPorReq(ControleChave controleChave)
	{
		this.anotacaoDAO = DAOFactory.criarAnotacaoDAO();
		return this.anotacaoDAO.listarPorReq(controleChave);
	}

}
