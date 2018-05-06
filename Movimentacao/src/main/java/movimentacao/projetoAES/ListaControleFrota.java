package movimentacao.projetoAES;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="listaControleFrota")
public class ListaControleFrota implements Serializable
{
	private static final long serialVersionUID = 3077918727175790509L;
	
	private List<ControleFrota> controleFrota;
	
	@XmlElement(name="ControleFrotaObject")
	public List<ControleFrota> getControleFrota()
	{
		return controleFrota;
	}
	
	public void setControleFrota(List<ControleFrota> controleFrota)
	{
		this.controleFrota = controleFrota;
	}
	
	@Override
	public String toString()
	{
		return "ListaControleFrota [ControleFrota=" + controleFrota + "]";
	}
	
}
