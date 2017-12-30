package movimentacao.projetoNCE.status;

import java.util.List;

import movimentacao.util.DAOFactory;

public class StatusRequisicaoRN
{
	private StatusRequisicaoDAO statusRequisicaoDAO;
	private StatusRequisicaoFiltro filtro;
	
	public StatusRequisicaoRN()
	{
		this.statusRequisicaoDAO = DAOFactory.criarStatusRequisicaoDAO();
	}
	
	public void salvar(StatusRequisicao status)
	{
		this.statusRequisicaoDAO = DAOFactory.criarStatusRequisicaoDAO();
		this.statusRequisicaoDAO.salvar(status);
	}
	
	public void excluir(StatusRequisicao status)
	{
		this.statusRequisicaoDAO = DAOFactory.criarStatusRequisicaoDAO();
		this.statusRequisicaoDAO.excluir(status);
	}
	
	public StatusRequisicao carregar(Integer id)
	{
		this.statusRequisicaoDAO = DAOFactory.criarStatusRequisicaoDAO();
		
		return this.statusRequisicaoDAO.carregar(id);
	}
	
	public List<StatusRequisicao> listar()
	{
		this.statusRequisicaoDAO = DAOFactory.criarStatusRequisicaoDAO();
		
		return this.statusRequisicaoDAO.listar();
	}
	
	public StatusRequisicaoFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(StatusRequisicaoFiltro filtro) {
		this.filtro = filtro;
	}
	
}
