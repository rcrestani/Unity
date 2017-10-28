package movimentacao.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import movimentacao.cliente.Cliente;
import movimentacao.cliente.ClienteRN;
import movimentacao.itMov.ItMov;
import movimentacao.itMov.ItMovRN;
import movimentacao.mov.LazyMovDataModel;
import movimentacao.mov.Mov;
import movimentacao.mov.MovFiltro;
import movimentacao.mov.MovRN;
import movimentacao.produto.Produto;
import movimentacao.produto.ProdutoRN;
import movimentacao.usuario.UsuarioRN;

@ManagedBean(name = "movBean")
@ViewScoped
public class MovBean implements Serializable
{
	private static final long serialVersionUID = -3908508230740590846L;
	
	private Mov mov = new Mov();
	private MovRN movRN = new MovRN();
	private ItMov itMov = new ItMov();
	private List<ItMov> listItMov;
	private ItMovRN itMovRN = new ItMovRN();
	private Cliente cliente = new Cliente();
	private UsuarioRN usuarioRN = new UsuarioRN();
	private Produto pdt = new Produto();
	private ProdutoRN produtoRN = new ProdutoRN();
	
	private String nomeProd;
	private int codProd;
	private float totais;
	
	private LazyMovDataModel lazyMov;
	private MovFiltro filtro = new MovFiltro();
	
	private FacesContext context = FacesContext.getCurrentInstance();
	private ExternalContext external = context.getExternalContext();
	private String login = external.getRemoteUser();
		
	public LazyMovDataModel getLazyMov() {
		return lazyMov;
	}
	
	@PostConstruct
	public void init()
	{
		lazyMov = new LazyMovDataModel(movRN, filtro);
		mov.setUsuario(usuarioRN.buscarPorLogin(login));
	}

	public String salvar()
	{
		try
		{
			UsuarioRN usuarioRN = new UsuarioRN();
			mov.setUsuario(usuarioRN.buscarPorLogin(login));
			
			ClienteRN clienteRN = new ClienteRN();
			//cliente = clienteRN.buscarPorNome(cliente.getNome());
			mov.setCliente(clienteRN.buscarPorNome(cliente.getNome()));
			
			movRN.salvar(mov);
			
			/*FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_INFO , "Movimentação salva com sucesso!" , ""));*/
			
			return null;
		}
		catch(Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Movimentação não foi salva no Banco de Dados. Motivo: " + e.getMessage(), ""));
			
			return null;			
		}
	}
	
	
	 public void onItemSelect(SelectEvent event) 
	 {
		 ProdutoRN pdtRN = new ProdutoRN();
		 pdt = pdtRN.buscarPorDescricao(nomeProd);
		 codProd = pdt.getCodigo();
	 }
	
	public String adicionar()
	{
		ItMov itensMov = new ItMov();
		itensMov.setCodProd(pdt);
	    itensMov.setId_mov(mov);
	    itensMov.setNumeracao(itMov.getNumeracao());
	    itensMov.setQtde(itMov.getQtde());
	    itensMov.setTipoMovi(itMov.getTipoMovi());
	    itensMov.setvUnit(itMov.getvUnit());
	    
	    int qtde = pdt.getQtde();
	    int baixaEstoque = 0;
	    boolean estoque = true;
	    
	    if(itensMov.getTipoMovi().equals("saida") && qtde >= itensMov.getQtde())
	    {
	    	baixaEstoque = qtde - itensMov.getQtde();
	    	pdt.setQtde(baixaEstoque);
	    	produtoRN.salvar(pdt);
	    }
	    else if(itensMov.getTipoMovi().equals("devolucao") || itensMov.getTipoMovi().equals("entrada"))
	    {
	    	baixaEstoque = qtde + itensMov.getQtde();
	    	pdt.setQtde(baixaEstoque);
	    	produtoRN.salvar(pdt);
	    }
	    else if(itensMov.getTipoMovi().equals("manutencao") && qtde >= itensMov.getQtde())
	    {
	    	baixaEstoque = qtde - itensMov.getQtde();
	    	pdt.setQtde(baixaEstoque);
	    	produtoRN.salvar(pdt);
	    }
	    else if(qtde < itensMov.getQtde() && itensMov.getTipoMovi() != "pendente")
	    {
	    	estoque = false;
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Estoque insulficiente!" , ""));
	    }
	    
	    if(estoque == true)
	    {
		    itMovRN.salvar(itensMov);
		    //Recarregando a lista dos itens=========================================
			    this.listItMov = itMovRN.itensMov(mov);
			    totais = 0;
			    for (int i = 0; i < listItMov.size() ; i++)
			    {
			    	ItMov itm = listItMov.get(i);
			    	totais += itm.getQtde() * itm.getvUnit();
			    }
		    //========================================================================
		    
		    //Limpando formulario de inserção dos itens===============================
			    codProd = 0;
			    nomeProd = "";
			    itMov.setCodProd(null);
			    itMov.setNumeracao(null);
			    itMov.setQtde(null);
			    itMov.setTipoMovi(null);
			    itMov.setvUnit(0);
		    //========================================================================
	    }
		
		return null;
	}
	
