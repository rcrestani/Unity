package movimentacao.util;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import movimentacao.projetoAES.ControleFrota;
import movimentacao.projetoAES.ListaControleFrota;

public class JAXBUtil
{
	private static JAXBUtil instance;
	private static JAXBContext context;
	
	public static JAXBUtil getInstance()
	{
		return instance;
	}
	
	static
	{
		try
		{
			//Informa ao JAXB que é para gerar xml destas classes.
			context = JAXBContext.newInstance(ListaControleFrota.class , ControleFrota.class);
		}
		catch (JAXBException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static String toXML(Object object) throws IOException
	{
		try
		{
			StringWriter writer = new StringWriter();
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(object, writer);
			String xml = writer.toString();
			return xml;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/*public static String toJSON(Object object) throws IOException
	{
		try
		{
			StringWriter writer = new StringWriter();
			Marshaller m = context.createMarshaller();
			MappedNamespaceConvention con = new MappedNamespaceConvention();
			XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);
			m.marshal(object, xmlStreamWriter);
			String json = writer.toString();
			return json;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			return null;
		}
	}*/

}
