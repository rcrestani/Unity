package movimentacao.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;

import movimentacao.cartaoVisita.CartaoVisita;
import movimentacao.cartaoVisita.CartaoVisitaFiltro;
import movimentacao.cartaoVisita.CartaoVisitaRN;
import movimentacao.enderecoRaiz.Constantes;
import movimentacao.util.RedimensionarImagem;

@ManagedBean (name = "cartaoVisitaBean")
@ViewScoped
public class CartaoVisitaBean implements Serializable
{
	private static final long serialVersionUID = 9155036104084081993L;
	
	private CartaoVisita cartaoVisita = new CartaoVisita();
	private CartaoVisitaRN cartaoVisitaRN = new CartaoVisitaRN();
	private CartaoVisitaFiltro filtro = new CartaoVisitaFiltro();
	private List<CartaoVisita> listaCartaoVisita = new ArrayList<CartaoVisita>();
	
	private int qtde;
	private boolean ativaUpload = false;
	
	@PostConstruct
	public void init()
	{
		this.listaCartaoVisita = cartaoVisitaRN.listar(this.filtro);
		this.qtde = this.listaCartaoVisita.size();
	}
	
	public String salvar()
	{
		if(StringUtils.isEmpty(this.cartaoVisita.getArquivoImagemFrente()))
		{
			this.cartaoVisita.setArquivoImagemFrente("provisorio");
			this.cartaoVisita.setArquivoImagemVerso("provisorio");
		}
		
		this.cartaoVisitaRN.salvar(this.cartaoVisita);
		//this.ativaUpload = true;
		
		return null;
	}
	
	public String novo()
	{
		this.cartaoVisita = new CartaoVisita();
		//this.ativaUpload = false;
		
		return null;
	}
	
