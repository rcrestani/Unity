package movimentacao.negocios.contatos;

import java.util.List;

import movimentacao.negocios.Negocios;

public interface ContatosDAO 
{
	public void salvar(Contatos contatos);
	public void excluir(Contatos contatos);
	public List<Contatos> listar();
	public List<Contatos> contatosPorNegocios(Negocios negocios);

}
