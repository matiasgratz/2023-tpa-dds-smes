package ar.edu.utn.frba.dds.Model.NotificacionAlUsuario;

import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;

import javax.mail.MessagingException;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "medioDeDifusion")
public abstract class NotificarAlUsuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public abstract void send(Usuario usuario, String subject, String message) throws MessagingException;
}
