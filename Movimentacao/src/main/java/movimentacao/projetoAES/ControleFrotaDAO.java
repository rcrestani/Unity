package movimentacao.projetoAES;

import java.util.Date;
import java.util.List;

public interface ControleFrotaDAO 
{
	public void salvar(ControleFrota controleFrota);
	public ControleFrota consultaRegistroSaida(String vtr, Date data);
	public ControleFrota ultimoRegistro();
	public List<ControleFrota> listar();
	public List<ControleFrota> consultaLista(ControleFrotaFiltro filtro);
	public List<String> completeColetor(String text);
	public List<String> completeVtr(String text);
	public List<String> completeCondutor(String text);
	public List<ControleFrota> buscarTodosPaginado(ControleFrotaFiltro filtro);
	public int qtdeFiltrados(ControleFrotaFiltro filtro);
}
