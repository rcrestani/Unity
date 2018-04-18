package movimentacao.projetoNCE.controleSiteChave;

import java.util.List;

import movimentacao.projetoNCE.ControleChave;
import movimentacao.util.DAOFactory;

public class ControleSiteChaveRN
{
	private ControleSiteChaveDAO controlDAO;

	public ControleSiteChaveRN()
	{
		this.controlDAO = DAOFactory.criarControleSiteChaveDAO();
	}
	
	public void salvar(ControleSiteChave controleSiteChave)
	{
		this.controlDAO = DAOFactory.criarControleSiteChaveDAO();
		this.controlDAO.salvar(controleSiteChave);
	}
	
	public List<ControleSiteChave> sitesChavesPorReq(ControleChave controleChave)
	{
		this.controlDAO = DAOFactory.criarControleSiteChaveDAO();
		
		return this.controlDAO.sitesChavesPorReq(controleChave);
	}
}
