package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios;

import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Domicilio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Establecimiento {

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Id
  @GeneratedValue
  private Long id;

  private String nombreEstablecimiento;
  @OneToOne(cascade = CascadeType.ALL)
  private Domicilio domicilio;

  @OneToMany
  List<PrestacionDeServicio> serviciosPrestados = new ArrayList<>();

  public Establecimiento(Domicilio ubicacion) {
    this.domicilio = ubicacion;
  }
  public Establecimiento(Domicilio ubicacion, String nombreEstablecimiento) {
    this.domicilio = ubicacion;
    this.nombreEstablecimiento = nombreEstablecimiento;
  }

  public Establecimiento() {

  }

  public Domicilio getUbicacion() {
    return domicilio;
  }

  public boolean tieneElServicio(PrestacionDeServicio servicio){
    return serviciosPrestados.contains(servicio);
  }

  public void prestacionDeServicio(PrestacionDeServicio servicio1) {
    serviciosPrestados.add(servicio1);
  }

  public boolean esCercanoA(Ubicacion ubicacion){
    return this.domicilio.esCercanoA(ubicacion);
  }

  public String getNombreEstablecimiento() {
    return nombreEstablecimiento;
  }

  public void setNombreEstablecimiento(String nombreEstablecimiento) {
    this.nombreEstablecimiento = nombreEstablecimiento;
  }
}
