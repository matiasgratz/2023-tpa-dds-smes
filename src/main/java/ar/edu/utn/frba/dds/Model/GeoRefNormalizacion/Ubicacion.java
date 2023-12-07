package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import ar.edu.utn.frba.dds.Model.UsuariosComunidad.DistanciaEntreDosPosiciones;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ubicacion {

  @Id
  @GeneratedValue
  private int id;
  private double lat;
  private double lon;

  public double getLat() {
    return lat;
  }

  public double getLon() {
    return lon;
  }

  public Ubicacion(){

  }
  public Ubicacion(double lat, double lon){
    this.lat = lat;
    this.lon = lon;
  }

  public boolean esCercanoA(Ubicacion unaPosicion) {
    return DistanciaEntreDosPosiciones
        .calcularDistancia(this, unaPosicion) < 2;
  }


}
