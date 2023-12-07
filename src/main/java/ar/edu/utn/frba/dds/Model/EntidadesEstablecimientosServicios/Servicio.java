package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Servicio {
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Id
  @GeneratedValue
  long id;
  public String nombreServicio;

  public Servicio(String nombreServicio) {
    this.nombreServicio = nombreServicio;
  }

  public Servicio() {

  }

  public String getNombreServicio() {
    return nombreServicio;
  }

  public String toAPI() {
    return "{ " +
        "\"id\":" + this.getId()
        + ",\"nombre\":\"" + this.getNombreServicio() + "\"}";
  }
}
