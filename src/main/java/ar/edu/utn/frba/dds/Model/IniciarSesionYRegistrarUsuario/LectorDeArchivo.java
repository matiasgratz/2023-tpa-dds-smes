package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class LectorDeArchivo {

  private URL urlArchivo;

  public LectorDeArchivo(URL urlArchivo) {
    this.urlArchivo = urlArchivo;
  }

  public List<String> devolverContenidoComoListaDeStrings() throws IOException {
    String contenidoArchivo = "";

    try {
      contenidoArchivo = Resources.toString(urlArchivo, Charsets.UTF_8);
      contenidoArchivo = contenidoArchivo.replace("\n", ",");
      contenidoArchivo = contenidoArchivo.replace("\r", "");
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    }
    return Arrays.asList(contenidoArchivo.split(","));
  }

  public static class UbicacionApi {
  }
}