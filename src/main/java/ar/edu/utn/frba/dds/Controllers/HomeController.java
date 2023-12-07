package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.IncidenteEnComunidad;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioIncidentes;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Miembro;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HomeController {

  public ModelAndView getHome(Request request, Response response) {
    String comunidadBuscado = request.queryParams("comunidad");
    String estadoBuscado = request.queryParams("estado");

    Map<String, Object> modelo = new HashMap<>();
    modelo.put("anio", LocalDate.now().getYear());
    String atributo = request.session().attribute("user_id");
    Long user_id = Long.parseLong(atributo);
    List<IncidenteEnComunidad> incidenteEnComunidades;
    EstadoIncidente estado;
    if((comunidadBuscado == null || comunidadBuscado.equals("")) && (estadoBuscado == null || estadoBuscado.equals("TODOS")))
      incidenteEnComunidades = RepositorioIncidentes.instance().traerIncidentes(user_id);
    else{
      if (estadoBuscado.equals("ABIERTO"))
        estado = EstadoIncidente.ABIERTO;
      else
        estado = EstadoIncidente.CERRADO;
      if (comunidadBuscado == null || comunidadBuscado.equals("")) incidenteEnComunidades = RepositorioIncidentes.instance().traerIncidentesEstado(user_id, estado);
      else incidenteEnComunidades = RepositorioIncidentes.instance().traerIncidentesComunidadYEstado(user_id, comunidadBuscado, estado);
    }
    modelo.put("incidenteEnComunidades", incidenteEnComunidades);
    return new ModelAndView(modelo, "index.html.hbs");
  }
}
