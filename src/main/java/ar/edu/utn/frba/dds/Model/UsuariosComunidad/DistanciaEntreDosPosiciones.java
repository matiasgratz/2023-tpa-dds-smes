package ar.edu.utn.frba.dds.Model.UsuariosComunidad;

import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;

public class DistanciaEntreDosPosiciones {

  private DistanciaEntreDosPosiciones() {}


  public static double calcularDistancia(Ubicacion unaPosicion, Ubicacion otraPosicion) {
    double latitud1 = Math.toRadians(unaPosicion.getLat());
    double longitud1 = Math.toRadians(unaPosicion.getLon());
    double latitud2 = Math.toRadians(otraPosicion.getLat());
    double longitud2 = Math.toRadians(otraPosicion.getLon());

    final double RADIO_TIERRA = 6371.01;

    double distancia = RADIO_TIERRA * Math.acos(Math.sin(latitud1) * Math.sin(latitud2)
        + Math.cos(latitud1) * Math.cos(latitud2) * Math.cos(longitud1 - longitud2));

    return distancia;

  }

}
