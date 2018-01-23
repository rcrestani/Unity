package movimentacao.projetoNCE.status;

import java.util.List;

public interface StatusRequisicaoDAO 
{
	public void salvar(StatusRequisicao status);
	public void excluir(StatusRequisicao status);
	public StatusRequisicao carregar(Integer id);
	public List<StatusRequisicao> listar();

}
