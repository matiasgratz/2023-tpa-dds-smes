package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.ExportacionRanking;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.Ranking;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.RankingCantidadDeIncidentes;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.RankingTiempoPromedioDeCierre;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Miembro;
import spark.ModelAndView;
import spark.Request;
import spark.Response;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingsController {

    public ModelAndView elegirRanking(Request request, Response response) {
        Map<String, Object> modelo = new HashMap<>();
        return new ModelAndView(modelo, "rankings.html.hbs");
    }
    public ModelAndView mostrarRanking(Request request) throws IOException {
        String ranking = request.queryParams("ranking");
        Map<String, Object> modelo = new HashMap<>();
        ArrayList<Entidad> entidadesResultado = new ArrayList<>();

        String atributo = request.session().attribute("user_id");
        Long user_id = Long.parseLong(atributo);

        List<Comunidad> comunidades = RepositorioUsuarios.instance().traerMisComunidades(user_id);
        //List<Comunidad> comunidades = miembros.stream().map(Miembro::getMiComunidad).toList();

        ArrayList<Entidad> entidades = new ArrayList<Entidad>(RepositorioEntidades.instance().traerTodasLasEntidades());

        if ("rankingCantidad".equals(ranking)) {

            Ranking ranking1 = new RankingCantidadDeIncidentes();
            entidadesResultado = ranking1.iniciarReporte(entidades, comunidades);

            //ExportacionRanking exportacionRanking = new ExportacionRanking();
            //exportacionRanking.GenerarInformePorCantidadIncidentes(entidades, comunidades, "Text");

        } else if ("rankingTiempoPromedio".equals(ranking)) {

            Ranking ranking2 = new RankingTiempoPromedioDeCierre();
            entidadesResultado = ranking2.iniciarReporte(entidades, comunidades);

            //ExportacionRanking exportacionRanking = new ExportacionRanking();
            //exportacionRanking.GenerarInformePorTiempoPromedio(entidades, comunidades, "Text");
        }

        modelo.put("entidades", entidadesResultado.stream().toList());

        return new ModelAndView(modelo, "rankings.html.hbs");
    }

    public ModelAndView exportarRanking(Request request) throws IOException {

        String ranking = request.queryParams("ranking");
        Map<String, Object> modelo = new HashMap<>();
        ArrayList<Entidad> entidadesResultado = new ArrayList<>();


        return new ModelAndView(modelo, "rankings.html.hbs");
    }
}
