package ar.edu.utn.frba.dds.Model.RankingsDeIncidentes;

    import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
    import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
    import com.opencsv.CSVWriter;

    import java.io.FileWriter;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;

public class ExportacionRanking {

  List<Entidad> entidadesRankeadas;

  public void GenerarInformePorCantidadIncidentes(ArrayList<Entidad> entidades, List<Comunidad> comunidades, String csvPathSalida) throws IOException {
    CSVWriter writer = new CSVWriter(new FileWriter(csvPathSalida));
    entidadesRankeadas = new RankingCantidadDeIncidentes().iniciarReporte(entidades, comunidades);
    entidadesRankeadas.forEach(entidad -> writer.writeNext(entidad.getNombreEntidadCSV()));
      writer.flush();
    writer.close();
  }

  public void GenerarInformePorTiempoPromedio(ArrayList<Entidad> entidades, List<Comunidad> comunidades, String csvPathSalida) throws IOException {
    CSVWriter writer = new CSVWriter(new FileWriter(csvPathSalida));
    entidadesRankeadas = new RankingTiempoPromedioDeCierre().iniciarReporte(entidades, comunidades);
    entidadesRankeadas.forEach(entidad -> writer.writeNext(entidad.getNombreEntidadCSV()));
    writer.close();
  }

}