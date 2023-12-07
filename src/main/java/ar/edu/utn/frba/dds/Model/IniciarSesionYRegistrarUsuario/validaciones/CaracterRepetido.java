package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones;

public class CaracterRepetido extends ValidacionContrasenia {

  public CaracterRepetido() {
    super("Hay caracteres repetidos!");
  }

  @Override
  public boolean condition(String username, String password) {
    boolean esRepetido = false;
    return esRepetido;
  }
}
