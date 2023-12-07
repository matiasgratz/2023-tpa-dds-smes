package db;


import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContextTestEntidad implements SimplePersistenceTest {


  @Test
  void crearEntidad() throws IOException {
    // Acá iria el setup del entorno.
    // entityManager().persist(ubicacion);

  }

  @Test
  void contextUp() {
    assertNotNull(entityManager());

  }

  @Test
  void contextUpWithTransaction() throws Exception {
    withTransaction(() -> {
    });
  }

}