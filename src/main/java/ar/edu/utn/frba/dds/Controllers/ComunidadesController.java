package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Miembro;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;

import java.util.HashMap;
import java.util.Map;
import spark.Response;

public class ComunidadesController implements WithSimplePersistenceUnit {

  public ModelAndView getComunidades(Request request) {
    Map<String, Object> modelo = new HashMap<>();
    String userId = request.session().attribute("user_id");
    Usuario usuario = RepositorioUsuarios.instance().getById(Long.parseLong(userId));
    modelo.put("user_id", userId);
    modelo.put("user", usuario);
    modelo.put("membresias", usuario.getMisMembresias());
    modelo.put("otrasComunidades", usuario.comunidadesQueNoMeSuscribi());
    return new ModelAndView(modelo, "comunidades.html.hbs");
  }

  public Void modificarComunidades(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    String userId = request.session().attribute("user_id");
    Usuario usuario = RepositorioUsuarios.instance().getById(Long.parseLong(userId));
    String idMiembroDesuscribir = request.queryParams("desuscribir");
    String idComunidadASuscribir = request.queryParams("suscribir");


    if (idMiembroDesuscribir != null) {
      Miembro miembro = usuario.getMisMembresias()
          .stream()
          .filter(m -> m.getMiembroId() == (Long.parseLong(idMiembroDesuscribir)))
          .findFirst()
          .get();
      usuario.desuscribirAComunidad(miembro);
    }

    if (idComunidadASuscribir != null) {
      Comunidad comunidadASuscribir = RepositorioComunidades.instance().getById(Long.parseLong(idComunidadASuscribir));
      usuario.unirseAComunidad(comunidadASuscribir, false);
    }

    withTransaction(() -> {
      persist(usuario);
    });

    response.redirect("http://50.19.51.60/comunidades");

    return null;

  }

  public ModelAndView detalleComunidad(Request request) {
    Map<String, Object> modelo = new HashMap<>();
    String userId = request.session().attribute("user_id");
    Usuario usuario = RepositorioUsuarios.instance().getById(Long.parseLong(userId));
    String idMembresia = request.params("id");
    Miembro miembro = usuario.getMisMembresias()
        .stream()
        .filter(m -> m.getMiembroId() == (Long.parseLong(idMembresia)))
        .findFirst()
        .get();
    modelo.put("miembro", miembro);
    modelo.put("idMembresia", idMembresia);
    return new ModelAndView(modelo, "comunidad.html.hbs");
  }
}
