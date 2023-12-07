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


public class RepositorioIncidentes implements WithSimplePersistenceUnit {

  private static final RepositorioIncidentes INSTANCE = new RepositorioIncidentes();

  public static RepositorioIncidentes instance() {
    return INSTANCE;
  }


  public List<IncidenteEnComunidad> traerIncidentesComunidadYEstado(Long id, String comunidad, EstadoIncidente estado) {
    List<IncidenteEnComunidad> retorno = entityManager().createQuery("select i from IncidenteEnComunidad i " +
            "inner join i.comunidadIncidente c " +
            "inner join c.miembros m " +
            "where m.usuario.id = :idUsuario and c.nombre LIKE :comunidad and i.estadoIncidente = :estado", IncidenteEnComunidad.class)
        .setParameter("idUsuario", id)
        .setParameter("comunidad", "%" + comunidad + "%")
        .setParameter("estado", estado)
        .getResultList();
    return retorno;
  }

  public List<IncidenteEnComunidad> traerIncidentesComunidad(Long id, String comunidad) {
    List<IncidenteEnComunidad> retorno = entityManager().createQuery("select i from IncidenteEnComunidad i " +
            "inner join i.comunidadIncidente c " +
            "inner join c.miembros m " +
            "where m.usuario.id = :idUsuario and c.nombre LIKE :comunidad", IncidenteEnComunidad.class)
        .setParameter("idUsuario", id)
        .setParameter("comunidad", comunidad)
        .getResultList();
    return retorno;
  }

  public List<IncidenteEnComunidad> traerIncidentesEstado(Long id, EstadoIncidente estado) {
    List<IncidenteEnComunidad> retorno = entityManager().createQuery("select i from IncidenteEnComunidad i " +
            "inner join i.comunidadIncidente c " +
            "inner join c.miembros m " +
            "where m.usuario.id = :idUsuario and i.estadoIncidente = :estado", IncidenteEnComunidad.class)
        .setParameter("idUsuario", id)
        .setParameter("estado", estado)
        .getResultList();
    return retorno;
  }

  public List<IncidenteEnComunidad> traerIncidentes(Long id) {
    List<IncidenteEnComunidad> retorno = entityManager().createQuery("select i from IncidenteEnComunidad i " +
            "inner join i.comunidadIncidente c " +
            "inner join c.miembros m " +
            "where m.usuario.id = :idUsuario", IncidenteEnComunidad.class)
        .setParameter("idUsuario", id)
        .getResultList();
    return retorno;
  }

  public void agregarIncidente(Incidente incidente) {
    persist(incidente);
  }

  public void agregarIncidenteEnComunidad(IncidenteEnComunidad incidente) {
    entityManager().persist(incidente);
  }

  public List<Incidente> buscarIncidente() {

    List<Incidente> prueba = entityManager()
        .createQuery("from Incidente", Incidente.class)
        .getResultList();

    return prueba;
  }


  public void agregarPrestacionDeServicio(PrestacionDeServicio incidente) {
    persist(incidente);
  }

  public IncidenteEnComunidad buscarIncidente(Long id) {
    return entityManager().createQuery("FROM IncidenteEnComunidad i where incidenteEnComunidadId = :id", IncidenteEnComunidad.class)
        .setParameter("id", id)
        .getResultList()
        .get(0);
  }

  public Comunidad getComunidad(long id) {
    return entityManager().createQuery("SELECT i.comunidadIncidente FROM IncidenteEnComunidad i where incidenteEnComunidadId = :id", Comunidad.class)
        .setParameter("id", id)
        .getResultList()
        .get(0);
  }


}
