package views;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import firebase.CRUDFirebase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Cuenta;
import utils.HashPassword;

public class LoginController implements Initializable {

  // Referencia al panel raíz.
  public static BorderPane root;

  // Referencia al escenario principal.
  private Stage stage;

  // Variable para almacenar la ubicación seleccionada.
  private String ubicacionSeleccionada;

  // Botón de cancelar.
  @FXML
  private Button btnCancel;

  // Botón de iniciar sesión.
  @FXML
  private Button btnLogin;

  // Botón de ir atrás.
  @FXML
  private Button btnAtras;

  // Campo de contraseña.
  @FXML
  private PasswordField txtPassword;

  // Campo de usuario.
  @FXML
  private TextField txtUser;

  // Label de tipo.
  @FXML
  private Label txtTipo;

  // Variable para cuenta la cuenta.
  public static Cuenta comprobar;

  // Variable para almacenar el correo del usuario logueado.
  public static String loggedInUserMail = "";

  /**
   * Realiza y comprueba el login de la cuenta.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   * @throws Exception   Si ocurre algún otro error durante el login.
   */
  @FXML
  void logeo(MouseEvent event) throws IOException, Exception {
      String usuario = txtUser.getText();
      String passwd = txtPassword.getText();
      boolean registrado;
      String hasPass = HashPassword.convertirSHA256(passwd);
      String usuarioConsultado = CRUDFirebase.consultarUsuario(usuario);
      String passwdConsultado = CRUDFirebase.consultarPasswd(usuario);

      try {
          if (usuarioConsultado.equalsIgnoreCase(usuario) && passwdConsultado.equalsIgnoreCase(hasPass)) {
              registrado = true;
              comprobar = CRUDFirebase.obtenerDatosCuenta(usuario);
              loggedInUserMail = CRUDFirebase.consultarCorreo(usuario);

              String tipoUsuario = comprobar.getTipo();
              String fxmlFile;

              if ("Administrador".equalsIgnoreCase(tipoUsuario)) {
                  fxmlFile = "/views/PrincipalAdminView.fxml";
              } else {
                  fxmlFile = "/views/PrincipalView.fxml";
              }

              FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
              BorderPane root = loader.load();

              // Crear un nuevo Stage y configurar estilo antes de mostrarlo
              Stage nuevoStage = new Stage();
              nuevoStage.initStyle(StageStyle.UNDECORATED); // Configurar estilo aquí
              nuevoStage.setResizable(true);
              nuevoStage.setMinWidth(800);
              nuevoStage.setMinHeight(600);
              nuevoStage.setScene(new Scene(root));
              Platform.runLater(() -> nuevoStage.setMaximized(true));
              nuevoStage.setTitle("GeoMusic");
              nuevoStage.show();

              // Pasar el Stage al controlador correspondiente
              if ("Administrador".equalsIgnoreCase(tipoUsuario)) {
                  PrincipalAdminController adminControl = loader.getController();
                  adminControl.initLogin(nuevoStage, this, comprobar.getNombre(), root, tipoUsuario);
              } else {
                  PrincipalController userControl = loader.getController();
                  userControl.initLogin(nuevoStage, this, comprobar.getNombre(), root, tipoUsuario);
              }

              // Cerrar el Stage actual del login
              if (this.stage != null) {
                  this.stage.close();
              }

          } else if (usuario.isEmpty() || passwd.isEmpty()) {
              registrado = false;
              alertaVacio();

          } else {
              registrado = false;
              alertaError();
          }

      } catch (NullPointerException e) {
          alertaError();
      }
  }


  /**
   * Vuelve a la pantalla previa de la aplicación.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void atras(MouseEvent event) throws IOException {
    if (stage == null) {
      // Manejar caso en que el Stage no esté inicializado
      System.err.println("Stage no inicializado.");
      return;
    }

    // Cargar la vista de previa
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PreviaView.fxml"));
    Scene previaScene = new Scene(loader.load());

    // Configurar el controlador de la vista previa
    PreviaController previaController = loader.getController();
    previaController.setStage(stage); // Pasar el Stage al controlador anterior

    // Cambiar la escena del Stage actual a la vista previa
    stage.setScene(previaScene);
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
   * Muestra una alerta de error cuando el usuario no existe.
   */
  public static void alertaError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText("Usuario no existe: comprueba tu usuario y tu contraseña.");
    alert.showAndWait();
  }

  /**
   * Muestra una alerta de error cuando la aplicación no está disponible.
   */
  public static void alertaFalloApp() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText("App no disponible");
    alert.showAndWait();
  }

  /**
   * Muestra una alerta de campos vacíos.
   */
  public static void alertaVacio() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setContentText("Campos vacíos");
    alert.showAndWait();
  }

  /**
   * Muestra la ubicación predeterminada del usuario.
   *
   * @return La ubicación predeterminada del usuario.
   */
  public static String mostrarUbicacionPredeteminada() {
    String ubicacionPredeterminada = CRUDFirebase.consultarUbicacion(comprobar.getUsuario());
    return ubicacionPredeterminada;
  }

  /**
   * Muestra el nombre del usuario.
   *
   * @return El nombre del usuario.
   */
  public static String mostrarNombreUsuario() {
    String usuario = CRUDFirebase.consultarUsuario(comprobar.getUsuario());
    return usuario;
  }

  /**
   * Establece el escenario principal.
   *
   * @param primaryStage El escenario principal de la aplicación.
   */
  public void setStage(Stage primaryStage) {
    stage = primaryStage;
  }

  /**
   * Inicializa el controlador.
   *
   * @param location  La ubicación utilizada para resolver rutas relativas para el
   *                  objeto raíz o nulo si la ubicación no es conocida.
   * @param resources Los recursos utilizados para localizar el objeto raíz o nulo
   *                  si los recursos no son conocidos.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Inicialización necesaria
  }
}