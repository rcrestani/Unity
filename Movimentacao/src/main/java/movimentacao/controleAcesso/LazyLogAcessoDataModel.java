package movimentacao.controleAcesso;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyLogAcessoDataModel extends LazyDataModel <LogAcesso> implements Serializable
{
	private static final long serialVersionUID = -1567139139410648391L;
	
	private LogAcessoRN logAcessoRN = new LogAcessoRN();
	private LogAcessoFiltro filtro = new LogAcessoFiltro();
	
	public LazyLogAcessoDataModel (LogAcessoRN logAcessoRN , LogAcessoFiltro filtro)
	{
		this.logAcessoRN = logAcessoRN;
		this.filtro = filtro;
	}
	
	@Override
	public List<LogAcesso> load (int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters)
	{
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(logAcessoRN.qtdeFiltrados(filtro));
		
		return logAcessoRN.buscarTodosPaginado(filtro);
	}

}
