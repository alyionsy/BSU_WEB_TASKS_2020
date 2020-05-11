package by.ignot.web.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtility {
    private static final Logger logger = LogManager.getLogger(EmailUtility.class);

    public static void sendEmail(String toAddress, String subject, String messageText){

        Properties properties = getProperties();

        Session session = getSession(properties);

        Message message = getMessage(session, toAddress, subject, messageText);


        try{
            if (message != null){
                Transport.send(message);
                logger.info("message sent");
            }
            else throw new NullPointerException();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private static Properties getProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }

    private static Session getSession(Properties properties){
        return Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(EmailData.LOGIN, EmailData.PASSWORD);

            }
        });
    }

    private static Message getMessage(Session session,String toAddress,String subject,String messageText){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailData.LOGIN));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setText(messageText);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
