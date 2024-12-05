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
import firebase.FirebaseUploader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import models.Evento;
import utils.HashPassword;

public class AddEventoController implements Initializable {

  public static BorderPane borderPane;
  private Stage stage;

  @FXML
  private TextField txtNombreEvento;

  @FXML
  private ComboBox<String> ubicacion;

  @FXML
  private TextField txtFecha;

  @FXML
  private Button btnGuardar;

  @FXML
  private TextField txtArtista;
  
  @FXML
  private TextField txtImagenURL;

  @FXML
  private TextField txtDescripcion;
  
  @FXML
  private CheckBox checkBoxDisponible;


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

  /**
   * Crea un evento nuevo y lo guarda en Firestore.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException          Si ocurre un error de E/S.
   * @throws InterruptedException Si ocurre una interrupción durante la operación.
   * @throws ExecutionException   Si ocurre un error en la ejecución.
   */
  @FXML
  void crearEvento(MouseEvent event) throws IOException {
      // Obtener datos del formulario
      String nombreEvento = txtNombreEvento.getText().trim();
      String descripcion = txtDescripcion.getText().trim();
      String fecha = txtFecha.getText().trim(); // Fecha en formato "DD-MM-YYYY"
      String imagenEvento = txtImagenURL.getText().trim(); // Puede ser una URL o un identificador
      String artista = txtArtista.getText().trim();
      String ubicacionEvento = ubicacion.getValue(); // ComboBox para ubicaciones
      boolean disponible = checkBoxDisponible.isSelected(); // Valor del CheckBox

      // Validar datos requeridos
      if (nombreEvento.isEmpty() || descripcion.isEmpty() || fecha.isEmpty() || artista.isEmpty() || ubicacionEvento == null) {
          mostrarAlertaWarning("Todos los campos son obligatorios. Por favor, complétalos.");
          return;
      }

      // Validar el formato de la fecha
      if (!fecha.matches("\\d{2}-\\d{2}-\\d{4}")) { // Formato DD-MM-YYYY
          mostrarAlertaInfo("La fecha no tiene el formato correcto. Usa el formato DD-MM-YYYY.");
          return;
      }

      // Instanciar la clase FirebaseUploader para utilizar sus métodos
      FirebaseUploader uploader = new FirebaseUploader();

      // Normalizar y generar el ID del documento
      String ciudadNormalizada = uploader.normalizarCiudad(ubicacionEvento);
      String artistaNormalizado = uploader.normalizarArtista(artista);
      String idDocumento = ciudadNormalizada + artistaNormalizado + fecha;

      // Crear el objeto Evento
      Evento nuevoEvento = new Evento(
          nombreEvento,
          descripcion,
          fecha,
          imagenEvento,
          artista,
          ubicacionEvento,
          disponible // Asignar el valor del CheckBox
      );

      // Subir el evento a Firebase usando el método de FirebaseUploader
      uploader.subirEventoAFirebase(nuevoEvento, idDocumento);

      // Mostrar alerta de éxito
      mostrarAlertaInfo("Evento creado exitosamente con ID: " + idDocumento);
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
    txtNombreEvento.setEditable(true);
    txtFecha.setEditable(true);
    txtImagenURL.setEditable(true);
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
