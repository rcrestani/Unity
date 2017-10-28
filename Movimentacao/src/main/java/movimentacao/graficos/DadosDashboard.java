package movimentacao.graficos;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import movimentacao.cliente.ClienteRN;
import movimentacao.controleAcesso.LogAcessoRN;
import movimentacao.mov.MovRN;
import movimentacao.negocios.NegociosRN;
import movimentacao.negocios.atividades.AtividadesFiltro;
import movimentacao.negocios.atividades.AtividadesRN;

@ManagedBean (name = "dadosDash")
@RequestScoped
public class DadosDashboard 
{
	private int negocios;
	private int atividades;
	private int clientes;
	private int movimentacoes;
	private int acessos;
	
	public DadosDashboard()
	{
		NegociosRN negociosRN = new NegociosRN();
		this.negocios = negociosRN.listar().size();
		
		AtividadesRN atividadesRN = new AtividadesRN();
		AtividadesFiltro filtro = new AtividadesFiltro();
		this.atividades = atividadesRN.listar(filtro).size();
		
		ClienteRN clienteRN = new ClienteRN();
		this.clientes = clienteRN.listar().size();
		
		MovRN movRN = new MovRN();
		this.movimentacoes = movRN.listar().size();
		
		LogAcessoRN logAcessoRN = new LogAcessoRN();
		this.acessos = logAcessoRN.listar().size();
	}

	public int getNegocios() {
		return negocios;
	}

	public int getAtividades() {
		return atividades;
	}

	public int getClientes() {
		return clientes;
	}

	public int getMovimentacoes() {
		return movimentacoes;
	}

	public int getAcessos() {
		return acessos;
	}
	
}
