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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Emails;
import models.Evento;

public class EventoController {

  private Evento evento = null;
  private String correoUsuario;

  @FXML
  private Label lblNombreEvento;

  @FXML
  private Label lblArtista;

  @FXML
  private Label lblDescripcion;

  @FXML
  private TextField txtFecha;

  @FXML
  private TextField txtUbicacion;

  @FXML
  private Button favButton;

  @FXML
  private Button reviewButton;

  @FXML
  private ImageView img;

  @FXML
  private Rectangle cuadrado;

  /**
   * Establece los datos del evento en los campos correspondientes.
   *
   * @param eventos El evento con los datos a establecer.
   * @throws FileNotFoundException Si ocurre un error al encontrar el archivo de
   *                               imagen.
   */
  public void setDatos(Evento eventos) {
    evento = eventos;
    lblNombreEvento.setText(eventos.getNombreEvento());
    lblDescripcion.setText(eventos.getDescripcion());
    lblArtista.setText(eventos.getArtista());
    txtFecha.setText(eventos.getFecha());
    txtFecha.setEditable(false);
    txtUbicacion.setText(eventos.getUbicacion());
    txtUbicacion.setEditable(false);


    try {
        // Intentar cargar la imagen localmente
        String rutaLocal = "imagenes/" + eventos.getImagenEvento();
        Image imagen = new Image(new FileInputStream(rutaLocal));
        cuadrado.setFill(new ImagePattern(imagen));
    } catch (FileNotFoundException e) {
        // Si la imagen local no está disponible, cargarla desde la URL remota
        System.out.println("Imagen local no encontrada, cargando desde URL remota.");
        try {
            Image imagenRemota = new Image(eventos.getImagenEvento());
            cuadrado.setFill(new ImagePattern(imagenRemota));
        } catch (Exception ex) {
            System.err.println("Error al cargar la imagen remota: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
  }

  /**
   * Envía un email al cliente con información sobre el evento.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException        Si ocurre un error de E/S.
   * @throws MessagingException Si ocurre un error al enviar el mensaje.
   */
  @FXML
  void enviarEmailCliente(MouseEvent event) throws IOException, MessagingException {
    // Obtener el nombre de usuario
    correoUsuario = LoginController.mostrarNombreUsuario();
    System.out.println("Correo usuario obtenido: " + correoUsuario);

    // Verificar si el usuario es nulo
    if (correoUsuario == null) {
      System.err.println("Usuario no inicializado.");
      alertaErrorCorreo();
      return;
    }

    // Consultar el correo del usuario
    String correo = CRUDFirebase.consultarCorreo(correoUsuario);
    System.out.println("Correo obtenido de Firebase: " + correo);

    // Verificar si el correo es nulo
    if (correo == null || correo.isEmpty()) {
      System.err.println("Correo no encontrado para el usuario: " + correoUsuario);
      alertaErrorCorreo();
      return;
    }

    try {
      // Enviar el email
      Emails e = new Emails();
      e.enviar(correo, "Evento marcado como Favorito: " + evento.getNombreEvento(),
          "Estimado usuario, \n" + "Aquí está toda la información resumida acerca del evento: \n\n" + "Artista: "
              + evento.getArtista() + "\n" + "Fecha: " + evento.getFecha() + "\n" + "Ubicación: "
              + evento.getUbicacion() + "\n" + "Descripción: " + evento.getDescripcion() + "\n\n"
              + "¡Esperamos que disfrutes del evento!");
      alertaEnviado();
    } catch (Exception e) {
      System.err.println("Error enviando el correo: " + e.getMessage());
      e.printStackTrace();
      alertaErrorCorreo();
    }
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
   * Abre la pantalla de reseñas para el evento actual.
   *
   * @param event El evento de ratón que dispara el método.
   */
  public void openReviewScreen(MouseEvent event) {
    if (evento != null) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewScreen.fxml"));
        Parent root = loader.load();

        ReviewController reviewController = loader.getController();
        reviewController.loadReviews(evento.getNombreEvento());

        Stage stage = new Stage();
        stage.setTitle("Reviews publicadas:");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/images/logo.PNG"));
        stage.show();
      } catch (Exception ex) {
        System.err.println("Hubo un error al abrir la ventana: " + ex.getMessage());
        ex.printStackTrace();
      }
    } else {
      System.err.println("Evento no inicializado.");
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
  public void initialize(URL location, ResourceBundle resources) {
    txtFecha.setEditable(false);
    txtUbicacion.setEditable(false);
  }
}
