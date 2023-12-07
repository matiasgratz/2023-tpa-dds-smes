package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.PasswordException;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.registradores.RegistrarUsuario;
import org.junit.jupiter.api.Test;

public class RegistrarseTest {

  @Test
  public void validandoLaContraseniaCorta(){
    assertEquals(contraseniaCorta("czelaya","czelaya@gmail.com","12").getMessage(), "No cumple con el minimo de caracteres!!!");
  }

  @Test
  public void validandoContraseniaQueEstaEnElTopDeLasPeoresContrasenias(){
    assertEquals(contraseniaCorta("czelaya","czelaya@gmail.com","12345678").getMessage(), "La contraseña es debil");
  }

  public PasswordException contraseniaCorta(String UsuarioIngresado, String email, String contraseniaIngresada){
    RegistrarUsuario registrarUsuarioIngresado = new RegistrarUsuario();
    PasswordException exceptionLogintudMinima =
        assertThrows(PasswordException.class, () -> {
          registrarUsuarioIngresado.registrarUsuario(UsuarioIngresado, email, contraseniaIngresada);
        });

    return exceptionLogintudMinima;

  }

  public PasswordException contraseniaQueEstaEntreLasPeores(String UsuarioIngresado, String email, String contraseniaIngresada){
    RegistrarUsuario registrarUsuarioIngresado = new RegistrarUsuario();
    PasswordException exceptionTopPeoresContrasenias =
        assertThrows(PasswordException.class, () -> {
          registrarUsuarioIngresado.registrarUsuario(UsuarioIngresado, email, contraseniaIngresada);
        });
    return exceptionTopPeoresContrasenias;

  }
}
