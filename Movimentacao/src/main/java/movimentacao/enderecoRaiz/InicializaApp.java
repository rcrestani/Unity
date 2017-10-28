package movimentacao.enderecoRaiz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InicializaApp implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Constantes.CAMINHO_SERVIDOR = sce.getServletContext().getRealPath("/");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
