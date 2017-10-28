package movimentacao.negocios;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyNegociosDataModel extends LazyDataModel<Negocios> implements Serializable
{
	private static final long serialVersionUID = -4074347552519242772L;
	
	private NegociosRN negociosRN;
	private NegociosFiltro filtro;
	
	public LazyNegociosDataModel(NegociosRN negociosRN , NegociosFiltro filtro)
	{
		this.negociosRN = negociosRN;
		this.filtro = filtro;
	}
	
	
	@Override
	public List<Negocios> load(int first, int pageSize,
            String sortField, SortOrder sortOrder, Map<String, Object> filters)
	{
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
	
		setRowCount(negociosRN.quantidadeFiltrados(filtro));

		return negociosRN.buscarTodosPaginado(filtro);
	}

}
