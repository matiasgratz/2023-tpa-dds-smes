package ar.edu.utn.frba.dds.Model.Repositorios;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioPrestacionDeServicio implements WithSimplePersistenceUnit {

  private static final RepositorioPrestacionDeServicio INSTANCE = new RepositorioPrestacionDeServicio();

  public static RepositorioPrestacionDeServicio instance() {
    return INSTANCE;
  }


  public void agregarServicio(Servicio incidente){
    persist(incidente);
  }

  public void agregarPrestacionDeServicio(PrestacionDeServicio incidente){
    entityManager().persist(incidente);
  }

  public List<PrestacionDeServicio> traerTodosLasPrestacionDeServicio(){

    List<PrestacionDeServicio> retorno = entityManager().createQuery("from PrestacionDeServicio ", PrestacionDeServicio.class)
        .getResultList();
    return retorno;
  }

  public List<Servicio> traerTodosLosServicios(){

    List<Servicio> retorno = entityManager().createQuery("from Servicio ", Servicio.class)
        .getResultList();
    return retorno;
  }

  public Servicio getByIdServicio(Long id) {
    return entityManager().find(Servicio.class , id);
  }

}

