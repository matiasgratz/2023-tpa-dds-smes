package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones;

public class EmailYaRegistrado extends Exception {

  public EmailYaRegistrado(String msg) {
    super(msg);
  }
}
