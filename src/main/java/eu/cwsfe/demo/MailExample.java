package eu.cwsfe.demo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Sending emails through private gmail account is not reliable.
 * This class is a test for external mail services via EmailLabs
 * @see <a href="https://emaillabs.pl/">EmailLabs</a>
 *
 * Created by Radosław Osiński
 */
public class MailExample {

    public static void main(String[] args) {
        sendTestEmail();
    }

    private static void sendTestEmail() {
        String senderEmail = "login1@customdomain1.pl";  //nadawca
        String receiverEmail = "login2@customdomain2.pl"; //odbiorca
        final String username = "1.customdomain1.smtp"; //todo login z maila
        final String password = "secretpassword";      //todo hasło z rejestracji

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.emaillabs.net.pl");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail)
            );
            message.setSubject("Testing Subject 1111");
            message.setText("It works - email");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
