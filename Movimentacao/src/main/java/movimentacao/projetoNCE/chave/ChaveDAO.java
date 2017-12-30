package movimentacao.projetoNCE.chave;

import java.util.List;

public interface ChaveDAO
{
	public void salvar(Chave chave);
	public void excluir(Chave chave);
	public Chave carregar(Integer id);
	public List<Chave> listar();

}
