package movimentacao.projetoNCE.emails;

import java.util.List;

public interface NivelEmailDAO
{
	public void salvar(NivelEmail nivelEmail);
	public void excluir(NivelEmail nivelEmail);
	public List<NivelEmail> listar();
}
