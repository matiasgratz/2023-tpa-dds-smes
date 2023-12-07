package ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.TipoDeControlador;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.registradores.RegistrarControlador;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.registradores.RegistrarEntidad;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;


import java.io.IOException;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.*;

public class ImportarEmpresa {

  private Registrados registrados;
  private static final int PASSWORD_LENGTH = 16;
  public static final char SEPARATOR = ',';
  public static final char QUOTE = '"';
  private CSVReader reader = null;

  public ImportarEmpresa(Registrados registrados) {
    this.registrados = registrados;
  }

  public void darAltaEmpresas(String csvPathEntrada) throws IOException {

    try {
      reader = new CSVReader(new FileReader(csvPathEntrada), SEPARATOR, QUOTE);
      StringWriter writer = new StringWriter();
      CSVWriter csvWriter = new CSVWriter(writer, ',', '\'');
      String[] nextLine;

      while ((nextLine = reader.readNext()) != null) {
        RegistrarEntidad registrarEntidad = new RegistrarEntidad(registrados);
        Entidad entidad = registrarEntidad.traerEntidadSiExisteSinoCrearla(nextLine[0]);
        RegistrarControlador registrarControlador = new RegistrarControlador(registrados);
        registrarControlador.registrarControladorEnEntidad(nextLine[4], stringToEnumControlador(nextLine[1]), entidad);
      }
    } finally {
      if (null != reader) {
        reader.close();
      }
    }
  }


  public TipoDeControlador stringToEnumControlador(String tipo) {
    if (Objects.equals(tipo, "empresa")) return TipoDeControlador.ENTIDAD_PRESTADORA;
    return TipoDeControlador.ORGANISMO_CONTROL;
  }

  private String generateUsername(String firstName, String lastName) {
    String username = (firstName.charAt(0) + lastName).toLowerCase();
    int count = 1;
    String newUsername = username;

    while (registrados.existeNombreUsuarioRegistrado(newUsername)) {
      newUsername = username + count;
      count++;
    }

    return newUsername;
  }

  private static String generatePassword(int length) {
    String minusculas = "abcdefghijklmnopqrstuvwxyz";
    String mayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String especial = "!@#$%^&*()-_=+";
    String numeros = "0123456789";

    Random random = new SecureRandom();
    StringBuilder password = new StringBuilder(length);

    // Generar un carácter especial
    password.append(especial.charAt(random.nextInt(especial.length())));

    // Generar una mayúscula
    password.append(mayusculas.charAt(random.nextInt(mayusculas.length())));

    // Generar una minúscula
    password.append(minusculas.charAt(random.nextInt(minusculas.length())));

    // Generar un número
    password.append(numeros.charAt(random.nextInt(numeros.length())));

    // Generar los caracteres restantes de forma aleatoria
    for (int i = 4; i < length; i++) {
      String characters = minusculas + mayusculas + especial + numeros;
      password.append(characters.charAt(random.nextInt(characters.length())));
    }

    // Mezclar los caracteres en la contraseña generada
    for (int i = password.length() - 1; i > 0; i--) {
      int j = random.nextInt(i + 1);
      char temp = password.charAt(i);
      password.setCharAt(i, password.charAt(j));
      password.setCharAt(j, temp);
    }

    return password.toString();
  }
}
