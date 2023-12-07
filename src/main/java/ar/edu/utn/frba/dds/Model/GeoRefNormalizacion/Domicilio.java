package ar.edu.utn.frba.dds.Model.GeoRefNormalizacion;

import javax.persistence.*;

@Entity
public class Domicilio {

  @Id
  @GeneratedValue
  private Long id;


  private Altura altura;
  @AttributeOverride(name="id", column=@Column(name="id_calle"))
  @AttributeOverride(name="nombre", column=@Column(name="nombre_calle"))
  private Calle calle;


  @AttributeOverride(name="id", column=@Column(name="id_departamento"))
  @AttributeOverride(name="nombre", column=@Column(name="nombre_departamento"))
  private Departamento departamento;
  private String nomenclatura;
  private String piso;
  @AttributeOverride(name="id", column=@Column(name="id_provincia"))
  @AttributeOverride(name="nombre", column=@Column(name="nombre_provincia"))
  private Provincia provincia;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ubicacion_id")
  public Ubicacion ubicacion;

  public boolean esCercanoA(Ubicacion ubicacion){
    return this.ubicacion.esCercanoA(ubicacion);
  }

  public Altura getAltura() {
    return altura;
  }

  public void setAltura(Altura altura) {
    this.altura = altura;
  }

  public Calle getCalle() {
    return calle;
  }

  public void setCalle(Calle calle) {
    this.calle = calle;
  }

  public Departamento getMunicipio() {
    return departamento;
  }

  public void setDepartamento(Departamento municipio) {
    this.departamento = municipio;
  }

  public String getNomenclatura() {
    return nomenclatura;
  }

  public void setNomenclatura(String nomenclatura) {
    this.nomenclatura = nomenclatura;
  }

  public String getPiso() {
    return piso;
  }

  public void setPiso(String piso) {
    this.piso = piso;
  }

  public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

}
