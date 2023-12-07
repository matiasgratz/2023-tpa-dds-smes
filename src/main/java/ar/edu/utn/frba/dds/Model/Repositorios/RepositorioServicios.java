package ar.edu.utn.frba.dds.Model.Repositorios;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.IncidenteEnComunidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;

import java.util.List;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.Registrados;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.EmailYaRegistrado;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones.*;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Miembro;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import org.springframework.stereotype.Component;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class RepositorioServicios implements WithSimplePersistenceUnit {

  private static final RepositorioServicios INSTANCE = new RepositorioServicios();

  public static RepositorioServicios instance() {
    return INSTANCE;
  }


  public void agregarServicio(Servicio incidente) {
    persist(incidente);
  }

  public void agregarPrestacionDeServicio(PrestacionDeServicio incidente) {
    entityManager().persist(incidente);
  }

  public List<PrestacionDeServicio> traerTodosLasPrestacionDeServicio() {

    List<PrestacionDeServicio> retorno = entityManager().createQuery("from PrestacionDeServicio ", PrestacionDeServicio.class)
        .getResultList();
    return retorno;
  }

  public List<Servicio> traerTodosLosServicios() {

    List<Servicio> retorno = entityManager().createQuery("from Servicio ", Servicio.class)
        .getResultList();
    return retorno;
  }

  public Servicio getByIdServicio(Long id) {
    return entityManager().find(Servicio.class, id);
  }

  public PrestacionDeServicio getByIdPrestacionDeServicio(Long id) {
    return entityManager().find(PrestacionDeServicio.class, id);
  }

  public List<PrestacionDeServicio> getPrestacionByEstablecimiento(Long id) {
    List<PrestacionDeServicio> retorno = entityManager().createQuery("SELECT p from Establecimiento e inner join e.serviciosPrestados p where e.id = :id", PrestacionDeServicio.class)
        .setParameter("id", id)
        .getResultList();
    return retorno;
  }
}
