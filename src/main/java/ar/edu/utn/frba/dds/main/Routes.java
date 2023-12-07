package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.Controllers.*;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioComunidades;
import ar.edu.utn.frba.dds.Model.TareasProgramadas.Main;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import spark.Spark;
import ar.edu.utn.frba.dds.spark.template.handlebars.HandlebarsTemplateEngine;


import javax.persistence.PersistenceException;

public class Routes implements WithSimplePersistenceUnit{

  public static void main(String[] args) {
    new Bootstrap().run();
    new Routes().start();
    SpringApplication.run(Main.class, args);
  }

  public void start() {
    System.out.println("Iniciando servidor...");
    Spark.port(8081);
    Spark.staticFileLocation("/public");
    Gson gson = new Gson();


    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

    HomeController homeController = new HomeController();
    var sesionController = new SessionController();
    var rankingsController = new RankingsController();
    ComunidadesController comunidadesController = new ComunidadesController();
    var incidentesController = new IncidentesController();
    var registroController = new RegistroController();
    var entidadController = new EntidadController();
    var establecimientoController = new EstablecimientoController();
    var apiComunidades = new ApiComunidadesController();


    Spark.get("/",homeController::getHome, engine);

    Spark.get("/login", sesionController::mostrarLogin, engine);
    Spark.post("/login", sesionController::crearSesion);

    Spark.get("/logout", sesionController::cerrarSesion);

    //Spark.get("/rankings", rankingsController::elegirRanking, engine);
    Spark.get("/rankings", (request, response) -> rankingsController.mostrarRanking(request), engine);

    Spark.get("/registro", registroController::mostrarFormularioRegistro, engine);
    Spark.post("/registro", registroController::registrar, engine);

    Spark.get("/comunidades", (request, response) -> comunidadesController.getComunidades(request), engine);
    Spark.post("/comunidades", comunidadesController::modificarComunidades);

    Spark.get("/IncidentesCercanos", incidentesController::getIncidentesCercanos, engine);

    Spark.get("/incidentes/:id",incidentesController::getIncidente, engine );

    Spark.post("/incidentes/:id",incidentesController::cerrarIncidente, engine );

    Spark.get("/JSON/entidades/:id", entidadController::getEstablecimientosFromEntidadJSON);

    Spark.get("/JSON/establecimientos/:id", establecimientoController::getPrestacionFromEntidadJSON);
    // Tener presente que es necesario loguearse (por ahora con el usuario Lucas y pass Prueba$159) para
    // que funcionen todas las otras rutas


    Spark.get("/reportarIncidente", (request, response) -> incidentesController.reportarIncidente(request), engine);
    Spark.post("/reportarIncidente", incidentesController::agregarIncidente, engine);

    Spark.get("/comunidades/:id", (request, response) -> comunidadesController.detalleComunidad(request), engine);


    // RUTAS PARA LA API COMUNIDADES
    /*
    Spark.get("/JSON/comunidades/:id", ((request, response) -> {
      Comunidad comunidad = RepositorioComunidades.instance().getById(Long.parseLong(request.params("id")));
      response.header("Content-Type", "application/json");
      return gson.toJson(comunidad);
    }));*/

    // Para probar hice también la api de comunidades pero no funciona ni esta ni la del detalle de una comunidad!!!!
    Spark.get("/api/comunidades",apiComunidades::getComunidadesJSON );

    Spark.post("/api/comunidades", apiComunidades::crearComunidadFromJSON);

    Spark.get("/api/comunidades/:id", apiComunidades::getComunidadJSON);

    Spark.patch("/api/comunidades/:id", apiComunidades::editarComunidadFromJSON);

    Spark.delete("/api/comunidades/:idComunidad/servicios/:idServicio", apiComunidades::editarComunidadFromEliminarServicioJSON);

    Spark.post("/api/comunidades/:idComunidad/servicios/:idServicio", apiComunidades::editarComunidadAgregarServicioJSON);

    Spark.delete("/api/comunidades/:idComunidad", apiComunidades::borrarComunidadFromJSON);

    Spark.exception(PersistenceException.class, (e, request, response) -> {
      response.redirect("http://50.19.51.60/500");
    });


    Spark.before(((request, response) -> {
      if(!request.pathInfo().startsWith("/login") && !request.pathInfo().startsWith("/registro")&& request.session().attribute("user_id") == null && !request.pathInfo().startsWith("/api")) {
        response.redirect("http://50.19.51.60/login");
      }
    }));

    Spark.after(((request, response) -> {
      entityManager().close();
    }));


    System.out.println("Servidor iniciado!");

  }


}