package movimentacao.projetoNCE.chave;

import java.util.List;

import movimentacao.projetoNCE.ControleChave;
import movimentacao.projetoNCE.site.Site;

public interface ChaveDAO
{
	public void salvar(Chave chave);
	public void excluir(Chave chave);
	public Chave carregar(Integer id);
	public List<Chave> listar();
	public List<String> completeChave(String text);
	public Chave chavePorNome(String nome);
	public List<Chave> listaPorSiteStatusTrue(Site site);
	public List<Chave> listaPorSelecaoTrue(ControleChave controleChave);
	public List<Chave> buscarTodosPaginado(ChaveFiltro filtro);
	public int quantidadeFiltrados(ChaveFiltro filtro);
}