	public String excluirItem()
	{
		pdt = produtoRN.carregar(this.itMov.getCodProd().getCodigo());
		int qtde = pdt.getQtde();
		int baixaEstoque = 0;
	    boolean estoque = true;
		
		
		if(this.itMov.getTipoMovi().equals("saida") || this.itMov.getTipoMovi().equals("manutencao"))
	    {
	    	baixaEstoque = qtde + this.itMov.getQtde();
	    	pdt.setQtde(baixaEstoque);
	    	produtoRN.salvar(pdt);
	    }
	    else if(this.itMov.getTipoMovi().equals("devolucao") && qtde >= this.itMov.getQtde() )
	    {
	    	baixaEstoque = qtde - this.itMov.getQtde();
	    	pdt.setQtde(baixaEstoque);
	    	produtoRN.salvar(pdt);
	    }
	    else if(this.itMov.getTipoMovi().equals("entrada") && qtde >= this.itMov.getQtde())
	    {
	    	baixaEstoque = qtde - this.itMov.getQtde();
	    	pdt.setQtde(baixaEstoque);
	    	produtoRN.salvar(pdt);
	    }
	    else if(qtde < this.itMov.getQtde() && this.itMov.getTipoMovi().contentEquals("pendente"))
	    {
	    	estoque = false;
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		    		FacesMessage.SEVERITY_ERROR, "Erro! " + "Estoque insulficiente!" , ""));
	    }
		
		if (estoque == true)
		{
			itMovRN.excluir(this.itMov);
			
			//Excluindo o item do ArrayList====================
			    for (int i = 0; i < listItMov.size() ; i++)
			    {
			    	ItMov itm = listItMov.get(i);
			    	if (itm == this.itMov)
			    	{
			    		listItMov.remove(i);
			    	}
			    }
			//=================================================
		    
		    //Atualizando o total geral========================
			    totais = 0;
			    for (int i = 0; i < listItMov.size() ; i++)
			    {
			    	ItMov itm = listItMov.get(i);
			    	totais += itm.getQtde() * itm.getvUnit();
			    }
		    //=================================================
		    
		    //Limpando formulario de inserção dos itens===============================
			    codProd = 0;
			    nomeProd = "";
			    itMov.setCodProd(null);
			    itMov.setNumeracao(null);
			    itMov.setQtde(null);
			    itMov.setTipoMovi(null);
			    itMov.setvUnit(0);
		    //========================================================================
		}
	    
		return null;
	}
	
	public String limparForm()
	{
		this.mov = new Mov();
		this.mov.setUsuario(usuarioRN.buscarPorLogin(login));
		this.cliente = new Cliente();

		if (this.listItMov != null)
		{
			this.listItMov.clear();
		}
		totais = 0;
		
		return null;
	}
	
	public String editar()
	{
		this.cliente.setNome(this.mov.getCliente().getNome());
		this.listItMov = itMovRN.itensMov(this.mov);
	    totais = 0;
	    for (int i = 0; i < listItMov.size() ; i++)
	    {
	    	ItMov itm = listItMov.get(i);
	    	totais += itm.getQtde() * itm.getvUnit();
	    }
	    
		return null;
	}
	
	public Mov getMov() {
		return mov;
	}

	public void setMov(Mov mov) {
		this.mov = mov;
	}

	public MovRN getMovRN() {
		return movRN;
	}

	public void setMovRN(MovRN movRN) {
		this.movRN = movRN;
	}

	public String getLogin() {
		return login;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ItMov getItMov() {
		return itMov;
	}

	public void setItMov(ItMov itMov) {
		this.itMov = itMov;
	}

	public String getNomeProd() {
		return nomeProd;
	}

	public void setNomeProd(String nomeProd) {
		this.nomeProd = nomeProd;
	}

	public ProdutoRN getProdutoRN() {
		return produtoRN;
	}

	public void setProdutoRN(ProdutoRN produtoRN) {
		this.produtoRN = produtoRN;
	}


	public int getCodProd() {
		return codProd;
	}


	public void setCodProd(int codProd) {
		this.codProd = codProd;
	}


	public List<ItMov> getListItMov() {
		return listItMov;
	}


	public Produto getPdt() {
		return pdt;
	}


	public void setPdt(Produto pdt) {
		this.pdt = pdt;
	}


	public ItMovRN getItMovRN() {
		return itMovRN;
	}


	public void setItMovRN(ItMovRN itMovRN) {
		this.itMovRN = itMovRN;
	}


	public float getTotais() {
		return totais;
	}

	public MovFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(MovFiltro filtro) {
		this.filtro = filtro;
	}

	
}
