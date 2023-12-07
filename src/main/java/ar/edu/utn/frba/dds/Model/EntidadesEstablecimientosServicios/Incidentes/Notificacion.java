package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Notificacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private boolean fueNotificado;

  public Notificacion() {

  }

  public Incidente getIncidente() {
    return incidente;
  }

  @OneToOne
  private Incidente incidente;

  public void setFueNotificado(boolean fueNotificado) {
    this.fueNotificado = fueNotificado;
  }

  public boolean getFueNotificado() {
    return this.fueNotificado;
  }

  public Notificacion(Incidente incidente) {
    this.incidente = incidente;
    this.fueNotificado = false;
  }

  public boolean esIncidente(Incidente incidente) {
    return this.incidente == incidente;
  }
}
