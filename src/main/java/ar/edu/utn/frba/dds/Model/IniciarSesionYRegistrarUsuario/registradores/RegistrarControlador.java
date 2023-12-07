package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.registradores;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Controlador;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.TipoDeControlador;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.Registrados;

public class RegistrarControlador {
  private Registrados registrados;

  public RegistrarControlador(Registrados registrados) {
    this.registrados = registrados;
  }

  public void registrarControladorEnEntidad(String email, TipoDeControlador tipo, Entidad entidad) {
    Controlador controlador = registrados.existeControladorRegistrado(email);
    if (controlador == null) {
      controlador = new Controlador(email, tipo);
      registrados.agregarControlador(controlador);
    }
    entidad.agregarControlador(controlador);
  }

}
