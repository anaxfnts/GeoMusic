package models;

public class Evento {

  private static final long serialVersionUID = 1L;

  String nombreEvento;
  String artista;
  String ubicacion;
  String fecha;
  String imagenEvento;
  String descripcion;

  // Constructor ajustado para aceptar los parámetros en el orden correcto
  public Evento(String nombreEvento, String descripcion, String fecha, String imagenEvento, String artista, String ubicacion) {
    this.nombreEvento = nombreEvento;
    this.descripcion = descripcion;
    this.fecha = fecha;
    this.imagenEvento = imagenEvento;
    this.artista = artista;
    this.ubicacion = ubicacion;
  }

  public Evento() {}

  public String getNombreEvento() {
    return nombreEvento;
  }

  public void setNombreEvento(String nombre) {
    this.nombreEvento = nombre;
  }

  public String getArtista() {
    return artista;
  }

  public void setArtista(String artista) {
    this.artista = artista;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
  }

  public String getImagenEvento() {
    return imagenEvento;
  }

  public void setImagenEvento(String imagenEvento) {
    this.imagenEvento = imagenEvento;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public String toString() {
    return "Evento [nombreEvento=" + nombreEvento + ", artista=" + artista + ", ubicacion=" + ubicacion + ", fecha="
        + fecha + ", imagenEvento=" + imagenEvento + ", descripcion=" + descripcion + "]";
  }
}
