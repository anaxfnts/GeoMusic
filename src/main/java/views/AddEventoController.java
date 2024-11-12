package views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import firebase.CRUDFirebase;
import firebase.ConexionFirebase;
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
import javafx.stage.StageStyle;
import models.Cuenta;
import utils.HashPassword;

public class AddEventoController implements Initializable {

  public static BorderPane borderPane;
  private Stage stage;

  @FXML
  private TextField txtNombreEvento;

  @FXML
  private Button btnAtras;

  @FXML
  private Button btnCancel;

  @FXML
  private ComboBox<String> ubicacion;

  @FXML
  private TextField txtFecha;

  @FXML
  private Button btnGuardar;

  @FXML
  private TextField txtArtista;

  @FXML
  private TextField txtDescripcion;

  private Cuenta cuenta;

  /**
   * Establece los datos de la cuenta en los campos correspondientes.
   *
   * @param cuentas La cuenta con los datos a establecer.
   * @throws FileNotFoundException Si ocurre un error al encontrar el archivo.
   */
  public void setDatos(Cuenta cuentas) throws FileNotFoundException {
    cuenta = cuentas;
    txtNombreEvento.setText(cuentas.getNombre());
    txtFecha.setText(cuentas.getUsuario());
    txtArtista.setText(cuentas.getCorreo());
    txtDescripcion.setText(cuentas.getContrasenya());
    ubicacion.setValue(cuentas.getUbicacion());
  }

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

//  /**
//   * Añade los datos del evento en Firestore.
//   *
//   * @param event El evento de ratón que dispara el método.
//   * @throws IOException Si ocurre un error de E/S.
//   * @throws Exception   Si ocurre un error general.
//   */
//  @FXML
//  void addEvento(MouseEvent event) throws IOException, InterruptedException, ExecutionException {
//    String usuario = txtUser.getText();
//    String usuarioConsultado = CRUDFirebase.consultarUsuario(usuario);
//    String contrasenyaHash = HashPassword.convertirSHA256(txtPassword.getText());
//
//    if (usuario.isEmpty()) {
//      alertaVacio();
//    } else if (!txtPassword.getText().equals(txtPasswordComprobado.getText())) {
//      alertaNoCoinciden();
//    } else {
//      if (usuarioConsultado.equalsIgnoreCase(usuario)) {
//        Firestore firestore = ConexionFirebase.getFirestore();
//        DocumentReference docRef = firestore.collection("Eventos").document(usuario);
//
//        Cuenta cuenta = new Cuenta(usuario, txtNombreUser.getText(), txtUser.getText().trim(), contrasenyaHash,
//            ubicacion.getValue(), txtCorreo.getText().trim(), "Normal", false);
//
//        @SuppressWarnings("unused")
//        WriteResult writeResult = docRef.set(cuenta).get();
//
//        System.out.println("Cuenta añadida con ID: " + usuario);
//
//        alertaCuentaCreada();
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PreviaView.fxml"));
//        AnchorPane root = loader.load();
//        Scene escena = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(escena);
//        stage.setMaximized(true);
//        stage.setResizable(false);
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.show();
//        if (this.stage != null) {
//          this.stage.close();
//        }
//      }
//    }
//  }

  /**
   * Muestra una alerta de advertencia.
   *
   * @param mensaje El mensaje de advertencia a mostrar.
   */
  private void mostrarAlertaWarning(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Advertencia");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  /**
   * Muestra una alerta de información.
   *
   * @param mensaje El mensaje de información a mostrar.
   */
  private void mostrarAlertaInfo(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Información");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  /**
   * Maneja el evento de clic para volver atrás.
   * 
   * @param event El evento de clic del ratón.
   * @throws IOException Si ocurre un error al cargar la vista.
   */
  @FXML
  void atras(MouseEvent event) throws IOException {
    String usuario = LoginController.mostrarNombreUsuario();
    cuenta = CRUDFirebase.obtenerDatosCuenta(usuario);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PrincipalAdminView.fxml"));
    BorderPane newBorderPane = loader.load(); // Cargar el nuevo BorderPane
    PrincipalAdminController control = loader.getController();
    Scene escena = new Scene(newBorderPane); // Crear una nueva escena con el nuevo BorderPane
    Stage newStage = new Stage();
    newStage.initStyle(StageStyle.UNDECORATED); // Aquí se quita la barra de título
    newStage.setScene(escena);
    control.initAddEvento(newStage, this, cuenta.getNombre(), newBorderPane, cuenta.getTipo());
    newStage.show();
    if (this.stage != null) {
      this.stage.close();
    }
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
    txtNombreEvento.setEditable(true);
    txtFecha.setEditable(false);
    txtArtista.setEditable(true);
    txtDescripcion.setEditable(true);
    ubicacion.setEditable(true);

    // Llama al método para agregar provincias al ComboBox.
    agregarProvincias();

  }

  /**
   * Establece la instancia de Stage para el controlador.
   *
   * @param stage La instancia de Stage.
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Establece la instancia de BorderPane para el controlador.
   *
   * @param borderPane La instancia de BorderPane.
   */
  public void setBorderPane(BorderPane borderPane) {
    this.borderPane = borderPane;
  }
}
