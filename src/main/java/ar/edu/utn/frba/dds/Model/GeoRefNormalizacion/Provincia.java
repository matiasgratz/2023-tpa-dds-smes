package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Provincia {

  private String id;
  private String nombre;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
}
