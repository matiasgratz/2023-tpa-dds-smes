package ar.edu.utn.frba.dds;


import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.GeorefGob;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.ServicioDeNormalizacion;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.RankingCantidadDeIncidentes;
import ar.edu.utn.frba.dds.Model.RankingsDeIncidentes.RankingTiempoPromedioDeCierre;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingTest {

    ArrayList<Entidad> entidades = new ArrayList<>();
    List<Comunidad> comunidades = new ArrayList<>();
    Entidad entidad1 = new Entidad("Entidad1");
    Entidad entidad2 = new Entidad("Entidad2");
    Comunidad comunidad1 = new Comunidad();
    Comunidad comunidad2 = new Comunidad();

    @BeforeEach
    void inicializar() throws IOException {
        ServicioDeNormalizacion servicioDeNormalizacion = new GeorefGob();
        Establecimiento establecimiento1 = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Moreno", "2526", "Buenos Aires", "San Martin"));
        Establecimiento establecimiento2 = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Moreno", "2525", "Buenos Aires", "San Martin"));

        Servicio servicio1 = new Servicio("Servicio1");
        Servicio servicio2 = new Servicio("Servicio2");

        PrestacionDeServicio prestacionDeServicio1 = new PrestacionDeServicio("prestacion1",servicio1, establecimiento1 );
        PrestacionDeServicio prestacionDeServicio2 = new PrestacionDeServicio("prestacion2",servicio2, establecimiento2 );

        establecimiento1.prestacionDeServicio(prestacionDeServicio1);
        establecimiento2.prestacionDeServicio(prestacionDeServicio2);

        entidad1.nuevoEstablecimiento(establecimiento1);
        entidad2.nuevoEstablecimiento(establecimiento2);

        Incidente incidente1 = new Incidente(prestacionDeServicio1, ".","Incidente1");
        Incidente incidente2 = new Incidente(prestacionDeServicio1, ".","Incidente2");
        Incidente incidente3 = new Incidente(prestacionDeServicio2, ".","Incidente3");

        comunidad1.agregarServicioInteres(servicio1);
        comunidad1.agregarServicioInteres(servicio2);
        comunidad2.agregarServicioInteres(servicio1);
        comunidad2.agregarServicioInteres(servicio2);

        comunidad1.agregarIncidente(incidente1);
        comunidad1.agregarIncidente(incidente2);
        comunidad2.agregarIncidente(incidente3);

        entidades.add(entidad2);
        entidades.add(entidad1);

        comunidades.add(comunidad1);
        comunidades.add(comunidad2);
    }

    @Test
    public void rankingCantidadTest(){
        //solo toma los q pasaron hace 24 hs
        comunidad1.cambiarInicioIncidentesParaPrueba();
        comunidad2.cambiarInicioIncidentesParaPrueba();

        RankingCantidadDeIncidentes ranking = new RankingCantidadDeIncidentes();
        ArrayList<Entidad> entidadesOrdenadas = ranking.iniciarReporte(entidades, comunidades);
        assertEquals(entidad1, entidadesOrdenadas.get(0));

    }


    @Test
    public void rankingPromedioDeCierre() {
        RankingTiempoPromedioDeCierre ranking = new RankingTiempoPromedioDeCierre();
        List<Entidad> entidadesOrdenadas = ranking.iniciarReporte(entidades, comunidades);
        assertEquals(entidad2, entidadesOrdenadas.get(0));
    }

}
