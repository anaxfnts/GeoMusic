package views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import firebase.CRUDFirebase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Cuenta;
import utils.HashPassword;

public class EditarCuentaController implements Initializable {

  public static BorderPane borderPane;
  private Stage stage;

  @FXML
  private TextField txtNombreUser;

  @FXML
  private ComboBox<String> ubicacion;

  @FXML
  private TextField txtUser;

  @FXML
  private Button btnGuardar;

  @FXML
  private TextField txtCorreo;

  @FXML
  private PasswordField txtPassword;

  @FXML
  private PasswordField txtPasswordComprobado;

  private Cuenta cuenta;

  /**
   * Establece los datos de la cuenta en los campos correspondientes.
   *
   * @param cuentas La cuenta con los datos a establecer.
   * @throws FileNotFoundException Si ocurre un error al encontrar el archivo.
   */
  public void setDatos(Cuenta cuentas) throws FileNotFoundException {
    cuenta = cuentas;
    txtNombreUser.setText(cuentas.getNombre());
    txtUser.setText(cuentas.getUsuario());
    txtCorreo.setText(cuentas.getCorreo());
    txtPassword.setText(cuentas.getContrasenya());
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

  /**
   * Actualiza los datos de la cuenta en Firestore.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S.
   * @throws Exception   Si ocurre un error general.
   */
  @FXML
  void actualizarDatos(MouseEvent event) throws IOException, Exception {
    String documentoId = LoginController.mostrarNombreUsuario(); // Obtén el ID del documento
    if (documentoId == null || documentoId.isEmpty()) {
      System.out.println("ID del documento no puede ser nulo o vacío.");
      return;
    }

    // Verificar que las contraseñas coincidan
    String passwd = txtPassword.getText();
    String passwdComprobado = txtPasswordComprobado.getText();
    if (!passwd.equals(passwdComprobado)) {
      mostrarAlertaWarning("Las contraseñas no coinciden. Inténtalo de nuevo.");
      return;
    }

    // Crear un mapa con los datos a actualizar
    Map<String, String> datosActualizados = new HashMap<>();
    datosActualizados.put("password", HashPassword.convertirSHA256(passwd));
    datosActualizados.put("nombre", txtNombreUser.getText());
    datosActualizados.put("usuario", txtUser.getText());
    datosActualizados.put("ubicacion", ubicacion.getValue());
    datosActualizados.put("correo", txtCorreo.getText());

    // Actualizar todos los campos en Firestore
    boolean updateSuccess = true;
    for (Map.Entry<String, String> entry : datosActualizados.entrySet()) {
      boolean result = CRUDFirebase.actualizarEnFirebase(documentoId, entry.getKey(), entry.getValue());
      if (!result) {
        updateSuccess = false;
      }
    }

    if (updateSuccess) {
      mostrarAlertaInfo("Todos los campos fueron actualizados exitosamente.");
    } else {
      mostrarAlertaWarning("Ocurrió un error al actualizar uno o más campos.");
    }
  }
  

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
   * Inicializa el controlador.
   *
   * @param location  La ubicación utilizada para resolver rutas relativas del
   *                  objeto raíz, o null si no se conoce.
   * @param resources Los recursos utilizados para localizar el objeto raíz, o
   *                  null si no se conocen.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    txtNombreUser.setEditable(true);
    txtUser.setEditable(false);
    txtCorreo.setEditable(true);
    txtPassword.setEditable(true);
    txtPasswordComprobado.setEditable(true);
    ubicacion.setEditable(true);

    // Llama al método para agregar provincias al ComboBox.
    agregarProvincias();

    // Cargar datos de la cuenta del usuario.
    try {
      cuenta = CRUDFirebase.obtenerDatosCuenta(LoginController.mostrarNombreUsuario());
      if (cuenta != null) {
        setDatos(cuenta);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
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
