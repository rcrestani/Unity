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
	
	public LazyUsuarioDataModel(UsuarioRN usuarioRN)
	{
		this.usuarioRN = usuarioRN;
	}
	
	@Override
    public List<Usuario> load(int first, int pageSize,
                String sortField, SortOrder sortOrder, Map<String, Object> filters) 
	{
		List<Usuario> usuario = usuarioRN.buscarTodosPaginado(first, pageSize, sortField, sortOrder);
		
		this.setRowCount(usuarioRN.pegarQuantidadeDeUsuarios());

		return usuario;
    }

	
	
	
	
}
