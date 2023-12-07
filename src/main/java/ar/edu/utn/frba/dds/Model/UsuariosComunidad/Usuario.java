package ar.edu.utn.frba.dds.Model.UsuariosComunidad;


import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.IncidenteEnComunidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Notificacion;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.EmailYaRegistrado;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarAlUsuario;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.persistence.*;

@Entity
public class Usuario implements WithSimplePersistenceUnit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int startNotificacion;
  private int finishNotificacion;

  @ManyToMany
  private Set<Servicio> serviciosDeInteres;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
  public List<Miembro> misMembresias = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  private List<Notificacion> incidenteANotificar;

  @OneToOne
  private NotificarAlUsuario medioDeComunicacionPreferido;

  @Transient
  ServicioUbicacionGps servicioUbicacionGps;  //TODO: creo que esto no debiera ser un atributo sino un uso mediante SINGLETON

  public void unirseAComunidad(Comunidad comunidad, Boolean esAdmin) {
    Miembro nuevoMiembro = new Miembro(comunidad, this, esAdmin);
    misMembresias.add(nuevoMiembro);
  }

  public void desuscribirAComunidad(Miembro miembro) {
    misMembresias.remove(miembro);
    miembro.miComunidad.eliminarMiembros(miembro);
  }

  public String nombre;
  private String contrasenia;
  private String email;
  private LocalDateTime fechaRegistracion;
  private LocalDateTime fechaExpiracionContrasenia;
  private String tel;


  public Usuario(String nombre, String contrasenia, String email, LocalDateTime fechaRegistracion) {
    this.nombre = nombre;
    this.contrasenia = DigestUtils.sha256Hex(contrasenia);
    this.fechaRegistracion = fechaRegistracion;
    this.email = email;
  }


  public Usuario() {

  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public void setMedioDeComunicacionPreferido(NotificarAlUsuario medioDeComunicacionPreferido) {
    this.medioDeComunicacionPreferido = medioDeComunicacionPreferido;
  }

  public void setNotificaciones(int startNotificacion, int finishNotificacion) {
    this.startNotificacion = startNotificacion;
    this.finishNotificacion = finishNotificacion;
  }

  public void notificarmeIncidentesActivosYNoNotificados() {
    if (esHoraDeNotificar()) {
      try {
        List<Notificacion> incidentesNoNotificados = obtenerIncidentesNoNotificados();
        notificarIncidentes(incidentesNoNotificados);
        marcarIncidentesComoNotificados(incidentesNoNotificados);
      } catch (MessagingException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private boolean esHoraDeNotificar() {
    var horaActual = LocalDateTime.now().getHour();
    return this.startNotificacion >= horaActual && horaActual >= this.finishNotificacion;
  }

  private List<Notificacion> obtenerIncidentesNoNotificados() {
    return incidenteANotificar.stream().filter(incidente -> !incidente.getFueNotificado()).toList();
  }

  private void notificarIncidentes(List<Notificacion> incidentesNoNotificados) throws MessagingException {
    List<String> incidentesAEnviar = incidentesNoNotificados.stream()
        .map(a -> a.getIncidente().getNombreIncidente())
        .toList();
    medioDeComunicacionPreferido.send(
        this,
        "Incidentes reportados",
        "Estos son los incidentes que se reportaron en las comunidades: " + incidentesAEnviar
    );
  }

  private void marcarIncidentesComoNotificados(List<Notificacion> incidentesNoNotificados) {
    incidentesNoNotificados.forEach(notificacion -> notificacion.setFueNotificado(true));
  }

  public void anteCierreIncidente(Incidente incidente) {
    Optional<Notificacion> notificacion = incidenteANotificar.stream().filter(p -> p.esIncidente(incidente)).findFirst();
    incidenteANotificar.remove(notificacion);
  }


  public void notificarSiEsNecesario(Incidente incidente) {
    if (incidente.esDeInteres(serviciosDeInteres)) {
      incidenteANotificar.add(new Notificacion(incidente));
    }
  }

  public void crearIncidente(PrestacionDeServicio prestacionDeServicio, String observacion, String nombreIncidente) {
    Incidente incidente = new Incidente(prestacionDeServicio, observacion, nombreIncidente);
    withTransaction(() -> {
      try {
        persist(incidente);
        //prestacionDeServicio.setEstaFuncionando(false); -> Estado duplicado de Lowi
        for (Miembro miembro : misMembresias) {
          miembro.crearIncidenteEnComunidad(incidente);
        }
      } catch (RuntimeException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public void setContrasenia(String contrasenia) {
    this.contrasenia = contrasenia;
  }

  public LocalDateTime getFechaRegistracion() {
    return fechaRegistracion;
  }

  /**
   * no agrego el setter de fechaRegistración, ya que no es un campo que se pueda modificar
   **/

  public LocalDateTime getFechaExpiracionContrasenia() {
    return fechaExpiracionContrasenia;
  }

  public void setFechaExpiracionContrasenia(LocalDateTime fechaExpiracionContrasenia) {
    this.fechaExpiracionContrasenia = fechaExpiracionContrasenia;
  }

  public void cambiarContrasenia(Usuario usuario, String contraseniaNueva) {
    usuario.setContrasenia(contrasenia);
    usuario.setFechaExpiracionContrasenia(LocalDateTime.now());
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public void iniciarSesion() throws MessagingException {
    if (this.incidentesCercanosDeMisComunidades() != null) {
      /*Lucas: le agregué este if que habías sacado (adaptado a los nuevos métodos), porque si no hay incidentes cercanos,
       * no debería dispararninguna notificación al usuario. Si estás de acuerdo, borrá este comentario y fijate
       * si ya se puede borrar también los comentarios al final de la clase*/
      this.sugerirRevisionDeIncidentesCercanosA(servicioUbicacionGps.obtenerUbicacion());
    }

  }

  public List<IncidenteEnComunidad> incidentesCercanosDeMisComunidades() {
    return
        misMembresias
            .stream()
            .flatMap(miembro -> miembro.incidentesActivosEnMiComunidad(servicioUbicacionGps.obtenerUbicacion()).stream())
            .collect(Collectors.toList());
  }

  public List<IncidenteEnComunidad> incidentesCercanosA(Ubicacion ubicacion) {
    return
        misMembresias
            .stream()
            .flatMap(miembro -> miembro.incidentesActivosEnMiComunidad(ubicacion).stream())
            .collect(Collectors.toList());
  }


  private void sugerirRevisionDeIncidentesCercanosA(Ubicacion ubicacion) throws MessagingException {

    List<String> listaDeIncidentesCercanos = this.incidentesCercanosDeMisComunidades()
        .stream()
        .map(IncidenteEnComunidad::getNombrePrestacionDeServicio)
        .toList();

    medioDeComunicacionPreferido.send(
        this,
        "Hay servicios interrumpidos cercanos",
        "Estás cerca de los siguientes servicios que no funcionan seguidos por tus comunidades: "
            + listaDeIncidentesCercanos
    );
  }

  public void anteIncidenteEntidad(Incidente incidente, Entidad entidad, PrestacionDeServicio prestacionDeServicio) throws MessagingException {

    medioDeComunicacionPreferido.send(
        this,
        "Alerta de mal funcionamiento de servicio",
        "La empresa"
            + entidad.getNombreEntidad()
            + "anuncia que el "
            + prestacionDeServicio.getNombrePrestacionDeServicio()
            + "se encuentra fuera de servicio"
    );
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Miembro> getMisMembresias() {
    return misMembresias;
  }

  public List<Comunidad> comunidadesQueNoMeSuscribi() {
    List<Comunidad> misComunidades = misMembresias.stream().map(miembro -> miembro.miComunidad).toList();
    return RepositorioComunidades.instance().todas().stream().filter(elem -> !misComunidades.contains(elem)).toList();



  }

  public void salirComunidad(Miembro miembro) {
    misMembresias.remove(miembro);
  }

/**   List<String> lista = listaB.stream().filter(f-> !listaA.contains(f)).collect(Collectors.toList());
 System.out.println(lista);


  /***************************************************
 *
 *  posicion no existe ¿ ver cual es la sustitucion
 *

 public void iniciarSesion() throws MessagingException {
 posicion.actualizarPosicion();
 if (posicion.serviciosConIncidenteCercanos() != null) {
 this.sugerirRevisionDeIncidentesCercanos();
 }
 }

 private void sugerirRevisionDeIncidentesCercanos() throws MessagingException {
 List<String> listaDeIncidentesCercanos =
 posicion
 .serviciosConIncidenteCercanos()
 .stream()
 .map(PrestacionDeServicio::getNombrePrestacionDeServicio)
 .toList();

 medioDeComunicacionPreferido.send(
 this,
 "Hay servicios interrumpidos cercanos",
 "Estás cerca de los siguientes servicios que no funcionan seguidos por tus comunidades: "
 + listaDeIncidentesCercanos
 );
 }

 public void anteIncidenteEntidad(Incidente incidente, Entidad entidad, PrestacionDeServicio prestacionDeServicio) throws MessagingException {

 medioDeComunicacionPreferido.send(
 this,
 "Alerta de mal funcionamiento de servicio",
 "La empresa"
 + entidad.getNombreEntidad()
 + "anuncia que el "
 + prestacionDeServicio.getNombrePrestacionDeServicio()
 + "se encuentra fuera de servicio"
 );
 }
 ******************************************************************************************/
}
