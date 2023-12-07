package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones;

public class LongitudMinimaContrasenia extends ValidacionContrasenia {
  private Integer LONGITUD_MINIMA = 8;

  public LongitudMinimaContrasenia() {
    super("No cumple con el minimo de caracteres!!!");
  }

  @Override
  public boolean condition(String username, String password) {
    return password.length() < LONGITUD_MINIMA;
  }
}
