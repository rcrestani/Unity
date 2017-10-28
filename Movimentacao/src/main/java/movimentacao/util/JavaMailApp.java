package movimentacao.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;

public class JavaMailApp 
{
	private String destinatario , assunto, msg;
	private InputStream arquivo;
	private String nomeArquivo;
	private String applicationType;
	private boolean enviado;
	
	ByteArrayOutputStream byteStream = null;
	ObjectOutputStream objectStream = null;
	
	
    public void sendMail() throws IOException
    {

		Properties props = new Properties();
        /** Parâmetros de conexão com servidor de email */
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host", "mail.performancelab.com.br");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication()
                         {
                               return new PasswordAuthentication("crm@performancelab.com.br", "dgr508467");
                         }
                    });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {

              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress("crm@performancelab.com.br")); //Remetente

              Address[] toUser = InternetAddress //Destinatário(s)
                         .parse(destinatario);  

              message.setRecipients(Message.RecipientType.TO, toUser);
              message.setSubject(assunto);//Assunto              
             
              Multipart parteEmail = new MimeMultipart();
              MimeBodyPart corpo = new MimeBodyPart();
              corpo.setContent("<html>" + msg + "</html>", "text/html; charset=UTF-8");
              parteEmail.addBodyPart(corpo);
	            
              if(StringUtils.isNotEmpty(applicationType))
              {
	              //InputStream is = getClass().getResourceAsStream(arquivo);
             
	              MimeBodyPart anexo = new MimeBodyPart();
	              anexo.setDataHandler(new DataHandler(new ByteArrayDataSource(arquivo , applicationType)));
	              anexo.setFileName(nomeArquivo);
	              
	              parteEmail.addBodyPart(anexo);
	              
              }
              
              message.setContent(parteEmail);
              /**Método para enviar a mensagem criada*/
              Transport.send(message);
              enviado = true;
              
         } catch (MessagingException e) {
              throw new RuntimeException(e);
        }
    }
    

	public boolean getEnviado() {
		return enviado;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}



	public String getAssunto() {
		return assunto;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getMsg() {
		return msg;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}


	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}


	public String getApplicationType() {
		return applicationType;
	}


	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}


	public InputStream getArquivo() {
		return arquivo;
	}


	public void setArquivo(InputStream arquivo) {
		this.arquivo = arquivo;
	}

}
