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
	public Chave chavePorId(Integer id);
	public List<Chave> listaPorSiteStatusTrue(Site site);
	public List<Chave> listaPorSiteSelecaoTrue(Site site , ControleChave controleChave);
}
