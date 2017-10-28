package movimentacao.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class JavaTask 
{
	private int hora;
	private int minuto;
	private int segundo;
	private int semana;
	
	public void tarefaSemanal() 
	{
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hora);
        c.set(Calendar.MINUTE, minuto);
        c.set(Calendar.SECOND, segundo);
        c.set(Calendar.DAY_OF_WEEK, semana);

        Date time = c.getTime();

        final Timer t = new Timer();
        
        t.schedule(new TimerTask() 
        {
            @Override
            public void run() 
            {
                
                t.cancel();
            }

        }, time );
    }

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	public int getSegundo() {
		return segundo;
	}

	public void setSegundo(int segundo) {
		this.segundo = segundo;
	}

	public int getSemana() {
		return semana;
	}

	public void setSemana(int semana) {
		this.semana = semana;
	}
	
	
	
}
