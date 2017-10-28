package movimentacao.util;
import java.io.*;

import javax.faces.context.*;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import movimentacao.util.UtilException;

public class RelatorioUtilStream 
{
	public static final int	RELATORIO_PDF		= 1;
	public static final int	RELATORIO_EXCEL		= 2;
	public static final int	RELATORIO_HTML		= 3;
	public static final int	RELATORIO_PLANILHA_OPEN_OFFICE	= 4;
	
	public InputStream geraRelatorio(String nomeRelatorioJasper,
		String nomeRelatorioSaida, int tipoRelatorio, JRBeanCollectionDataSource cBean) throws UtilException, IOException 
	{ 
		//StreamedContent arquivoRetorno = null;
		InputStream conteudoRelatorio = null;
		try {
			FacesContext contextoFaces = FacesContext.getCurrentInstance();
			ExternalContext contextoExterno = contextoFaces.getExternalContext();
			ServletContext contextoServlet = (ServletContext) contextoExterno.getContext();

			String caminhoRelatorios = contextoServlet.getRealPath("/relatorios");
			
			/*System.out.println("Caminho: " + Constantes.CAMINHO_SERVIDOR
					+ "\n" + caminhoRelatorios);*/
			
			String caminhoArquivoJasper = caminhoRelatorios + File.separator + nomeRelatorioJasper + ".jasper"; 
			String caminhoArquivoRelatorio = caminhoRelatorios + File.separator + nomeRelatorioSaida;

			JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivoJasper);
			//JRBeanCollectionDataSource classeBean = new JRBeanCollectionDataSource((Collection<?>) cBean);

			JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, null , cBean);
				//(relatorioJasper, parametrosRelatorio);

			String extensao = null;
			File arquivoGerado = null;

			switch (tipoRelatorio) {
				case RelatorioUtilStream.RELATORIO_PDF:
					JRPdfExporter pdfExportado = new JRPdfExporter(); 
					extensao = "pdf";
					arquivoGerado = new java.io.File(caminhoArquivoRelatorio + "." + extensao);
					pdfExportado.setExporterInput(new SimpleExporterInput(	impressoraJasper));
					pdfExportado.setExporterOutput(new SimpleOutputStreamExporterOutput(arquivoGerado));
					pdfExportado.exportReport();
					arquivoGerado.deleteOnExit();
					break;
				case RelatorioUtilStream.RELATORIO_HTML:
					HtmlExporter htmlExportado = new HtmlExporter();
					extensao = "html";
					arquivoGerado = new java.io.File(caminhoArquivoRelatorio + "." + extensao);
					htmlExportado.setExporterInput(new SimpleExporterInput(impressoraJasper));
					htmlExportado.setExporterOutput(new SimpleHtmlExporterOutput(arquivoGerado));
					htmlExportado.exportReport();
					arquivoGerado.deleteOnExit();
				break;
				case RelatorioUtilStream.RELATORIO_EXCEL:
					JRXlsExporter xlsExportado = new JRXlsExporter();
					extensao = "xls";
					arquivoGerado = new java.io.File(caminhoArquivoRelatorio + "." + extensao);
					xlsExportado.setExporterInput(new SimpleExporterInput(	impressoraJasper));
					xlsExportado.setExporterOutput(new SimpleOutputStreamExporterOutput(arquivoGerado));
					xlsExportado.exportReport();
					arquivoGerado.deleteOnExit();
					break;
				case RelatorioUtilStream.RELATORIO_PLANILHA_OPEN_OFFICE:
					JROdtExporter openExportado = new JROdtExporter();
					extensao = "ods";
					arquivoGerado = new java.io.File(caminhoArquivoRelatorio + "." + extensao);
					openExportado.setExporterInput(new SimpleExporterInput(impressoraJasper));
					openExportado.setExporterOutput(new SimpleOutputStreamExporterOutput(arquivoGerado));
					openExportado.exportReport();
					arquivoGerado.deleteOnExit();
					break;
			}			

			conteudoRelatorio = new FileInputStream(arquivoGerado);
			/*arquivoRetorno = new DefaultStreamedContent(conteudoRelatorio, "application/" 
				+ extensao, nomeRelatorioSaida + "." + extensao); */
		} catch (JRException e) {
			throw new UtilException("Não foi possível gerar o relatório." + e, e);
		} catch (FileNotFoundException e) {
			throw new UtilException("Arquivo do relatório não encontrado." + e, e);
		}
		return conteudoRelatorio;
	}
}

