package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.Domicilio;

import java.util.Arrays;
import java.util.List;


public class unMainParaArmarTest { //esta clase es solo a los efectos de hacer pruebas para después armar test

  public static void main(String[] args) {

    PrestacionDeServicio banio1EstLacroze = new PrestacionDeServicio(
        "Baño de hombres en Estación Federico Lacroze Subte B",
        new Servicio("Baño de hombres"),
        new Establecimiento(new Domicilio())
    );

    PrestacionDeServicio escalera1EstCatedral = new PrestacionDeServicio(
        "Escalera mecánica de acceso a Estación Catedral Subte D",
        new Servicio("Escalera Mecánica"),
        new Establecimiento(new Domicilio())
    );

    PrestacionDeServicio banio2CotoFloresta = new PrestacionDeServicio(
        "Baño de mujeres en Coto Sucursal Floresta",
        new Servicio("Baño de mujeres"),
        new Establecimiento(new Domicilio())
    );


    List<String> prestacionesDeServicios = Arrays.asList(
        banio1EstLacroze.getNombrePrestacionDeServicio(),
        banio2CotoFloresta.getNombrePrestacionDeServicio(),
        escalera1EstCatedral.getNombrePrestacionDeServicio()
    );

    System.out.println("Los siguientes servicios están cerca: ");
    System.out.println(prestacionesDeServicios);


  }

}
