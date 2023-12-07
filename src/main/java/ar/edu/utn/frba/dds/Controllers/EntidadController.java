package ar.edu.utn.frba.dds.Controllers;


import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioEstablecimiento;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class EntidadController {

  public String getEstablecimientosFromEntidadJSON(Request request, Response response) {
    Gson gson = new Gson();
    Long id = Long.parseLong(request.params(":id"));
    List<Establecimiento> establecimientos = RepositorioEstablecimiento.instance().getEstablecimientoByEntidad(id);
    List<ToJSON> Json = new ArrayList<>();
    establecimientos.forEach(establecimiento -> Json.add(new ToJSON(establecimiento.getId(), establecimiento.getNombreEstablecimiento())));
    return gson.toJson(Json);
  }

}
