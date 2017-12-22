package movimentacao.projetoAES;

import java.util.Date;
import java.util.List;

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
	
	public ControleFrota consultaRegistroSaida(String vtr, Date data)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.consultaRegistroSaida(vtr, data);
	}
	
	public ControleFrota ultimoRegistro()
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.ultimoRegistro();
	}
	
	public List<ControleFrota> listar()
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.listar();
	}
	
	public List<ControleFrota> consultaLista(ControleFrotaFiltro filtro)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.consultaLista(filtro);
	}
	
	public List<String> completeColetor(String text)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.completeColetor(text);
	}
	
	public List<String> completeVtr(String text)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.completeVtr(text);
	}
	public List<String> completeCondutor(String text)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.completeCondutor(text);
	}
	
	public List<ControleFrota> buscarTodosPaginado(ControleFrotaFiltro filtro)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.buscarTodosPaginado(filtro);
	}
	public int qtdeFiltrados(ControleFrotaFiltro filtro)
	{
		this.controleFrotaDAO = DAOFactory.criarControleFrotaDAO();
		return this.controleFrotaDAO.qtdeFiltrados(filtro);
	}
}
