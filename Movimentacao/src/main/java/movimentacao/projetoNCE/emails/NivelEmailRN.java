package movimentacao.projetoNCE.emails;

import java.util.List;

import movimentacao.util.DAOFactory;

public class NivelEmailRN
{
	private NivelEmailDAO nivelEmailDAO;
	
	public NivelEmailRN()
	{
		this.nivelEmailDAO = DAOFactory.criarNivelEmailDAO();
	}
	
	public void salvar(NivelEmail nivelEmail)
	{
		this.nivelEmailDAO = DAOFactory.criarNivelEmailDAO();
		this.nivelEmailDAO.salvar(nivelEmail);
	}
	
	public void excluir(NivelEmail nivelEmail)
	{
		this.nivelEmailDAO = DAOFactory.criarNivelEmailDAO();
		this.nivelEmailDAO.excluir(nivelEmail);
	}
	
	public List<NivelEmail> listar()
	{
		this.nivelEmailDAO = DAOFactory.criarNivelEmailDAO();
		
		return this.nivelEmailDAO.listar();
	}

}
