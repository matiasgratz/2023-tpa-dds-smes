package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Controlador;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Registrados {
  private static Registrados instancia = null;
  private List<Usuario> usuarios = new ArrayList<>();
  private List<Entidad> entidades = new ArrayList<>();

  private List<Controlador> controladores = new ArrayList<>();

  private Registrados() {
  }

  public static Registrados getInstancia() {
    if (instancia == null) {
      Registrados.instancia = new Registrados();
    }
    return instancia;
  }

  public List<Entidad> getEntidades() {
    return entidades;
  }

  public void agregarControlador(Controlador controlador) {
    instancia.controladores.add(controlador);
  }

  public void agregarUsuarios(Usuario usuarioNuevo) {
    instancia.usuarios.add(usuarioNuevo);
  }

  public void agregarEntidad(Entidad entidad) {
    instancia.entidades.add(entidad);
  }

  public boolean existeNombreUsuarioRegistrado(String nombreUsuario) {
    Optional<Usuario> usuario = instancia.usuarios.stream().filter(x -> x.getNombre().equals(nombreUsuario)).findFirst();
    return usuario.isPresent();
  }

  public Entidad existeEntidadRegistrada(String nombreEntidad) {
    Optional<Entidad> entidad = instancia.entidades.stream().filter(x -> x.getNombreEntidad().equals(nombreEntidad)).findFirst();
    return entidad.orElse(null);
  }

  public Controlador existeControladorRegistrado(String emailControlador) {
    Optional<Controlador> controlador = instancia.controladores.stream().filter(x -> x.getEmail().equals(emailControlador)).findFirst();
    return controlador.orElse(null);
  }



}
