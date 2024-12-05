package models;

import java.io.Serializable;

public class Evento implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombreEvento;
  private String artista;
  private String ubicacion;
  private String fecha;
  private String imagenEvento;
  private String descripcion;
  private boolean disponible; // Nuevo atributo

  // Constructor ajustado para aceptar todos los parámetros, incluyendo el nuevo atributo
  public Evento(String nombreEvento, String descripcion, String fecha, String imagenEvento, String artista, String ubicacion, boolean disponible) {
    this.nombreEvento = nombreEvento;
    this.descripcion = descripcion;
    this.fecha = fecha;
    this.imagenEvento = imagenEvento;
    this.artista = artista;
    this.ubicacion = ubicacion;
    this.disponible = disponible;
  }

  // Constructor vacío
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

  public boolean isDisponible() {
    return disponible;
  }

  public void setDisponible(boolean disponible) {
    this.disponible = disponible;
  }

  @Override
  public String toString() {
    return "Evento [nombreEvento=" + nombreEvento + ", artista=" + artista + ", ubicacion=" + ubicacion + ", fecha="
        + fecha + ", imagenEvento=" + imagenEvento + ", descripcion=" + descripcion + ", disponible=" + disponible + "]";
  }
}
