package views;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PreviaController {

  // Referencia al panel raíz.
  public static BorderPane root;

  // Referencia al escenario principal.
  private Stage stage;

  // Botón de cancelar.
  @FXML
  private Button btnCancel;

  // Botón de iniciar sesión.
  @FXML
  private Button btnLogin;

  // Botón de crear cuenta.
  @FXML
  private Button btnCrearCuenta;

  // Icono de Facebook.
  @FXML
  private ImageView facebook;

  // Icono de Instagram.
  @FXML
  private ImageView instagram;

  // Icono de Twitter.
  @FXML
  private ImageView twitter;

  /**
   * Maneja el evento de clic para iniciar sesión.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void logeo(MouseEvent event) throws IOException {
      if (stage == null) {
          // Manejar caso en que el Stage no esté inicializado
          System.err.println("Stage no inicializado.");
          return;
      }

      // Cargar la vista de login
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
      Scene loginScene = new Scene(loader.load());

      // Configurar el controlador de la vista de login
      LoginController loginController = loader.getController();
      loginController.setStage(stage); // Pasar el Stage al siguiente controlador

      // Cambiar la escena del Stage actual a la vista de login
      stage.setScene(loginScene);
  }
  /**
   * Maneja el evento de clic para crear cuenta.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void crearCuenta(MouseEvent event) throws IOException {
    if (stage == null) {
      // Manejar caso en que el Stage no esté inicializado
      System.err.println("Stage no inicializado.");
      return;
  }

  // Cargar la vista de login
  FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CrearCuentaView.fxml"));
  Scene cuentaScene = new Scene(loader.load());

  // Configurar el controlador de la vista de login
  CrearCuentaController crearCuenta = loader.getController();
  crearCuenta.setStage(stage); // Pasar el Stage al siguiente controlador

  // Cambiar la escena del Stage actual a la vista de login
  stage.setScene(cuentaScene);
  }

  /**
   * Sale de la aplicación.
   *
   * @param event El evento de ratón que dispara el método.
   */
  @FXML
  void salir(MouseEvent event) {
    Platform.exit();
  }

  /**
   * Método para abrir la web de Facebook.
   *
   * @param event El evento de ratón que dispara el método.
   */
  @FXML
  void webFacebook(MouseEvent event) {
    URL url = null;
    try {
      url = new URL("https://es-es.facebook.com/");
      try {
        Desktop.getDesktop().browse(url.toURI());
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Método para abrir la web de Instagram.
   *
   * @param event El evento de ratón que dispara el método.
   */
  @FXML
  void webInstagram(MouseEvent event) {
    URL url = null;
    try {
      url = new URL("http://instagram.com/");
      try {
        Desktop.getDesktop().browse(url.toURI());
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Método para abrir la web de Twitter.
   *
   * @param event El evento de ratón que dispara el método.
   */
  @FXML
  void webTwitter(MouseEvent event) {
    URL url = null;
    try {
      url = new URL("https://twitter.com/?lang=es");
      try {
        Desktop.getDesktop().browse(url.toURI());
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Muestra una alerta de error.
   */
  public static void alertaError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText("Usuario incorrecto");
    alert.showAndWait();
  }

  /**
   * Establece el escenario principal.
   *
   * @param primaryStage El escenario principal de la aplicación.
   */
  public void setStage(Stage primaryStage) {
    stage = primaryStage;
  }
}