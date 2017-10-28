package movimentacao.cliente;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyClienteDataModel extends LazyDataModel<Cliente> implements Serializable
{
	private static final long serialVersionUID = 3954791656674310542L;
	
	private ClienteRN clienteRN;
	private ClienteFiltro filtro;
	
	public LazyClienteDataModel(ClienteRN clienteRN, ClienteFiltro filtro)
	{
		this.clienteRN = clienteRN;
		this.filtro = filtro;
	}
	
	@Override
    public List<Cliente> load(int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters) 
	{		

		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(clienteRN.quantidadeFiltrados(filtro));
		
		return clienteRN.buscarTodosPaginado(filtro);
	}

}
