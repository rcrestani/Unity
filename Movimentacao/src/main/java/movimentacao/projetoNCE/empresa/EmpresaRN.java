package movimentacao.projetoNCE.empresa;

import java.util.List;

import movimentacao.util.DAOFactory;

public class EmpresaRN
{
	private EmpresaDAO empresaDAO;
	private EmpresaFiltro filtro;
	
	public EmpresaRN()
	{
		this.empresaDAO = DAOFactory.criarEmpresaDAO();
	}
	
	public void salvar(Empresa empresa)
	{
		this.empresaDAO = DAOFactory.criarEmpresaDAO();
		this.empresaDAO.salvar(empresa);
	}
	
	public void excluir(Empresa empresa)
	{
		this.empresaDAO = DAOFactory.criarEmpresaDAO();
		this.empresaDAO.excluir(empresa);
	}
	
	public Empresa carregar(Integer id)
	{
		this.empresaDAO = DAOFactory.criarEmpresaDAO();
		return this.empresaDAO.carregar(id);
	}
	
	public List<Empresa> listar()
	{
		this.empresaDAO = DAOFactory.criarEmpresaDAO();
		return this.empresaDAO.listar();
	}
	
	public EmpresaFiltro getFiltro() {
		return filtro;
	}
	public void setFiltro(EmpresaFiltro filtro) {
		this.filtro = filtro;
	}
}
