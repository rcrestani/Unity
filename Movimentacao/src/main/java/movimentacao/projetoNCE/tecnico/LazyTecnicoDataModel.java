package movimentacao.projetoNCE.tecnico;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyTecnicoDataModel extends LazyDataModel<Tecnico> implements Serializable
{
	private static final long serialVersionUID = -692930790418115936L;
	
	private TecnicoRN tecnicoRN;
	private TecnicoFiltro filtro;
	
	public LazyTecnicoDataModel(TecnicoRN tecnicoRN , TecnicoFiltro filtro)
	{
		this.tecnicoRN = tecnicoRN;
		this.filtro = filtro;
	}
	
	@Override
    public List<Tecnico> load(int first, int pageSize,
            String sortField, SortOrder sortOrder, Map<String, Object> filters) 
	{		
	
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(this.tecnicoRN.quantidadeFiltrados(filtro));
		
		return this.tecnicoRN.buscarTodosPaginado(filtro);
	}
	
}
