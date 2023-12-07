package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Calle {
  private String categoria;

  private String id;
  private String nombre;

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

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

  // Getters y setters
}
