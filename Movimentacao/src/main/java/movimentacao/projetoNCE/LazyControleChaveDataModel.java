package movimentacao.projetoNCE;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyControleChaveDataModel extends LazyDataModel <ControleChave> implements Serializable
{
	private static final long serialVersionUID = 470984490658647696L;
	
	private ControleChaveRN controleChaveRN;
	private ControleChaveFiltro filtro;
	
	public LazyControleChaveDataModel (ControleChaveRN controleChaveRN , ControleChaveFiltro filtro)
	{
		this.controleChaveRN = controleChaveRN;
		this.filtro = filtro;
	}
	
	@Override
	public List<ControleChave> load (int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters)
	{
		
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(controleChaveRN.qtdeFiltrados(filtro));
		
		return controleChaveRN.buscarTodosPaginado(filtro);
	}
}
