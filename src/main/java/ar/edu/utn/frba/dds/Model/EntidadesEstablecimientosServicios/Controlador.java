package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Controlador {
  @Id
  @GeneratedValue
  private Long id;
  private String email;
  TipoDeControlador tipoControlador;

  public Controlador(String email, TipoDeControlador tipoDeControlador) {
    this.email = email;
    this.tipoControlador = tipoDeControlador;
  }

  public Controlador() {

  }

  public Object getEmail() {
    return this.email;
  }
}
