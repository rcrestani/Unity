package movimentacao.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RedimensionarImagem {

	public void redimensionar(BufferedImage image, String name) throws IOException
	{
		 	BufferedImage imagem = image;
		 	
	        int largura = 431, altura = 242;
	        
	        BufferedImage newImage = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
	        
	        Graphics2D g = newImage.createGraphics();
	        g.drawImage(imagem, 0, 0, largura, altura, null);
	        
	        ImageIO.write(newImage, "JPG", new File("/opt/unityImages/cartoesVisita/" , name));

	}

}
