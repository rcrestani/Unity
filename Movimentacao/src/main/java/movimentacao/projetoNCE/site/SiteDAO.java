package movimentacao.projetoNCE.site;

import java.util.List;

public interface SiteDAO
{
	public void salvar(Site site);
	public void excluir(Site site);
	public Site carregar(Integer id);
	public List<Site> listar();
	public List<String> completeSite(String text);
	public Site sitePorIdCodAtual(String idCodAtual);
}
