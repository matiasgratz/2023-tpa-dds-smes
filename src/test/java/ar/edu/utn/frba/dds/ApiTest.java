package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.GeorefGob;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.ServicioDeNormalizacion;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Domicilio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApiTest {



   @Test
  void normalizacionUbicacion() throws IOException {
     ServicioDeNormalizacion servicioDeNormalizacion = new GeorefGob();
    Domicilio ubicacion = servicioDeNormalizacion.normalizacionLocalizacion("Moreno", "2526", "Buenos Aires", "San Martin");
    Assertions.assertEquals("MORENO CALLE 148",ubicacion.getCalle().getNombre());
  }

}
