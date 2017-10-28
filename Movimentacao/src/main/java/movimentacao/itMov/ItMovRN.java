package movimentacao.itMov;

import java.util.List;

import movimentacao.mov.Mov;
import movimentacao.util.DAOFactory;

public class ItMovRN 
{
	private ItMovDAO itMovDAO;
	
	public ItMovRN()
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
	}
	
	public void salvar(ItMov itMov)
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
		this.itMovDAO.salvar(itMov);
	}
	
	public void excluir(ItMov itMov)
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
		this.itMovDAO.excluir(itMov);
	}
	
	public ItMov carregar(Integer id)
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
		return this.itMovDAO.carregar(id);
	}
	
	public List<ItMov> listar()
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
		return this.itMovDAO.listar();
	}
	
	public List<ItMov> itensMov(Mov mov)
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
		return this.itMovDAO.itensMov(mov);
	}
	
	public List<ItMov> tipoMov(String tipoMovi)
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
		return this.itMovDAO.tipoMov(tipoMovi);
	}
	
	public List<ItMov> itMovFechamento(Mov mov , String tipoMovi)
	{
		this.itMovDAO = DAOFactory.criarItMovDAO();
		return this.itMovDAO.itMovFechamento(mov, tipoMovi);
	}
}
