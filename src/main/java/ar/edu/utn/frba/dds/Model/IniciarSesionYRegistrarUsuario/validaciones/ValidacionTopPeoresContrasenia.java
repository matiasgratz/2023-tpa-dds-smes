package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones;

import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.LectorDeArchivo;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.google.common.io.Resources.getResource;

public class ValidacionTopPeoresContrasenia extends ValidacionContrasenia {

  private List<String> contrasenias;

  public ValidacionTopPeoresContrasenia() {
    super("La contraseña es debil");

    URL urlArchivo = getResource("10-million-password-list-top-10000.txt");
    LectorDeArchivo lectorArchivos = new LectorDeArchivo(urlArchivo);

    try {
      contrasenias = lectorArchivos.devolverContenidoComoListaDeStrings();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // Verifica mediante un archivo txt de 10k peores contrasenias
  @Override
  public boolean condition(String username, String password) {
    return contrasenias.stream().anyMatch(contrasenia -> contrasenia.equals(password));
  }

}
