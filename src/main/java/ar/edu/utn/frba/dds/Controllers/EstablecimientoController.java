package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioEstablecimiento;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioServicios;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class EstablecimientoController {

  public String getPrestacionFromEntidadJSON(Request request, Response response) {
    Gson gson = new Gson();
    Long id = Long.parseLong(request.params(":id"));
    List<PrestacionDeServicio> prestaciones = RepositorioServicios.instance().getPrestacionByEstablecimiento(id);
    List<ToJSON> Json = new ArrayList<>();
    prestaciones.forEach(prestacion -> Json.add(new ToJSON(prestacion.getId(), prestacion.getNombrePrestacionDeServicio())));
    return gson.toJson(Json);
  }

}
