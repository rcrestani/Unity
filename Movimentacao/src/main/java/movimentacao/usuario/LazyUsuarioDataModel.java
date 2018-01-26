package movimentacao.usuario;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyUsuarioDataModel extends LazyDataModel<Usuario> implements Serializable
{

	private static final long serialVersionUID = -2124799407851421301L;
	
	private UsuarioRN usuarioRN;
	private UsuarioFiltro filtro;
	
	public LazyUsuarioDataModel(UsuarioRN usuarioRN , UsuarioFiltro filtro)
	{
		this.usuarioRN = usuarioRN;
		this.filtro = filtro;
	}
	
	@Override
    public List<Usuario> load(int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters) 
	{
		filtro.setPrimeiroRegistro(first);
		filtro.setQuantidadeRegistros(pageSize);
		filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
		filtro.setPropriedadeOrdenacao(sortField);
		
		setRowCount(usuarioRN.quantidadeFiltrados(filtro));
		
		return usuarioRN.buscarTodosPaginado(filtro);

    }

	
	
	
	
}
