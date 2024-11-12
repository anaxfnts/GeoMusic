package views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.mail.MessagingException;
import firebase.CRUDFirebase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Emails;
import models.Evento;

public class APITableController {

  private Evento evento = null;
  
  @FXML
  private TableColumn nombreEvento;

  @FXML
  private TableColumn artista;
  
  @FXML
  private TableColumn fecha;
  
  @FXML
  private TableColumn ubicacion;
  
  @FXML
  private TableColumn validez;


  /**
   * Establece los datos del evento en los campos correspondientes.
   *
   * @param eventos El evento con los datos a establecer.
   * @throws FileNotFoundException Si ocurre un error al encontrar el archivo de
   *                               imagen.
   */
  public void setDatos(Evento eventos) {
    evento = eventos;
    nombreEvento.setText(eventos.getNombreEvento());
    artista.setText(eventos.getArtista());
    fecha.setText(eventos.getFecha());
    ubicacion.setText(eventos.getUbicacion());
    //validez.setText(eventos.getValidez());
  }

  /**
   * Muestra una alerta de error cuando no se envía el correo.
   */
  private static void alertaErrorCorreo() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText("Correo electrónico no enviado. Por favor, inténtalo más tarde.");
    alert.showAndWait();
  }

  /**
   * Muestra una alerta confirmando que el correo se ha enviado.
   */
  private static void alertaEnviado() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Correo electrónico enviado");
    alert.setContentText("Comprueba tu bandeja de entrada.");
    alert.showAndWait();
  }


  /**
   * Inicializa el controlador.
   *
   * @param location  La ubicación utilizada para resolver rutas relativas del
   *                  objeto raíz, o null si no se conoce.
   * @param resources Los recursos utilizados para localizar el objeto raíz, o
   *                  null si no se conocen.
   */
  public void initialize(URL location, ResourceBundle resources) {
    validez.setEditable(true);
  }
}
