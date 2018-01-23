package movimentacao.projetoAES;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyControleFrotaDataModel extends LazyDataModel <ControleFrota> implements Serializable
{
	private static final long serialVersionUID = 7882594678979334183L;
	
	private ControleFrotaRN controleFrotaRN;
	private ControleFrotaFiltro filtro;
	
	public LazyControleFrotaDataModel(ControleFrotaRN controleFrotaRN , ControleFrotaFiltro filtro)
	{
		this.controleFrotaRN = controleFrotaRN;
		this.filtro = filtro;
	}
	
	@Override
	public List<ControleFrota> load(int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters)
	{
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(controleFrotaRN.qtdeFiltrados(filtro));
		
		return controleFrotaRN.buscarTodosPaginado(filtro);
	}

}
