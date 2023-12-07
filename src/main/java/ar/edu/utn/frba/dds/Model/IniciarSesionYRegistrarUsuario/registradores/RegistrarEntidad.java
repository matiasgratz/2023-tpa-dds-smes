package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.registradores;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.Registrados;

public class RegistrarEntidad {
  Registrados registrados;

  public RegistrarEntidad(Registrados registrados) {
    this.registrados = registrados;
  }

  public Entidad traerEntidadSiExisteSinoCrearla(String s) {
    Entidad entidad = registrados.existeEntidadRegistrada(s);
    if (entidad == null) {
      entidad = new Entidad(s);
      registrados.agregarEntidad(entidad);
    }
    return entidad;
  }
}
