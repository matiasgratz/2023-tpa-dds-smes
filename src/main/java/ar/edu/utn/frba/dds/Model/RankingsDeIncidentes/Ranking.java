package ar.edu.utn.frba.dds.Model.RankingsDeIncidentes;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;

import java.util.ArrayList;
import java.util.List;

public interface Ranking {
    ArrayList<Entidad> iniciarReporte(ArrayList<Entidad> entidades, List<Comunidad> comunidades);
}
