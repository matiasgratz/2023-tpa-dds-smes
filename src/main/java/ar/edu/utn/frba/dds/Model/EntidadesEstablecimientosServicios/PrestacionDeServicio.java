package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios;

import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;

import javax.persistence.*;

@Entity
public class PrestacionDeServicio {

  @Id
  @GeneratedValue
  long id;
  String nombrePrestacionDeServicio;
  @ManyToOne
  Servicio servicio;
  @ManyToOne
  Establecimiento establecimiento;

  public Long getId() {
    return id;
  }

  public PrestacionDeServicio(String nombrePrestacionDeServicio, Servicio servicio, Establecimiento establecimiento) {
    this.nombrePrestacionDeServicio = nombrePrestacionDeServicio;
    this.servicio = servicio;
    this.establecimiento = establecimiento;
  }

  public PrestacionDeServicio() {

  }

  public Servicio getServicio() {
    return servicio;
  }

  public boolean esCercanoA(Ubicacion ubicacion){
    return establecimiento.esCercanoA(ubicacion);
  }

  public String getNombrePrestacionDeServicio() {
    return nombrePrestacionDeServicio;
  }

  public Establecimiento getEstablecimiento() {
    return establecimiento;
  }

  public boolean esMismoServicio(Servicio servicio) {
    return this.servicio == servicio;
  }



}
