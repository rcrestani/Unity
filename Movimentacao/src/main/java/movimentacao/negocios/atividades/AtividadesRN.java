package movimentacao.negocios.atividades;

import java.util.List;

import movimentacao.negocios.Negocios;
import movimentacao.util.DAOFactory;

public class AtividadesRN 
{
	private AtividadesDAO atividadesDAO;
	
	public AtividadesRN()
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
	}
	
	public void salvar(Atividades atividades)
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
		this.atividadesDAO.salvar(atividades);
	}
	
	public void excluir(Atividades atividades)
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
		this.atividadesDAO.excluir(atividades);
	}
	
	public List<Atividades> listar(AtividadesFiltro filtro)
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
		return this.atividadesDAO.listar(filtro);
	}
	
	public List<Atividades> listarPendentes()
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
		return this.atividadesDAO.listarPendentes();
	}
	
	public Atividades carregar(Integer id)
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
		return this.atividadesDAO.carregar(id);
	}
	
	public Atividades ultimaAtividade(Negocios negocios)
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
		return this.atividadesDAO.ultimaAtividade(negocios);
	}
	
	public List<Atividades> atividadesDoDia()
	{
		this.atividadesDAO = DAOFactory.criarAtividadesDAO();
		return this.atividadesDAO.atividadesDoDia();
	}

}
