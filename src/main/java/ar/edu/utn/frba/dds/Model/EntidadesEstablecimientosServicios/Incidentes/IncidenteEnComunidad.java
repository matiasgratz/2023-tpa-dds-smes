package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Entity
public class IncidenteEnComunidad {

  @Id
  @GeneratedValue
  private Long incidenteEnComunidadId;
  @ManyToOne
  @JoinColumn(name = "incidente_id", referencedColumnName = "id")
  public Incidente incidente;
  private EstadoIncidente estadoIncidente;

  @ManyToOne
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  private Comunidad comunidadIncidente;
  @Transient
  private LocalDateTime inicio = LocalDateTime.now();;
  @Transient
  private LocalDateTime cierre = LocalDateTime.now();

  public IncidenteEnComunidad(Incidente incidente, Comunidad comunidad) {
    this.incidente = incidente;
    this.estadoIncidente = EstadoIncidente.ABIERTO;
    this.inicio = LocalDateTime.now();
    this.comunidadIncidente = comunidad;
  }

  public String getNombrePrestacionDeServicio(){
    return incidente.getNombrePrestacionDeServicio();
  }

  public IncidenteEnComunidad() {

  }

  public boolean esCercanoAYEstaAbierto(Ubicacion ubicacion){
    if (estadoIncidente.equals(EstadoIncidente.CERRADO)) return false;
    return this.esCercanoA(ubicacion);
  }
  public boolean esCercanoA(Ubicacion ubicacion){
    return incidente.esCercanoA(ubicacion);
  }

  public Incidente getIncidente() {
    return incidente;
  }

  public boolean estadoIncidenteEsAbierto() {
    return estadoIncidente == EstadoIncidente.ABIERTO;
  }

  public boolean esIncidente(Incidente incidente) {
    return this.incidente == incidente;
  }
  public int tiempoDeCierre(){
    return (int) ChronoUnit.MICROS.between(cierre, inicio);
  }
  public PrestacionDeServicio prestacionDeServicio(){
    return incidente.lugarIncidente;
  }

  public boolean esDeLaSemana() {
    if (incidente.validarReporte(inicio)) {

      return Period.between(inicio.toLocalDate(), LocalDateTime.now().toLocalDate()).getDays() <= 7;
    }
    return true;
  }
  public void cerrarIncidente() {
    estadoIncidente = EstadoIncidente.CERRADO;
    cierre = LocalDateTime.now();
  }

  public void cambiarInicioIncidentesParaPrueba() {
    this.inicio = inicio.minusHours(25);
    incidente.primerReporte = inicio;
  }

  public Long getIncidenteEnComunidadId() {
    return incidenteEnComunidadId;
  }

  public void setIncidenteEnComunidadId(Long incidenteEnComunidadId) {
    this.incidenteEnComunidadId = incidenteEnComunidadId;
  }

  public Comunidad getComunidadIncidente() {
    return comunidadIncidente;
  }

  public void setComunidadIncidente(Comunidad comunidadIncidente) {
    this.comunidadIncidente = comunidadIncidente;
  }
}

