package ar.edu.utn.frba.dds.Model.RankingsDeIncidentes;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.Comparadores.EntidadComparatorCantidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;

import java.util.ArrayList;
import java.util.List;

public class RankingCantidadDeIncidentes implements Ranking{
    @Override
    public ArrayList<Entidad> iniciarReporte(ArrayList<Entidad> entidades, List<Comunidad> comunidades){
        EntidadComparatorCantidad entidadComparator = new EntidadComparatorCantidad(comunidades);
        entidades.sort(entidadComparator);
        return entidades;
    }
}
