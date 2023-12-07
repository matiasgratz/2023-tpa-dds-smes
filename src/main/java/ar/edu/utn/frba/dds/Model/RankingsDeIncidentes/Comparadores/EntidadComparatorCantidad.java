package ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.Comparadores;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;

import java.util.Comparator;
import java.util.List;

public class EntidadComparatorCantidad implements Comparator<Entidad> {
    private final List<Comunidad> comunidades;

    public EntidadComparatorCantidad(List<Comunidad> comunidades) {
        this.comunidades = comunidades;
    }

    @Override
    public int compare(Entidad entidad1, Entidad entidad2) {
        int cantidad1 = cantidadIncidentesPorEntidad(entidad1, comunidades);
        int cantidad2 = cantidadIncidentesPorEntidad(entidad2, comunidades);
        return Integer.compare(cantidad1, cantidad2);
    }

    private int cantidadIncidentesPorEntidad(Entidad entidad, List<Comunidad> comunidades) {
        return comunidadesDeEntidad(comunidades, entidad).stream().mapToInt(Comunidad::incidentesEnLaSemnana).sum();
    }
    List<Comunidad> comunidadesDeEntidad(List<Comunidad> comunidades, Entidad entidad) {
        return comunidades.stream().filter(c -> !c.incidentesDeEntidad(entidad).toList().isEmpty()).toList();
    }
}
