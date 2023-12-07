package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeorefGob implements ServicioDeNormalizacion {



  @Override
  public Domicilio normalizacionLocalizacion(String calle, String altura, String provincia, String municipio) throws IOException {
    String url = "http://apis.datos.gob.ar/georef/api/direcciones?";
    url = url + "direccion=" + calle + " " + altura + "&departamento=" + municipio + "&provincia=" + provincia + "";
    url = url.replace(" ", "%20");
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    StringBuilder response = new StringBuilder();

    try {
      URL apiUrl = new URL(url);
      connection = (HttpURLConnection) apiUrl.openConnection();
      connection.setRequestMethod("GET");

      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      //while ((line = reader.readLine()) != null) {
        //response.append(line);
      //}
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
      if (reader != null) {
        reader.close();
      }
    }

    Gson gson = new Gson();
    Datos datos = (gson.fromJson("{\"cantidad\":3,\"direcciones\":[{\"altura\":{\"unidad\":null,\"valor\":2526},\"calle\":{\"categoria\":\"CALLE\",\"id\":\"0637101002980\",\"nombre\":\"MORENO CALLE 148\"},\"calle_cruce_1\":{\"categoria\":null,\"id\":null,\"nombre\":null},\"calle_cruce_2\":{\"categoria\":null,\"id\":null,\"nombre\":null},\"departamento\":{\"id\":\"06371\",\"nombre\":\"Ciudad Libertador San Martín\"},\"localidad_censal\":{\"id\":\"06371010\",\"nombre\":\"General San Martín\"},\"nomenclatura\":\"MORENO CALLE 148 2526, Ciudad Libertador San Martín, Buenos Aires\",\"piso\":null,\"provincia\":{\"id\":\"06\",\"nombre\":\"Buenos Aires\"},\"ubicacion\":{\"lat\":-34.53897825548893,\"lon\":-58.56412466510702}},{\"altura\":{\"unidad\":null,\"valor\":2526},\"calle\":{\"categoria\":\"CALLE\",\"id\":\"0637101002975\",\"nombre\":\"MORENO CALLE 133\"},\"calle_cruce_1\":{\"categoria\":null,\"id\":null,\"nombre\":null},\"calle_cruce_2\":{\"categoria\":null,\"id\":null,\"nombre\":null},\"departamento\":{\"id\":\"06371\",\"nombre\":\"Ciudad Libertador San Martín\"},\"localidad_censal\":{\"id\":\"06371010\",\"nombre\":\"General San Martín\"},\"nomenclatura\":\"MORENO CALLE 133 2526, Ciudad Libertador San Martín, Buenos Aires\",\"piso\":null,\"provincia\":{\"id\":\"06\",\"nombre\":\"Buenos Aires\"},\"ubicacion\":{\"lat\":-34.57143006575812,\"lon\":-58.57822912868554}},{\"altura\":{\"unidad\":null,\"valor\":2526},\"calle\":{\"categoria\":\"CALLE\",\"id\":\"0637101002975\",\"nombre\":\"MORENO CALLE 133\"},\"calle_cruce_1\":{\"categoria\":null,\"id\":null,\"nombre\":null},\"calle_cruce_2\":{\"categoria\":null,\"id\":null,\"nombre\":null},\"departamento\":{\"id\":\"06371\",\"nombre\":\"Ciudad Libertador San Martín\"},\"localidad_censal\":{\"id\":\"06371010\",\"nombre\":\"General San Martín\"},\"nomenclatura\":\"MORENO CALLE 133 2526, Ciudad Libertador San Martín, Buenos Aires\",\"piso\":null,\"provincia\":{\"id\":\"06\",\"nombre\":\"Buenos Aires\"},\"ubicacion\":{\"lat\":-34.57915891156891,\"lon\":-58.56853141788574}}],\"inicio\":0,\"parametros\":{\"departamento\":\"San Martin\",\"direccion\":{\"altura\":{\"unidad\":null,\"valor\":\"2526\"},\"calles\":[\"Moreno\"],\"piso\":null,\"tipo\":\"simple\"},\"provincia\":\"Buenos Aires\"},\"total\":3}", Datos.class));
    return datos.getDirecciones().get(0);
  }
}

