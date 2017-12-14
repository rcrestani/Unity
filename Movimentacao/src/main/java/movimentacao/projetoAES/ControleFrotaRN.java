package movimentacao.projetoAES;

import movimentacao.util.DAOFactory;

public class ControleFrotaRN
{
	private ControleFrotaDAO controleFrotaDAO;
	
	public ControleFrotaRN()
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
	}
	
	public void salvar(ControleFrota controleFrota)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		this.controleFrotaDAO.salvar(controleFrota);
	}

}
