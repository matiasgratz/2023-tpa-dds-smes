package ar.edu.utn.frba.dds.Model.UsuariosComunidad;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.IncidenteEnComunidad;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Miembro {

  @Id
  @GeneratedValue
  Long miembroId;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  public Comunidad miComunidad;


  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  public Usuario usuario;

  private boolean esAdmin;
  public String nombreDeMiComunidad;

  public Miembro(){

  }

  public boolean esAdmin(Usuario usuario){
    return this.usuario == usuario && esAdmin;
  }


  public Miembro(Comunidad comunidad, Usuario usuario, boolean esAdmin){
    this.miComunidad = comunidad;
    this.usuario = usuario;
    this.esAdmin = esAdmin;
    this.nombreDeMiComunidad = comunidad.getNombre();
    comunidad.agregarMiembros(this);
  }

  public List<IncidenteEnComunidad> incidentesActivosEnMiComunidad(Ubicacion ubicacion) {
    return miComunidad.incidentesCercanoEnComunidad(ubicacion);
  }

  public Comunidad getMiComunidad() {
    return miComunidad;
  }

  public void crearIncidenteEnComunidad(Incidente incidente) {
    miComunidad.agregarIncidente(incidente);
  }

  public void notificarSiEsNecesario(Incidente incidente) {
    usuario.notificarSiEsNecesario(incidente);
  }

  public void anteCierreIncidente(Incidente incidente) {
    usuario.anteCierreIncidente(incidente);
  }

  public boolean isEsAdmin() {
    return esAdmin;
  }

  public void setEsAdmin(boolean esAdmin) {
    this.esAdmin = esAdmin;
  }

  public Long getMiembroId() {
    return miembroId;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public String toAPI() {
    return "{ " +
        "\"id\":" + this.usuario.getId()
        + ",\"nombre\":\"" + this.usuario.getNombre() + "\"}";
  }

  public void salirComunidad() {
    usuario.salirComunidad(this);
  }
}