	public void fileUploadFrente(FileUploadEvent event) throws NoSuchAlgorithmException 
	{
		//Variáveis auxiliares=============================
		List<String> lista = new ArrayList<String>();
		String fileNameMD5 = "";
		
		//Pegando o nome do arquivo e dividindo em partes pelo caracter ponto, que separa a extensao do arquivo============
		String [] texto = event.getFile().getFileName().split("\\.");
		
		//Convertendo o Array em ArrayList================
        lista = Arrays.asList(texto);
        
        //Variável auxiliar
        int indice = 0;
        //Verificando se a lista está populada======================
        if(lista.size() > 0)
        {
        	//Atribuindo o indice do último registro da ArrayList, pois preciso apenas da extensao do arquivo=============
        	indice = lista.size() -1;
        	
        	//Instanciando a biblioteca MD5===========================
        	MessageDigest md5 = MessageDigest.getInstance("MD5");
            
        	//Montando o texto a ser convertido em MD5, primeira parte do arquivo + data e hora sem espaço e traços===========
        	String textToMD5 = lista.get(0) + formatDate(new Date());
        	
        	//Fazendo a conversão para MD5======================================================
        	md5.update(textToMD5.getBytes(),0,textToMD5.length());
        	//Atribuindo o MD5 convertido em String com a base 32================================
        	fileNameMD5 = new BigInteger(1,md5.digest()).toString(32) + "." + lista.get(indice);
        }        	
       
		try 
		{
			//Verificando se há imagem já salva em disco deste objeto, caso tiver, deletar o arquivo e gravar o novo======
			if(StringUtils.isEmpty(this.cartaoVisita.getArquivoImagemFrente()))
			{
				//Passando o stream do evento do arquivo para o tipo BufferedImage============
				BufferedImage imagem = ImageIO.read(event.getFile().getInputstream());
				//Classe que contem o método de redimensionar a imagem e gravar na pasta dos cartões de visita===========
				RedimensionarImagem redImagem = new RedimensionarImagem();
				redImagem.redimensionar(imagem, fileNameMD5);
			    
			    //Atualizando o objeto com o arquivo salvo=======================
			    this.cartaoVisita.setArquivoImagemFrente(fileNameMD5);
			    
			    FacesContext.getCurrentInstance().addMessage(
			               null, new FacesMessage("Upload completo! A Imagem " + event.getFile().getFileName() + " foi salva!"));
			}
			else
			{
				File file = new File(Constantes.CAMINHO_SERVIDOR + "/resources/cartoesVisita/" + this.cartaoVisita.getArquivoImagemFrente());
				file.delete();

				//Passando o stream do evento do arquivo para o tipo BufferedImage============
				BufferedImage imagem = ImageIO.read(event.getFile().getInputstream());
				//Classe que contem o método de redimensionar a imagem e gravar na pasta dos cartões de visita===========
				RedimensionarImagem redImagem = new RedimensionarImagem();
				redImagem.redimensionar(imagem, fileNameMD5);
			    
			    //Atualizando o objeto com o arquivo salvo=======================
			    this.cartaoVisita.setArquivoImagemFrente(fileNameMD5);
			    this.cartaoVisitaRN.salvar(this.cartaoVisita);
			    
			    FacesContext.getCurrentInstance().addMessage(
			               null, new FacesMessage("Upload completo! A Imagem " + event.getFile().getFileName() + " foi atualizada!"));
			}
			
			
		} 
		catch(IOException e) 
		{
		    FacesContext.getCurrentInstance().addMessage(
		              null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
		}
		
    }
	
	public void fileUploadVerso(FileUploadEvent event) throws NoSuchAlgorithmException 
	{
		//Variáveis auxiliares=============================
				List<String> lista = new ArrayList<String>();
				String fileNameMD5 = "";
				
				//Pegando o nome do arquivo e dividindo em partes pelo caracter ponto, que separa a extensao do arquivo============
				String [] texto = event.getFile().getFileName().split("\\.");
				
				//Convertendo o Array em ArrayList================
		        lista = Arrays.asList(texto);
		        
		        //Variável auxiliar
		        int indice = 0;
		        //Verificando se a lista está populada======================
		        if(lista.size() > 0)
		        {
		        	//Atribuindo o indice do último registro da ArrayList, pois preciso apenas da extensao do arquivo=============
		        	indice = lista.size() -1;
		        	
		        	//Instanciando a biblioteca MD5===========================
		        	MessageDigest md5 = MessageDigest.getInstance("MD5");
		            
		        	//Montando o texto a ser convertido em MD5, primeira parte do arquivo + data e hora sem espaço e traços===========
		        	String textToMD5 = lista.get(0) + formatDate(new Date());
		        	
		        	//Fazendo a conversão para MD5======================================================
		        	md5.update(textToMD5.getBytes(),0,textToMD5.length());
		        	//Atribuindo o MD5 convertido em String com a base 32================================
		        	fileNameMD5 = new BigInteger(1,md5.digest()).toString(32) + "." + lista.get(indice);
		        }        	
		       
				try 
				{					
					//Verificando se há imagem já salva em disco deste objeto, caso tiver, deletar o arquivo e gravar o novo======
					if(StringUtils.isEmpty(this.cartaoVisita.getArquivoImagemVerso()))
					{
						//Passando o stream do evento do arquivo para o tipo BufferedImage============
						BufferedImage imagem = ImageIO.read(event.getFile().getInputstream());
						//Classe que contem o método de redimensionar a imagem e gravar na pasta dos cartões de visita===========
						RedimensionarImagem redImagem = new RedimensionarImagem();
						redImagem.redimensionar(imagem, fileNameMD5);
					    
					    //Atualizando o objeto com o arquivo salvo=======================
					    this.cartaoVisita.setArquivoImagemVerso(fileNameMD5);
					    
					    FacesContext.getCurrentInstance().addMessage(
					               null, new FacesMessage("Upload completo! A Imagem " + event.getFile().getFileName() + " foi salva!"));
					}
					else
					{
						File file = new File(Constantes.CAMINHO_SERVIDOR + "/resources/cartoesVisita/" + this.cartaoVisita.getArquivoImagemVerso());
						file.delete();

						//Passando o stream do evento do arquivo para o tipo BufferedImage============
						BufferedImage imagem = ImageIO.read(event.getFile().getInputstream());
						//Classe que contem o método de redimensionar a imagem e gravar na pasta dos cartões de visita===========
						RedimensionarImagem redImagem = new RedimensionarImagem();
						redImagem.redimensionar(imagem, fileNameMD5);
					    
					    //Atualizando o objeto com o arquivo salvo=======================
					    this.cartaoVisita.setArquivoImagemVerso(fileNameMD5);
					    this.cartaoVisitaRN.salvar(this.cartaoVisita);
					    
					    FacesContext.getCurrentInstance().addMessage(
					               null, new FacesMessage("Upload completo! A Imagem " + event.getFile().getFileName() + " foi atualizada!"));
					}
				} 
				catch(IOException e) 
				{
				    FacesContext.getCurrentInstance().addMessage(
				              null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
				}
    }
	
	private String formatDate(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmm");
		
		return dateFormat.format(data);
	}
	
	//Getters and setters=================================================================================
	public CartaoVisita getCartaoVisita() {
		return cartaoVisita;
	}

	public void setCartaoVisita(CartaoVisita cartaoVisita) {
		this.cartaoVisita = cartaoVisita;
	}

	public CartaoVisitaFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(CartaoVisitaFiltro filtro) {
		this.filtro = filtro;
	}

	public List<CartaoVisita> getListaCartaoVisita() {
		return listaCartaoVisita;
	}

	public void setListaCartaoVisita(List<CartaoVisita> listaCartaoVisita) {
		this.listaCartaoVisita = listaCartaoVisita;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public boolean isAtivaUpload() {
		return ativaUpload;
	}

	public void setAtivaUpload(boolean ativaUpload) {
		this.ativaUpload = ativaUpload;
	}
	
}
