package ar.edu.utn.frba.dds.Controllers;


import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SessionController {
  // TODO GRAN TODO: notar que las responsabildades
  // de saber si una personas está con sesión inciada,
  // de saber le usuarie actual, etc, probablmente se vayan a repetir
  // y convendrá generalizarlas

  public ModelAndView mostrarLogin(Request request, Response response) {

    Map<String, Object> modelo = new HashMap<>();
    boolean error = false;
    if (request.session().attribute("error_login") != null && request.session().attribute("error_login") == "true")
      error = true;
    String intentos = request.session().attribute("intentos");
    if(intentos == null) {
      request.session().attribute("intentos", "0");
      intentos = "0";
    }
    modelo.put("error", error);
    if(Integer.parseInt(intentos) <= 3){
      modelo.put("intentos", intentos);
    }
    else{
      modelo.put("error", false);
      modelo.put("bloqueado", true);
    }
    request.session().attribute("error_login", "false");
    return new ModelAndView(modelo, "formulario-login.html.hbs");
  }

  public Void cerrarSesion(Request request, Response response) {
    response.removeCookie("JSESSIONID");
    response.redirect("http://50.19.51.60/login");
    return null;
  }

  public Void crearSesion(Request request, Response response) {
    String intentos = request.session().attribute("intentos");
    if(Integer.parseInt(intentos) > 3){
      LocalDateTime tiempoInicio = request.session().attribute("tiempoInicio");
      if(tiempoInicio.plusSeconds(30).isAfter(LocalDateTime.now())){
        response.redirect("http://50.19.51.60/login"); //TODO mensaje de tiempo
        return null;
      }
      request.session().attribute("intentos",0);
    }



    try {
      Usuario usuario = RepositorioUsuarios.instance().buscarPorUsuarioYContrasenia(
          request.queryParams("nombre"),
          request.queryParams("contrasenia"));

      if (usuario.getFechaExpiracionContrasenia().isBefore(LocalDateTime.now())) { // valido si la fecha de expiración es mayor a hoy
        System.out.println("Su contraseña expiro y debe cambiarlo para ingresar al sistema");
      } else {
        System.out.println("Pudo iniciar sesión correctamente");
      }
      request.session().attribute("user_id", String.valueOf(usuario.getId()));
      request.session().attribute("intentos","0");
      request.session().attribute("error_login", "false");
      response.redirect("http://50.19.51.60/");
      return null;
    } catch (Exception e) {
      intentos = String.valueOf(Integer.parseInt(intentos) + 1);
      request.session().attribute("intentos", intentos);
      request.session().attribute("tiempoInicio", LocalDateTime.now());
      request.session().attribute("error_login", "true");
      response.redirect("http://50.19.51.60/login");
      return null;
    }
  }
}