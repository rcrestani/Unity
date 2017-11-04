package movimentacao.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean (name = "imageBean")
@RequestScoped
public class ImageController implements Serializable
{
	private static final long serialVersionUID = 8796475325020781619L;
	
	
	@SuppressWarnings("unused")
	private StreamedContent foto;
	
	public StreamedContent getFoto() throws FileNotFoundException
	{
		String fotoNome = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fotoNome");
		
		String folder = "/opt/unityImages/cartoesVisita" + File.separator;
		
		System.out.println("ARQUIVO: " + folder + fotoNome);
		
		DefaultStreamedContent content = null;
		
	    try
	    {
	    	File imagem = new File(folder , fotoNome);
	    	
	    	BufferedInputStream in = new BufferedInputStream(new FileInputStream(imagem));
	        byte[] bytes = new byte[in.available()];
	        in.read(bytes);
	        in.close();
	        content = new DefaultStreamedContent(new ByteArrayInputStream(bytes),"image/jpeg");
	    }
	    catch(Exception e)
	    {
	        System.out.println(e.getMessage());
	        return new DefaultStreamedContent(new FileInputStream(new File(folder + "cartaoDefault.jpg")), "image/jpeg");
	    }
	    
	    return content;
    }

}
