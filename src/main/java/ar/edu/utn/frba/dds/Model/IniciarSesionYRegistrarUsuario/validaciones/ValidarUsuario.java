package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones;

import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.UserException;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.Registrados;

public class ValidarUsuario {
  private static ValidarUsuario instancia;

  private ValidarUsuario() {
  }

  public static ValidarUsuario getInstancia() {
    if (instancia != null) {
      instancia = new ValidarUsuario();
    }
    return instancia;
  }

  public Usuario validarUsuarioIngresado(String nombre, String contrasenia) {

    Usuario usuarioIngresado = RepositorioUsuarios.instance().buscarPorUsuarioYContrasenia(nombre, contrasenia); //Busco por usuario y contrasenia en una lista de usuarios registrados

    if (usuarioIngresado == null) {
      throw new UserException("Usuario o contrasenia inexistente"); //TODO hacer que devuelva que el usuario no existe
    }
    //Si encuentro el usuario en la lista retorno el usuario
    return usuarioIngresado;
  }

}
