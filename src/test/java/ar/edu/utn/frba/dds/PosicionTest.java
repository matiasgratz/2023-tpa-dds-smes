package ar.edu.utn.frba.dds;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.ServicioUbicacionGps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PosicionTest {

  double latBsAsObelisco = -34.603678;
  double lonBsAsObelisco = -58.381731;

  double latBsAsCongreso = -34.609801;
  double lonBsAsCongreso = -58.391913;

  double latBsAsPilar = -34.466928282520676;
  double lonBsAsPilar = -58.916308322792425;

  double latStaFeRosario = -32.9504536938106;
  double lonStaFeRosario = -60.68325393539601;

  private ServicioUbicacionGps servicioUbicacionGpsMock;
  private Ubicacion posicionObelisco;
  private Ubicacion posicionCongreso;
  private Ubicacion posicionPilar;
  private Ubicacion posicionRosario;



  @BeforeEach
  void initPosicion() {

    servicioUbicacionGpsMock = mock(ServicioUbicacionGps.class);

    posicionObelisco = new Ubicacion(latBsAsObelisco, lonBsAsObelisco);

    posicionCongreso = new Ubicacion(latBsAsCongreso, lonBsAsCongreso);


    posicionPilar = new Ubicacion(latBsAsPilar, lonBsAsPilar);

    posicionRosario = new Ubicacion(latStaFeRosario, lonStaFeRosario);



  }

  @Test
  public void sePuedeObtenerunaPosicion() {

    when(servicioUbicacionGpsMock.obtenerUbicacion()).thenReturn(posicionObelisco);

    Ubicacion ubicacion = servicioUbicacionGpsMock.obtenerUbicacion();

    Assertions.assertEquals(ubicacion.getLat(), latBsAsObelisco);
    Assertions.assertEquals(ubicacion.getLon(), lonBsAsObelisco);
  }

  @Test
  public void sePuedeConocerSiUnaPosicionEstaCercanaAOtra() {

    Assertions.assertTrue(posicionObelisco.esCercanoA(posicionCongreso));
    Assertions.assertFalse(posicionObelisco.esCercanoA(posicionPilar));
    Assertions.assertFalse(posicionObelisco.esCercanoA(posicionRosario));

  }


}
