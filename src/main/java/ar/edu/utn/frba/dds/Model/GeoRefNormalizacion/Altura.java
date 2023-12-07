package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import javax.persistence.Embeddable;

@Embeddable
public class Altura {
  private String unidad;
  private int valor;


  public String getUnidad() {
    return unidad;
  }

  public void setUnidad(String unidad) {
    this.unidad = unidad;
  }

  public int getValor() {
    return valor;
  }

  public void setValor(int valor) {
    this.valor = valor;
  }
}


