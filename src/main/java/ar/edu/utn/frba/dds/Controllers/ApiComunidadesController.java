package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioEstablecimiento;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioServicios;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.checkerframework.checker.units.qual.C;
import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


public class ApiComunidadesController implements WithSimplePersistenceUnit {

  public Usuario autentificarUser(Request request, Response response) throws NoAutorizado {

    String authorization = request.headers("Authorization");
    if(authorization.toLowerCase().startsWith("basic")){
      String base64Credentials = authorization.substring("Basic".length()).trim();
      byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
      String[] credentials = new String(credDecoded, StandardCharsets.UTF_8).split(":", 2);
      Usuario usuario = RepositorioUsuarios.instance().buscarPorUsuarioYContrasenia(
          credentials[0],
          credentials[1]);
      if(usuario == null) {
        throw new NoAutorizado("usuario no atorizado por Basic");
      }
      return usuario;
    }
    throw new NoAutorizado("usuario no atorizado por Basic");
  }

  public Servicio getServicioFromDB(JsonObject objeto){
    Servicio servicio = RepositorioServicios.instance().getByIdServicio(objeto.get("id").getAsLong());
    return servicio;
  }

  public String crearComunidadFromJSON(Request request, Response response){
    response.type("json");
    try
    {
      Usuario usuario = autentificarUser(request, response);

      String body = request.body();
      Gson gson = new Gson();
      JsonObject object = gson.fromJson(body, JsonObject.class);
      String nombre = object.get("nombre").getAsString();
      JsonArray intereses = object.getAsJsonArray("intereses");

      Comunidad comunidad = new Comunidad(nombre);
      usuario.unirseAComunidad(comunidad, true);

      withTransaction(() -> {
        intereses.forEach(interes -> {comunidad.agregarServicioInteres(getServicioFromDB(interes.getAsJsonObject()));});
        persist(comunidad);
        persist(usuario);
      });
      response.status(201);
      return comunidad.toAPI();
    }
    catch (NoAutorizado e){
      response.status(401);
      return "{message: Error 401, usuario no autorizado}";
    }
    catch (Exception e){
      response.status(400);
      return "{message: Error 400, error en el request enviado}";
    }

  }

  public String editarComunidadFromJSON(Request request, Response response){
    response.type("json");
    try
    {
      Usuario usuario = autentificarUser(request, response);

      String body = request.body();
      Gson gson = new Gson();
      JsonObject object = gson.fromJson(body, JsonObject.class);
      String nombre = object.get("nombre").getAsString();

      Comunidad comunidad = RepositorioComunidades.instance().getById(Long.parseLong(request.params(":id")));

      if(!comunidad.esAdmin(usuario)){
        throw new NoAutorizado("usuario no tiene permiso admin");
      }

      comunidad.setNombre(nombre);

      withTransaction(() -> {
          persist(comunidad);
      });
      return comunidad.toAPI();
    }
    catch (NoAutorizado e){
      response.status(401);
      return "{message: Error 401, usuario no autorizado}";
    }
    catch (Exception e){
      response.status(400);
      return "{message: Error 400, error en el request enviado}";
    }


  }

  public String getComunidadJSON(Request request, Response response){
    response.type("json");
    try
    {
      Usuario usuario = autentificarUser(request, response);

      Comunidad comunidad = RepositorioComunidades.instance().getById(Long.parseLong(request.params(":id")));

      return comunidad.toAPI();
    }
    catch (NoAutorizado e){
      response.status(401);
      return "{message: Error 401, usuario no autorizado}";
    }
    catch (Exception e){
      response.status(400);
      return "{message: Error 400, error en el request enviado}";
    }

  }



  public String getComunidadesJSON(Request request, Response response){
    response.type("json");
    try
    {
      Usuario usuario = autentificarUser(request, response);

      List<Comunidad> comunidades = RepositorioComunidades.instance().todas();

      final String[] retorno = {""};
      AtomicInteger cantidad = new AtomicInteger(comunidades.size());
      comunidades.forEach(comunidad ->
          {
            cantidad.getAndDecrement();
            retorno[0] = retorno[0].concat(comunidad.toAPI());
            if(cantidad.get() != 0) retorno[0] = retorno[0].concat(",");
          }
      );
      return  "[" + retorno[0] + "]";
    }
    catch (NoAutorizado e){
      response.status(401);
      return "{message: Error 401, usuario no autorizado}";
    }
    catch (Exception e){
      response.status(400);
      return "{message: Error 400, error en el request enviado}";
    }

  }

  public Object editarComunidadAgregarServicioJSON(Request request, Response response) {
    response.type("json");
    try
    {
      Usuario usuario = autentificarUser(request, response);

      Comunidad comunidad = RepositorioComunidades.instance().getById(Long.parseLong(request.params(":idComunidad")));

      if(!comunidad.esAdmin(usuario)){
        throw new NoAutorizado("usuario no tiene permiso admin");
      }

      Servicio servicio = RepositorioServicios.instance().getByIdServicio(Long.parseLong(request.params(":idServicio")));

      comunidad.agregarServicioInteres(servicio);

      withTransaction(() -> {
        persist(comunidad);
      });

      return comunidad.toAPI();
    }
    catch (NoAutorizado e){
      response.status(401);
      return "{message: Error 401, usuario no autorizado}";
    }
    catch (Exception e){
      response.status(400);
      return "{message: Error 400, error en el request enviado}";
    }
  }


  public String editarComunidadFromEliminarServicioJSON(Request request, Response response) {
    response.type("json");
    try
    {
      Usuario usuario = autentificarUser(request, response);

      String body = request.body();
      Gson gson = new Gson();
      JsonObject object = gson.fromJson(body, JsonObject.class);

      Comunidad comunidad = RepositorioComunidades.instance().getById(Long.parseLong(request.params(":idComunidad")));

      if(!comunidad.esAdmin(usuario)){
        throw new NoAutorizado("usuario no tiene permiso admin");
      }

      Servicio servicio = RepositorioServicios.instance().getByIdServicio(Long.parseLong(request.params(":idServicio")));

      comunidad.eliminarInteres(servicio);

      withTransaction(() -> {
        persist(comunidad);
      });
      return comunidad.toAPI();
    }
    catch (NoAutorizado e){
      response.status(401);
      return "{message: Error 401, usuario no autorizado}";
    }
    catch (Exception e){
      response.status(400);
      return "{message: Error 400, error en el request enviado}";
    }

  }

  public String borrarComunidadFromJSON(Request request, Response response) {
    response.type("json");
    try
    {
      Usuario usuario = autentificarUser(request, response);


      Comunidad comunidad = RepositorioComunidades.instance().getById(Long.parseLong(request.params(":idComunidad")));


      if(!comunidad.esAdmin(usuario)){
        throw new NoAutorizado("usuario no tiene permiso admin");
      }

      withTransaction(() -> {
        entityManager().remove(comunidad);
      });
      List<Comunidad> comunidades = RepositorioComunidades.instance().todas();
      comunidades.size();

    }
    catch (NoAutorizado e){
      response.status(401);
      return "{message: Error 401, usuario no autorizado}";
    }
    catch (Exception e){
      response.status(400);
      return "{message: Error 400, error en el request enviado}";
    }

    return "{message:200 Borrado exitosamente}";
  }
}
