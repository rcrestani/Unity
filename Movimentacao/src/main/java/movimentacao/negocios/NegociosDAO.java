package movimentacao.negocios;

import java.util.List;

import org.hibernate.Criteria;

import movimentacao.cliente.Cliente;

public interface NegociosDAO 
{
	public void salvar(Negocios negocios);
	public void excluir(Negocios negocios);
	public Negocios carregar(Integer codigo);
	public Negocios buscaPorCliente(Cliente cliente);
	public List<Negocios> listar();
	public List<Negocios> negociosPreVenda();
	public List<Negocios> buscarTodosPaginado(NegociosFiltro filtro);
	public int quantidadeFiltrados(NegociosFiltro filtro);
	public Criteria criarCriteriaParaFiltro(NegociosFiltro filtro);
	public List<String> negociosPorCliente(String query);
}
