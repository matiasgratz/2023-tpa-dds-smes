package ar.edu.utn.frba.dds.Model.RankingsDeIncidentes;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.Comparadores.EntidadComparatorTiempo;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;


import java.util.ArrayList;
import java.util.List;

public class RankingTiempoPromedioDeCierre implements Ranking{
    public RankingTiempoPromedioDeCierre() {
    }
    @Override
    public ArrayList<Entidad> iniciarReporte(ArrayList<Entidad> entidades, List<Comunidad> comunidades) {
        EntidadComparatorTiempo entidadComparator = new EntidadComparatorTiempo(comunidades);
        entidades.sort(entidadComparator);
        return entidades;
    }
}
