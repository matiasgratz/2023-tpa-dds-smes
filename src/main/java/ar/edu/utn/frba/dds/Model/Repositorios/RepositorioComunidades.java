package ar.edu.utn.frba.dds.Model.Repositorios;

import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class RepositorioComunidades implements WithSimplePersistenceUnit {

  private static final RepositorioComunidades INSTANCE = new RepositorioComunidades();

  public static RepositorioComunidades instance() {
    return INSTANCE;
  }

  public List<Comunidad> todas() {
    return entityManager().createQuery("from Comunidad ", Comunidad.class).getResultList();
  }

  public Comunidad getById(Long id) {
    List<Comunidad> todas = todas();
    return entityManager().find(Comunidad.class, id);
  }

}
