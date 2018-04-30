package movimentacao.projetoNCE.anotacao;

import java.util.List;

import movimentacao.projetoNCE.ControleChave;

public interface AnotacaoDAO
{
	public void salvar(Anotacao anotacao);
	public List<Anotacao> listar();
	public List<Anotacao> listarPorReq(ControleChave controleChave);
}
