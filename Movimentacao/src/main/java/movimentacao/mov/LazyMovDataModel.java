package movimentacao.mov;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyMovDataModel extends LazyDataModel<Mov> implements Serializable
{
	private static final long serialVersionUID = -1817544535385708342L;
	
	private MovRN movRN;
	private MovFiltro filtro;
	
	public LazyMovDataModel(MovRN movRN, MovFiltro filtro)
	{
		this.movRN = movRN;
		this.filtro = filtro;
	}
	
	@Override
	public List<Mov> load(int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters)
	{
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
				
		setRowCount(movRN.quantidadeFiltrados(filtro));
		
		return movRN.buscarTodosPaginado(filtro);
	}

}
