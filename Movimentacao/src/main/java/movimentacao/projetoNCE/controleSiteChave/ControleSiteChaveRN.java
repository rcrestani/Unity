package movimentacao.projetoNCE.controleSiteChave;

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
}
