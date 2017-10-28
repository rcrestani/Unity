package movimentacao.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CentralTask implements ServletContextListener
{
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static int minutoAtual = 0;
	private static int auxDelay = 0;
	
	public static void executarTask()
	{		
		 //beep task
        final Runnable beeper = new Runnable() {
            public void run() 
            {
            	try 
            	{
					System.out.println("..:: CENTRAL TASK - UNITY::.." + "\n" + "DELAY = " + auxDelay);
					
				} 
            	catch (Exception e) 
            	{
					e.printStackTrace();
				}
            }
        };
        
        minutoAtual = Integer.parseInt(formatMin(new Date()));
        
        if(minutoAtual < 30)
        {
        	auxDelay = 30 - minutoAtual;
        }
        else if(minutoAtual >= 30)
        {
        	auxDelay = 60 - minutoAtual;
        }
        
        //beep each hour
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, auxDelay, 30, TimeUnit.MINUTES);

        //cancel beep task
        @SuppressWarnings("unused")
		final Runnable canceler = new Runnable() {
            public void run() {
                beeperHandle.cancel(false);
            }
        };

        //stop beep after 1 day
        //scheduler.schedule(canceler, 1, TimeUnit.DAYS);
    }
	
	private static String formatMin(Date data) 
	{
		DateFormat dateFormat = new SimpleDateFormat("m");
		
		return dateFormat.format(data);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) 
	{
		try
		{
			//executarTask();
		}
		catch (Exception e)
		{
			 System.err.println(e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) 
	{
		
	}
	
}
