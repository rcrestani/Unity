package movimentacao.produto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyProdutoDataModel extends LazyDataModel<Produto> implements Serializable
{

	private static final long serialVersionUID = -7852267401915463153L;
	
	private ProdutoRN produtoRN;
	private ProdutoFiltro filtro;
	
	public LazyProdutoDataModel(ProdutoRN produtoRN, ProdutoFiltro filtro)
	{
		this.produtoRN = produtoRN;
		this.filtro = filtro;
	}
	
	@Override
    public List<Produto> load(int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters) 
	{		

		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(produtoRN.quantidadeFiltrados(filtro));
		
		return produtoRN.buscarTodosPaginado(filtro);
	}

}
