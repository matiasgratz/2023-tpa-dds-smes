package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones;

public class PasswordException extends RuntimeException {

  public PasswordException(String msg) {
    super(msg);
  }
}
