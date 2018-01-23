package movimentacao.projetoNCE.tecnico;

import java.util.List;

public interface TecnicoDAO
{
	public void salvar(Tecnico tecnico);
	public void excluir(Tecnico tecnico);
	public Tecnico carregar (Integer id);
	public List<Tecnico> listar();
	public List<String> completeTecnico(String text);
	public Tecnico tecnicoPorNome(String nome);
}
