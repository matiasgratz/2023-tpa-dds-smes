package ar.edu.utn.frba.dds.Model.Repositorios;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;


public class RepositorioEntidades implements WithSimplePersistenceUnit {

  private static final RepositorioEntidades INSTANCE = new RepositorioEntidades();

  public static RepositorioEntidades instance() {
    return INSTANCE;
  }

  public void agregarEntidad(Entidad entidad) {
    persist(entidad);
  }

  public List<Entidad> traerTodasLasEntidades() {

    List<Entidad> retorno = entityManager().createQuery("from Entidad ", Entidad.class)
        .getResultList();
    return retorno;
  }

  public Entidad getByIdEntidad(Long id) {
    return entityManager().find(Entidad.class, id);
  }
}
