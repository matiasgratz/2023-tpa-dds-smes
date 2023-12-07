package db;


import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.GeorefGob;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Domicilio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.ServicioDeNormalizacion;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContextTest implements SimplePersistenceTest {


  @Test
  void crearEntidad() throws IOException {
    ServicioDeNormalizacion servicioDeNormalizacion = new GeorefGob();
    Domicilio ubicacion = servicioDeNormalizacion.normalizacionLocalizacion("Moreno", "2526", "Buenos Aires", "San Martin");
    entityManager().persist(ubicacion);

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