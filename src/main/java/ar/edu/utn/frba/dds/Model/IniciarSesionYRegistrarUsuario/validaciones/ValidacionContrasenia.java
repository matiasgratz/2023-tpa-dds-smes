package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones;

import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.PasswordException;

public abstract class ValidacionContrasenia {
  protected String mensaje;

  protected ValidacionContrasenia(String mensaje) {
    this.mensaje = mensaje;
  }


  public void validate(String username, String password) {
    if (this.condition(username, password))
      throw new PasswordException(mensaje);
  }

  protected boolean condition(String username, String password) {
    return false;
  }
}
