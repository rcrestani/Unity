package movimentacao.projetoNCE.controleSiteChave;

import java.util.List;

import movimentacao.projetoNCE.ControleChave;

public interface ControleSiteChaveDAO
{
	public void salvar(ControleSiteChave controleSiteChave);
	public List<ControleSiteChave> sitesChavesPorReq(ControleChave controleChave);
}
