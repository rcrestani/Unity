package movimentacao.negocios.atividades;

import java.util.List;

import movimentacao.negocios.Negocios;

public interface AtividadesDAO 
{
	public void salvar(Atividades atividades);
	public void excluir(Atividades atividades);
	public List<Atividades> listar(AtividadesFiltro filtro);
	public List<Atividades> listarPendentes();
	public Atividades carregar(Integer id);
	public Atividades ultimaAtividade(Negocios negocios);
	public List<Atividades> atividadesDoDia();
}
