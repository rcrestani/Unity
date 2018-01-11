package movimentacao.projetoNCE.empresa;

import java.util.List;

public interface EmpresaDAO
{
	public void salvar(Empresa empresa);
	public void excluir(Empresa empresa);
	public Empresa carregar(Integer id);
	public List<Empresa> listar();
	public List<String> completeEmpresa(String text);
	public Empresa empresaPorNome(String nome);
}
