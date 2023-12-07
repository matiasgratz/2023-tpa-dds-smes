package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import java.io.IOException;

public interface ServicioDeNormalizacion {

  public Domicilio normalizacionLocalizacion(String Calle, String Altura, String Provincia, String Municipio) throws IOException;

}
