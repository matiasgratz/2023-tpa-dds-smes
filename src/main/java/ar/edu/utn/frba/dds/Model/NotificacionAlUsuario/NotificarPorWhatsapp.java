package ar.edu.utn.frba.dds.Model.NotificacionAlUsuario;

import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.MedioEnvio.TwilioAdapter;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;

import javax.mail.MessagingException;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("W")
public class NotificarPorWhatsapp extends NotificarAlUsuario {

  @Transient
  private TwilioAdapter adapter = new TwilioAdapter();

  @Override
  public void send(Usuario usuario, String subject, String message) throws MessagingException {
      adapter.send(usuario, message);
    }
  }