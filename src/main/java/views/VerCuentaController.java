package views;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import firebase.CRUDFirebase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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

public class VerCuentaController implements Initializable {

  public static BorderPane borderPane;

  private Stage stage;

  @FXML
  private TextField txtNombreUser;

  @FXML
  private Button btnAtras;

  @FXML
  private Button btnEditar;

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

  private Cuenta cuenta;

  /**
   * Método para establecer los datos de la cuenta.
   * 
   * @param cuentas La cuenta cuyos datos se van a mostrar.
   * @throws FileNotFoundException Si no se encuentra el archivo con los datos de
   *                               la cuenta.
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
   * Método para agregar provincias al ComboBox.
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
   * Maneja el evento de clic para editar la cuenta.
   * 
   * @param event El evento de clic del ratón.
   * @throws IOException Si ocurre un error al cargar la vista.
   */
  @FXML
  void editarCuenta(MouseEvent event) throws IOException {
    escenaEditarCuenta();
  }

  /**
   * Muestra la escena para editar la cuenta.
   * 
   * @throws IOException Si ocurre un error al cargar la vista.
   */
  private void escenaEditarCuenta() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditarCuentaView.fxml"));
    AnchorPane root = loader.load();
    EditarCuentaController editarCuenta = loader.getController();

    borderPane.setCenter(root);
  }

  /**
   * Inicializa el controlador. Este método se llama automáticamente después de
   * cargar el archivo FXML.
   * 
   * @param location  La ubicación utilizada para resolver rutas relativas para el
   *                  objeto raíz, o null si la ubicación no se conoce.
   * @param resources Los recursos utilizados para localizar el objeto raíz, o
   *                  null si no se han localizado recursos.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    txtNombreUser.setEditable(false);
    txtUser.setEditable(false);
    txtCorreo.setEditable(false);
    txtPassword.setEditable(false);
    ubicacion.setDisable(true);

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
   * Establece el Stage del controlador.
   * 
   * @param stage El Stage que se va a establecer.
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Establece el BorderPane del controlador.
   * 
   * @param borderPane El BorderPane que se va a establecer.
   */
  public void setBorderPane(BorderPane borderPane) {
    this.borderPane = borderPane;
  }
}
