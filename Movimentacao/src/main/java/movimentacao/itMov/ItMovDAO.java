package movimentacao.itMov;

import java.util.List;

import movimentacao.mov.Mov;

public interface ItMovDAO 
{
	public void salvar(ItMov itMov);
	public void excluir(ItMov itMov);
	public ItMov carregar(Integer id);
	public List<ItMov> listar();
	public List<ItMov> itensMov(Mov mov);
	public List<ItMov> tipoMov(String tipoMovi);
	public List<ItMov> itMovFechamento(Mov mov , String tipoMovi);

}
