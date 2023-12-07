package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.IncidenteEnComunidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.GeorefGob;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.ServicioDeNormalizacion;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.EmailYaRegistrado;
import ar.edu.utn.frba.dds.Model.Repositorios.*;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Miembro;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidentesController implements WithSimplePersistenceUnit {

  public ModelAndView getIncidente(Request request, Response response) {
    String id = request.params(":id");
    try {
      IncidenteEnComunidad incidenteEnComunidad = RepositorioIncidentes.instance().buscarIncidente(Long.parseLong(id));
      return incidenteEnComunidad != null
          ? new ModelAndView(incidenteEnComunidad, "incidente.html.hbs")
          : null;
    } catch (NumberFormatException e) {
      response.status(400);
      System.out.println("El id ingresado (" + id + ") no es un número");
      return null;
    }
  }

  public ModelAndView cerrarIncidente(Request request, Response response) {
    String id = request.params(":id");
    IncidenteEnComunidad incidente = RepositorioIncidentes.instance().buscarIncidente(Long.parseLong(id));
    Comunidad comunidad = incidente.getComunidadIncidente();
    comunidad.cerrarIncidente(incidente.getIncidente());
    withTransaction(() -> {
      persist(comunidad);
    });
    response.redirect("http://50.19.51.60/");
    return null;
  }


  public ModelAndView getIncidentesCercanos(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    String latitud_S = request.cookie("latitud");
    String longitud_S = request.cookie("longitud");
    if (latitud_S != null && longitud_S != null) {
      double latitud = Double.parseDouble(request.cookie("latitud"));
      double longitud = Double.parseDouble(request.cookie("longitud"));
      Ubicacion ubicacion = new Ubicacion(latitud, longitud);

      Usuario user = RepositorioUsuarios.instance().getById(Long.parseLong(request.session().attribute("user_id")));

      List<IncidenteEnComunidad> incidentesEnComunidades = user.incidentesCercanosA(ubicacion);

      modelo.put("incidenteEnComunidades", incidentesEnComunidades);
    } else
      modelo.put("error", true);
    return new ModelAndView(modelo, "incidentes-cercanos.html.hbs");
  }

  public ModelAndView reportarIncidente(Request request) {
    Map<String, Object> modelo = new HashMap<>();
    String atributo = request.session().attribute("user_id");
    Long user_id = Long.parseLong(atributo);

    List<Entidad> entidades = RepositorioEntidades.instance().traerTodasLasEntidades();

    modelo.put("entidades", entidades);

    return new ModelAndView(modelo, "reportar-incidente.html.hbs");
  }

  public ModelAndView agregarIncidente(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    String atributo = request.session().attribute("user_id");
    Long user_id = Long.parseLong(atributo);

    var nombre = request.queryParams("nombre");
    var idPrestacion = request.queryParams("seleccionar-servicio");
    var observacion = request.queryParams("observacion");

    PrestacionDeServicio prestacionDeServicio = RepositorioServicios.instance().getByIdPrestacionDeServicio(Long.parseLong(idPrestacion));

    Usuario usuario = RepositorioUsuarios.instance().getById(user_id);

    withTransaction(() -> {
      usuario.crearIncidente(prestacionDeServicio, observacion, nombre);
    });

    response.redirect("http://50.19.51.60/");

    return new ModelAndView("", "reportar-incidente.html.hbs");
  }


}
