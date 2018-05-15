package movimentacao.projetoNCE.chave;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyChaveDataModel extends LazyDataModel<Chave> implements Serializable
{
	private static final long serialVersionUID = -6934165427920754753L;
	
	private ChaveRN chaveRN = new ChaveRN();
	private ChaveFiltro filtro = new ChaveFiltro();
	
	public LazyChaveDataModel (ChaveRN chaveRN , ChaveFiltro filtro)
	{
		this.chaveRN = chaveRN;
		this.filtro = filtro;
	}
	
	@Override
    public List<Chave> load (int first, int pageSize,
            String sortField, SortOrder sortOrder, Map<String, Object> filters)
    {
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(this.chaveRN.quantidadeFiltrados(this.filtro));
		
		return this.chaveRN.buscarTodosPaginado(this.filtro);
    }
}
