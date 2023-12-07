package ar.edu.utn.frba.dds.Model.UsuariosComunidad;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.IncidenteEnComunidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Entity
public class Comunidad implements WithSimplePersistenceUnit {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String nombre;

  @OneToMany(cascade =  CascadeType.DETACH, orphanRemoval = true)
  List<Miembro> miembros = new ArrayList<>();

  @ManyToMany(cascade =  CascadeType.DETACH)
  Set<Servicio> serviciosDeInteres = new HashSet<Servicio>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "comunidadIncidente", orphanRemoval = true)
  List<IncidenteEnComunidad> incidentesDeComunidad = new ArrayList<>();


  public Comunidad(String nombre) {
    this.nombre = nombre;
  }
  public Comunidad() {
  }

  public boolean esAdmin(Usuario usuario){
    return miembros.stream().anyMatch(miembro -> miembro.esAdmin(usuario));
  }

  public List<IncidenteEnComunidad> getIncidentesDeComunidad() {
    return incidentesDeComunidad;
  }

  public void agregarServicioInteres(Servicio interes){
    serviciosDeInteres.add(interes);
  }


  public void agregarIncidente(Incidente incidente) {
    if(incidentesDeComunidad == null || ! incidentesDeComunidad.contains(incidente)){
      incidente.reportarPrimero();
    }
    if(incidente.esDeInteres(serviciosDeInteres))
    {
      incidentesDeComunidad.add(new IncidenteEnComunidad(incidente, this));
      notificarMiembrosApertura(incidente);
    }

  }

  public void agregarMiembros(Miembro miembro){
    miembros.add(miembro);
  }

  public void eliminarMiembros(Miembro miembro) {
    miembros.remove(miembro);
  }

  public void cerrarIncidente(Incidente incidente) {
    //Cerrar Incidente en comunidad o Remover el incidente de la comunidad -> Cerrar Incidente y no removerlo
    incidentesDeComunidad.stream().filter(p -> p.esIncidente(incidente)).toList().get(0).cerrarIncidente();
    notificarMiembrosCierre(incidente);
  }

  public List<IncidenteEnComunidad> incidentesCercanoEnComunidad(Ubicacion ubicacion) {
      return incidentesDeComunidad.stream().filter(incidenteEnComunidad ->
        incidenteEnComunidad.esCercanoAYEstaAbierto(ubicacion)
      ).toList();
  }

  private void notificarMiembrosApertura(Incidente incidente) {
    for (Miembro miembro : miembros) {
      miembro.notificarSiEsNecesario(incidente);
    }
  }

  private void notificarMiembrosCierre(Incidente incidente) {
    for (Miembro miembro : miembros) {
      miembro.anteCierreIncidente(incidente);
    }
  }
  public int tiempoDeCierre(Entidad entidad){
    return incidentesDeEntidad(entidad).mapToInt(IncidenteEnComunidad::tiempoDeCierre).sum();
  }

  public Stream<IncidenteEnComunidad> incidentesDeEntidad(Entidad entidad) {
    return incidentesDeComunidad.stream().filter(i -> entidad.tieneServicio(i.prestacionDeServicio()));
  }

  public int incidentesEnLaSemnana() {
      return filtrarIncidentesSemanales().size();
  }

  private List<IncidenteEnComunidad> filtrarIncidentesSemanales() {

    return this.incidentesDeComunidad.stream().filter(IncidenteEnComunidad::esDeLaSemana).toList();

    // devuelve la lista con los incidentes semanales
  }

  public void cambiarInicioIncidentesParaPrueba() {
    incidentesDeComunidad.forEach(IncidenteEnComunidad::cambiarInicioIncidentesParaPrueba);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Set<Servicio> getServiciosDeInteres() {
    return serviciosDeInteres;
  }

  public int cantDeMiembros() {
    return miembros.size();
  }

  public void eliminarInteres(Servicio interes){
    serviciosDeInteres.remove(interes);
  }

  public List<Miembro> getMiembros() {
    return miembros;
  }

  public void eliminarComunidad(){

   entityManager().createQuery("delete from Comunidad where id=:id").setParameter("id", this.id);
  }
  public String toAPIMiembros(){
    final String[] retorno = {""};
    AtomicInteger cantidad = new AtomicInteger(miembros.size());
    miembros.forEach(miembro ->
        {
          cantidad.getAndDecrement();
          retorno[0] = retorno[0].concat(miembro.toAPI());
          if(cantidad.get() > 0) retorno[0] = retorno[0].concat(",");
        }
    );
    return "[" + retorno[0] + "]";
  }

  public String toAPI(){
    return "{ " +
        "\"id\":" + this.id
        + ",\"comunidad\":\"" + this.nombre
        + "\",\"miembros\":"
        +
        toAPIMiembros()
        + ",\"intereses\":"
        +
        toAPIIntereses()
        + "}";
  }

  private String toAPIIntereses() {
    final String[] retorno = {""};
    AtomicInteger cantidad = new AtomicInteger(serviciosDeInteres.size());
    serviciosDeInteres.forEach(servicio ->
        {
          cantidad.getAndDecrement();
          retorno[0] = retorno[0].concat(servicio.toAPI());
          if(cantidad.get() > 0) retorno[0] = retorno[0].concat(",");
        }
    );
    return "[" + retorno[0] + "]";
  }


}
