package ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.Comparadores;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;

import java.util.Comparator;
import java.util.List;

public class EntidadComparatorTiempo implements Comparator<Entidad> {
    private final List<Comunidad> comunidades;

    public EntidadComparatorTiempo(List<Comunidad> comunidades) {
        this.comunidades = comunidades;
    }

    @Override
    public int compare(Entidad entidad1, Entidad entidad2) {
        int tiempoPromedio1 = Math.abs(promedioDeCierre(entidad1, comunidades));
        int tiempoPromedio2 = Math.abs(promedioDeCierre(entidad2, comunidades));
        return Integer.compare(tiempoPromedio2, tiempoPromedio1);
    }

    private int promedioDeCierre(Entidad entidad, List<Comunidad> comunidades) {
        List<Comunidad> comunidadesDeEntidad = comunidadesDeEntidad(comunidades, entidad);
        if (comunidadesDeEntidad.isEmpty()) {
            return 0; // Evitar división por cero
        }
        return tiempoPorComunidad(comunidades, entidad) / comunidadesDeEntidad.size();
    }

    private int tiempoPorComunidad(List<Comunidad> comunidades, Entidad entidad) {
        return comunidades.stream().mapToInt(c -> c.tiempoDeCierre(entidad)).sum();
    }
    List<Comunidad> comunidadesDeEntidad(List<Comunidad> comunidades, Entidad entidad) {
        return comunidades.stream().filter(c -> !c.incidentesDeEntidad(entidad).toList().isEmpty()).toList();
    }
}