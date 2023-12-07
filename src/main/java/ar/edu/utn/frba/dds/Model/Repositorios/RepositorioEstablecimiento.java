package ar.edu.utn.frba.dds.Model.Repositorios;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;


public class RepositorioEstablecimiento implements WithSimplePersistenceUnit {

  private static final RepositorioEstablecimiento INSTANCE = new RepositorioEstablecimiento();

  public static RepositorioEstablecimiento instance() {
    return INSTANCE;
  }


  public void agregarEstablecimiento(Establecimiento establecimiento) {
    persist(establecimiento);
  }

  public List<Establecimiento> traerTodosLosEstablecimiento() {

    List<Establecimiento> retorno = entityManager().createQuery("from Establecimiento ", Establecimiento.class)
        .getResultList();
    return retorno;
  }

  public List<Establecimiento> getEstablecimientoByEntidad(Long id) {
    List<Establecimiento> retorno = entityManager().createQuery("SELECT esta from Entidad e inner join e.establecimientos esta where e.id = :id", Establecimiento.class)
        .setParameter("id", id)
        .getResultList();
    return retorno;
  }

  public Establecimiento getByIdEstablecimiento(Long id) {
    return entityManager().find(Establecimiento.class, id);
  }

}
