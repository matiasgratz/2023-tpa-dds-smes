package ar.edu.utn.frba.dds;
import static com.google.common.io.Resources.getResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.EmailYaRegistrado;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.ImportarEmpresa;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.Registrados;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

public class ImportarCSVTest {
/*
  @Test
  public void importarEmpresa() throws IOException, EmailYaRegistrado {
    Registrados registrado = Registrados.getInstancia();
    ImportarEmpresa importador = new ImportarEmpresa(registrado);
    URL recurso = getResource("empresas_entrada.csv");
    importador.darAltaEmpresas(recurso.getPath());
    assertEquals(2,registrado.getEntidades().size());
  }

 */

}
