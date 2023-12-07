package ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Ubicacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;


import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

public class Incidente implements WithSimplePersistenceUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String nombreIncidente;

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String observacion;
    @OneToOne
    @JoinColumn(name = "prestacion_de_servicio_id")
    PrestacionDeServicio lugarIncidente;
    LocalDateTime primerReporte;

    public Incidente() {

    }

    public String getNombrePrestacionDeServicio(){
        return lugarIncidente.getNombrePrestacionDeServicio();
    }

    public boolean esCercanoA(Ubicacion ubicacion) {
        return lugarIncidente.esCercanoA(ubicacion);
    }

    public void reportarPrimero() {
        primerReporte = LocalDateTime.now();
    }

    public Incidente(PrestacionDeServicio lugarIncidente, String observacion, String nombreIncidente) {
        this.observacion = observacion;
        this.lugarIncidente = lugarIncidente;
        this.nombreIncidente = nombreIncidente;
    }

    public String getNombreIncidente() {
        return nombreIncidente;
    }

    public PrestacionDeServicio getLugarIncidente() {
        return lugarIncidente;
    }

    public boolean esDeInteres(Set<Servicio> serviciosDeInteres) {
        return serviciosDeInteres.stream().anyMatch(servicio -> lugarIncidente.esMismoServicio(servicio));
    }

    public boolean validarReporte(LocalDateTime inicio) {
        return Duration.between(inicio, LocalDateTime.now()).toHours() >= 24;
    }
}
