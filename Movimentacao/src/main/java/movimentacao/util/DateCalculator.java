package movimentacao.util;

import java.util.Calendar;
import java.util.Date;

public class DateCalculator
{
	public String calculaHoras(Date saida , Date chegada) 
	{
		//SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String tempoMissao = "";
		Date dataInicialSD = saida;
		Date dataFinalSD = chegada;
		
		Calendar dataInicial = Calendar.getInstance();
	    dataInicial.setTime(dataInicialSD);
	    
	    Calendar dataFinal = Calendar.getInstance();
	    dataFinal.setTime(dataFinalSD);
	    
	    long diferenca = dataFinal.getTimeInMillis() - dataInicial.getTimeInMillis();
	    long diferencaSeg = diferenca / 1000;    //DIFERENCA EM SEGUNDOS   
	    long diferencaMin = diferenca / (60 * 1000);    //DIFERENCA EM MINUTOS   
	    long diferencaHoras = diferenca / (60 * 60 * 1000);    // DIFERENCA EM HORAS
	    
	    tempoMissao =  String.format("%d", diferencaHoras) + ":"
				+ String.format("%02d", diferencaMin % 60) + ":"
				+ String.format("%02d", diferencaSeg % 60);
	    
	   /* long dias = Long.parseLong(String.format("%d", diferencaHoras / 24));
	    long restoHoras = Long.parseLong(String.format("%d", diferencaHoras % 24));
	    long restoMinutos = Long.parseLong(String.format("%d", diferencaMin % 60));
	    long restoSegundos = Long.parseLong(String.format("%d", diferencaSeg % 60));*/
	     
	    return tempoMissao;
	}
	
	//Método utilizado no controle de frota da AES=============================
	public Date data14HorasAtras(Date data)
	{
		Calendar dataFinal = Calendar.getInstance();
		dataFinal.setTime(data);
		
		long resultado = dataFinal.getTimeInMillis() - 50400000;
		dataFinal.setTimeInMillis(resultado);
		
		return (Date) dataFinal.getTime();
		
	}
	
	//Método utilizado no controle de chave do projeto NCE========================
	public Date data15MinAtras(Date data)
	{
		Calendar dataFinal = Calendar.getInstance();
		dataFinal.setTime(data);
		
		long resultado = dataFinal.getTimeInMillis() - 900000;
		dataFinal.setTimeInMillis(resultado);
		
		return (Date) dataFinal.getTime();
		
	}
	
	//Método utilizado no controle de chave do projeto NCE========================
	public Date data48HorasAtras(Date data)
	{
		Calendar dataFinal = Calendar.getInstance();
		dataFinal.setTime(data);
		
		long resultado = dataFinal.getTimeInMillis() - 172800000;
		dataFinal.setTimeInMillis(resultado);
		
		return (Date) dataFinal.getTime();
		
	}

}
