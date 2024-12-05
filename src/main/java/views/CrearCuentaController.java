package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import firebase.CRUDFirebase;
import firebase.ConexionFirebase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Cuenta;
import utils.HashPassword;

public class CrearCuentaController implements Initializable {

  public static AnchorPane root;
  private Stage stage;

  @FXML
  private TextField txtNombreUser;

  @FXML
  private Button btnAtras;

  @FXML
  private Button btnCancel;

  @FXML
  private ComboBox<String> ubicacion;

  @FXML
  private TextField txtUser;

  @FXML
  private Button btnCrear;

  @FXML
  private TextField txtCorreo;

  @FXML
  private PasswordField txtPassword;

  @FXML
  private PasswordField txtPasswordComprobado;

  /**
   * Agrega una lista de provincias de España al ComboBox de ubicaciones.
   */
  void agregarProvincias() {
    List<String> provincias = new ArrayList<>();
    provincias.add("Alava");
    provincias.add("Albacete");
    provincias.add("Alicante");
    provincias.add("Almería");
    provincias.add("Asturias");
    provincias.add("Avila");
    provincias.add("Badajoz");
    provincias.add("Barcelona");
    provincias.add("Burgos");
    provincias.add("Cáceres");
    provincias.add("Cádiz");
    provincias.add("Cantabria");
    provincias.add("Castellón");
    provincias.add("Ciudad Real");
    provincias.add("Córdoba");
    provincias.add("Cuenca");
    provincias.add("Gerona");
    provincias.add("Granada");
    provincias.add("Guadalajara");
    provincias.add("Guipúzcoa");
    provincias.add("Huelva");
    provincias.add("Huesca");
    provincias.add("Islas Baleares");
    provincias.add("Jaén");
    provincias.add("La Coruña");
    provincias.add("La Rioja");
    provincias.add("Las Palmas");
    provincias.add("León");
    provincias.add("Lérida");
    provincias.add("Lugo");
    provincias.add("Madrid");
    provincias.add("Málaga");
    provincias.add("Murcia");
    provincias.add("Navarra");
    provincias.add("Orense");
    provincias.add("Palencia");
    provincias.add("Pontevedra");
    provincias.add("Salamanca");
    provincias.add("Segovia");
    provincias.add("Sevilla");
    provincias.add("Soria");
    provincias.add("Tarragona");
    provincias.add("Santa Cruz de Tenerife");
    provincias.add("Teruel");
    provincias.add("Toledo");
    provincias.add("Valencia");
    provincias.add("Valladolid");
    provincias.add("Vizcaya");
    provincias.add("Zamora");
    provincias.add("Zaragoza");

    ubicacion.getItems().clear();
    ubicacion.getItems().addAll(provincias);
  }

  /**
   * Crea una cuenta nueva y la guarda en Firestore.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException          Si ocurre un error de E/S.
   * @throws InterruptedException Si ocurre una interrupción durante la operación.
   * @throws ExecutionException   Si ocurre un error en la ejecución.
   */
  @FXML
  void crearCuenta(MouseEvent event) throws IOException, InterruptedException, ExecutionException {
    String usuario = txtUser.getText();
    String usuarioConsultado = CRUDFirebase.consultarUsuario(usuario);
    String contrasenyaHash = HashPassword.convertirSHA256(txtPassword.getText());

    if (usuario.isEmpty()) {
      alertaVacio();
    } else if (!txtPassword.getText().equals(txtPasswordComprobado.getText())) {
      alertaNoCoinciden();
    } else {
      if (usuarioConsultado.equalsIgnoreCase(usuario)) {
        Firestore firestore = ConexionFirebase.getFirestore();
        DocumentReference docRef = firestore.collection("Cuentas").document(usuario);

        Cuenta cuenta = new Cuenta(usuario, txtNombreUser.getText(), txtUser.getText().trim(), contrasenyaHash,
            ubicacion.getValue(), txtCorreo.getText().trim(), "Normal", false);

        @SuppressWarnings("unused")
        WriteResult writeResult = docRef.set(cuenta).get();

        System.out.println("Cuenta añadida con ID: " + usuario);

        alertaCuentaCreada();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PreviaView.fxml"));
        Scene previaScene = new Scene(loader.load());

        // Configurar el controlador de la vista previa
        PreviaController previaController = loader.getController();
        previaController.setStage(stage); // Pasar el Stage al controlador anterior

        // Cambiar la escena del Stage actual a la vista previa
        stage.setScene(previaScene);
        stage.setMaximized(true);
        stage.setTitle("GeoMusic");

      }
    }
  }

  /**
   * Regresa a la vista anterior.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S.
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
   * Muestra una alerta de error.
   */
  public static void alertaError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText("Usuario incorrecto");
    alert.showAndWait();
  }

  /**
   * Muestra una alerta indicando que hay campos vacíos.
   */
  public static void alertaVacio() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setContentText("Campos vacíos");
    alert.showAndWait();
  }

  /**
   * Muestra una alerta indicando que las contraseñas no coinciden.
   */
  public static void alertaNoCoinciden() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Error");
    alert.setContentText("Las contraseñas no coinciden");
    alert.showAndWait();
  }

  /**
   * Muestra una alerta indicando que la cuenta ha sido creada.
   */
  public static void alertaCuentaCreada() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Cuenta creada");
    alert.setContentText("A continuación vas a iniciar sesión");
    alert.showAndWait();
  }

  /**
   * Establece la instancia de Stage para el controlador.
   *
   * @param primaryStage La instancia de Stage principal.
   */
  public void setStage(Stage primaryStage) {
    stage = primaryStage;
  }

  /**
   * Inicializa el controlador.
   *
   * @param location  La ubicación utilizada para resolver rutas relativas del
   *                  objeto raíz, o null si no se conoce.
   * @param resources Los recursos utilizados para localizar el objeto raíz, o
   *                  null si no se conocen.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ubicacion.showingProperty().addListener((obs, oldValue, newValue) -> {
      if (newValue) {
        agregarProvincias();
      }
    });
  }

}