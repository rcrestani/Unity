package movimentacao.projetoNCE.coordenador;

import java.util.List;

public interface CoordenadorDAO 
{
	public void salvar(Coordenador coordenador);
	public void excluir(Coordenador coordenador);
	public Coordenador carregar(Integer id);
	public List<Coordenador> listar();

}
