package ar.edu.utn.frba.dds.Model.Repositorios;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.Registrados;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.EmailYaRegistrado;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.PasswordException;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones.*;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarAlUsuario;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarPorMail;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Miembro;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import org.springframework.stereotype.Component;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.apache.commons.codec.digest.DigestUtils;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component("emailSender")
public class RepositorioUsuarios implements WithSimplePersistenceUnit {

  private final List<ValidacionContrasenia> validaciones = Arrays.asList(new CaracterRepetido(), new LongitudMinimaContrasenia(), new ValidacionTopPeoresContrasenia(), new FormatoContrasenia());

  private static final RepositorioUsuarios INSTANCE = new RepositorioUsuarios();

  public static RepositorioUsuarios instance() {
    return INSTANCE;
  }


  public List<Usuario> getRepositorioUsuarios() {
    return entityManager().createQuery("from Usuario").getResultList();
  }


  public Usuario buscarPorUsuarioYContrasenia(String email, String contrasenia) {

    Usuario retorno = entityManager()
        .createQuery("from Usuario where email = :email and contrasenia = :hashContrasenia", Usuario.class)
        .setParameter("email", email)
        .setParameter("hashContrasenia", DigestUtils.sha256Hex(contrasenia))
        .getResultList()
        .get(0);
    return retorno;
  }

  public Usuario registrarUsuario(String usuario, String email, String contrasenia) throws EmailYaRegistrado, PasswordException {
    this.validarContrasenia(usuario, contrasenia); // validamos si cumple con todas las validaciones de contrasenia
    Usuario usuarioNuevo = new Usuario(usuario, contrasenia, email, LocalDateTime.now()); // Solo si pasa la validación instanciamos el usuario
    //Le sumo 90 días a la fecha que se registró el usuario
    usuarioNuevo.setFechaExpiracionContrasenia(LocalDateTime.now().plusDays(90));

    NotificarAlUsuario notificarPorMail = new NotificarPorMail();
    entityManager().persist(notificarPorMail);
    usuarioNuevo.setMedioDeComunicacionPreferido(notificarPorMail); // Por defecto el medio de comunicación preferido es el mail

    entityManager().persist(usuarioNuevo);

    List<Usuario> prueba = entityManager()
        .createQuery("from Usuario", Usuario.class)
        .getResultList();

    return usuarioNuevo;
  }

  public List<Comunidad> traerMisComunidades(Long idUsuario) {
    List<Comunidad> retorno = entityManager().createQuery("select c from Comunidad c inner join c.miembros m where m.usuario.id = :idUsuario", Comunidad.class)
        .setParameter("idUsuario", idUsuario)
        .getResultList();
    return retorno;
  }

  public List<Comunidad> traerMisComunidadesNoAdmin(Long idUsuario) {
    return entityManager().createQuery("from Miembro M "
            + "inner join M.miComunidad "
            + "where M.usuario = :idUsuario and M.esAdmin = false ", Comunidad.class)
        .setParameter("idUsuario", idUsuario)
        .getResultList();
  }

  public List<Comunidad> traerMisComunidadesAdmin(Long idUsuario) {
    return entityManager().createQuery("from Miembro M "
            + "inner join M.miComunidad "
            + "where M.usuario = :idUsuario and M.esAdmin = true", Comunidad.class)
        .setParameter("idUsuario", idUsuario)
        .getResultList();
  }

  public void validarContrasenia(String nombre, String contrasenia) {
    try {
      this.validaciones.forEach(validacion -> validacion.validate(nombre, contrasenia));
    } catch (PasswordException e) {
      throw new PasswordException(e.getMessage());
    }
  }

  public Usuario getById(Long id) {
    return entityManager().find(Usuario.class, id);
  }

  public void agregarComunidad(Comunidad comunidad) {
    persist(comunidad);
  }
}
