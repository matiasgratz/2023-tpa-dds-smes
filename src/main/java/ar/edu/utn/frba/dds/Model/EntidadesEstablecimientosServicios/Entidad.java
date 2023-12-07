package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.persistence.*;

@Entity
public class Entidad {

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  public String nombreEntidad;

  @ManyToMany
  private List<Controlador> controladores = new ArrayList<>();

  @ManyToMany
  private List<Usuario> interesadosEntidad = new ArrayList<>();

  @OneToMany
  @OrderColumn(name = "orden")
  List<Establecimiento> establecimientos = new ArrayList<>();

  public List<Establecimiento> getEstablecimientos(){
    return establecimientos;
  }


  public Entidad() {

  }

  public void agregarControlador(Controlador controlador) {
    controladores.add(controlador);
  }

  public Entidad(String nombreEntidad) {
    this.nombreEntidad = nombreEntidad;
  }

  public String[] getNombreEntidadCSV() {
    return new String[]{this.nombreEntidad};
  }

  public String getNombreEntidad() {
    return this.nombreEntidad;
  }

  public boolean tieneServicio(PrestacionDeServicio servicio) {
    return entidadTieneServicio(servicio).isEmpty();
  }

  public void nuevoEstablecimiento(Establecimiento establecimiento) {
    establecimientos.add(establecimiento);
  }

  private List<Establecimiento> entidadTieneServicio(PrestacionDeServicio servicio) {
    return establecimientos.stream().filter(e -> e.tieneElServicio(servicio)).toList();
  }

  public void crearIncidente(PrestacionDeServicio prestacionDeServicio, String observacion, String nombreIncidente) {
    Incidente incidente = new Incidente(prestacionDeServicio, observacion, nombreIncidente);

    interesadosEntidad.forEach(
        interesado -> {
          try {
            interesado.anteIncidenteEntidad(incidente, this, prestacionDeServicio);
          } catch (MessagingException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }

  public void suscribirseAEntidad(Usuario usuario) {
    interesadosEntidad.add(usuario);
  }

  public void desuscribirseAEntidad(Usuario usuario) {
    interesadosEntidad.remove(usuario);
  }

}
