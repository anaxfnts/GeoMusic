package models;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cuenta {

  private static final long serialVersionUID = 1L;

  StringProperty idCuenta;
  StringProperty nombre;
  StringProperty usuario;
  StringProperty contrasenya;
  StringProperty ubicacion;
  StringProperty correo;
  StringProperty tipo;
  BooleanProperty es_administrador;

  public Cuenta(String idCuenta, String nombre, String usuario, String contrasenya, String ubicacion, String correo,
      String tipo, boolean es_administrador) {
    this.idCuenta = new SimpleStringProperty(idCuenta);
    this.nombre = new SimpleStringProperty(nombre);
    this.usuario = new SimpleStringProperty(usuario);
    this.contrasenya = new SimpleStringProperty(contrasenya);
    this.ubicacion = new SimpleStringProperty(ubicacion);
    this.correo = new SimpleStringProperty(correo);
    this.tipo = new SimpleStringProperty(tipo);
    this.es_administrador = new SimpleBooleanProperty(es_administrador);
  }

  public Cuenta(String nombre, String usuario, String contrasenya, String ubicacion, String correo, String tipo) {
    this.nombre = new SimpleStringProperty(nombre);
    this.usuario = new SimpleStringProperty(usuario);
    this.contrasenya = new SimpleStringProperty(contrasenya);
    this.ubicacion = new SimpleStringProperty(ubicacion);
    this.correo = new SimpleStringProperty(correo);
    this.tipo = new SimpleStringProperty(tipo);
  }

  public String getIdCuenta() {
    return idCuenta.get();
  }

  public void setIdCuenta(String idCuenta) {
    this.idCuenta = new SimpleStringProperty(idCuenta);
  }

  public String getNombre() {
    return nombre.get();
  }

  public void setNombre(String nombre) {
    this.nombre = new SimpleStringProperty(nombre);
  }

  public String getUsuario() {
    return usuario.get();
  }

  public void setUsuario(String usuario) {
    this.usuario = new SimpleStringProperty(usuario);
  }

  public String getContrasenya() {
    return contrasenya.get();
  }

  public void setContrasenya(String contrasenya) {
    this.contrasenya = new SimpleStringProperty(contrasenya);
  }

  public String getUbicacion() {
    return ubicacion.get();
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = new SimpleStringProperty(ubicacion);
  }

  public String getCorreo() {
    return correo.get();
  }

  public void setCorreo(String correo) {
    this.correo = new SimpleStringProperty(correo);
  }

  public String getTipo() {
    return tipo.get();
  }

  public void setTipo(String tipo) {
    this.tipo = new SimpleStringProperty(tipo);
  }

  public boolean getEs_administrador() {
    return es_administrador.get();
  }

  public void setEs_administrador(boolean es_administrador) {
    this.es_administrador = new SimpleBooleanProperty(es_administrador);
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "Cuenta [nombre=" + nombre + ", usuario=" + usuario + ", contrasenya=" + contrasenya + ", ubicacion="
        + ubicacion + ", correo=" + correo + ", tipo=" + tipo + "]";
  }

}