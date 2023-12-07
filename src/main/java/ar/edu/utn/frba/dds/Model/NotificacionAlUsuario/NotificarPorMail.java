package ar.edu.utn.frba.dds.Model.NotificacionAlUsuario;

import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Properties;

@Entity
@DiscriminatorValue("M")
public class NotificarPorMail extends NotificarAlUsuario {
    @Transient
    private static final String SMTP_HOST = "smtp.gmail.com";
    @Transient
    private static final String SMTP_PORT = "587";

    @Override
    public void send(Usuario usuario, String subject, String texto) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        final String username = "smesdds@gmail.com";
        final String password = "iica payq durd lvbz";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario.getEmail()));
            message.setSubject(subject);
            message.setText(texto);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}