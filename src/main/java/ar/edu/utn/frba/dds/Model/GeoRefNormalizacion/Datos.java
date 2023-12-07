package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import java.util.List;

public class Datos {
    private int cantidad;
    private List<Domicilio> direcciones;
    private int inicio;
    private int total;


    public int getCantidad() {
      return cantidad;
    }

    public void setCantidad(int cantidad) {
      this.cantidad = cantidad;
    }

    public List<Domicilio> getDirecciones() {
      return direcciones;
    }

    public void setDirecciones(List<Domicilio> direcciones) {
      this.direcciones = direcciones;
    }

    public int getInicio() {
      return inicio;
    }

    public void setInicio(int inicio) {
      this.inicio = inicio;
    }

    public int getTotal() {
      return total;
    }

    public void setTotal(int total) {
      this.total = total;
    }


}

