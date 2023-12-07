package ar.edu.utn.frba.dds.Controllers;

public class NoAutorizado extends Exception {

  public NoAutorizado(String msg) {
    super(msg);
  }
}
