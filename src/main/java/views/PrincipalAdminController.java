package views;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Evento;

public class PrincipalAdminController {

  // Referencia al escenario principal.
  private Stage stage;

  // Panel principal de la interfaz.
  private BorderPane borderPane;

  // Controlador del login.
  @SuppressWarnings("unused")
  private LoginController controLogin;

  // Controlador del login.
  @SuppressWarnings("unused")
  private VerCuentaController verCuenta;

  // Controlador de la creación de cuenta.
  @SuppressWarnings("unused")
  private CrearCuentaController crearCuenta;

  @SuppressWarnings("unused")
  private EditarCuentaController editarCuenta;

  @SuppressWarnings("unused")
  private AddEventoController addEvento;

  // Lista de eventos.
  @SuppressWarnings("unused")
  private List<Evento> eventos;

  // Etiqueta para mostrar el nombre del usuario.
  @FXML
  private Label lblNombre;

  // Etiqueta para mostrar el tipo de cuenta del usuario.
  @FXML
  private Label lblTipoCuenta;

  // Círculo para la imagen de perfil.
  @FXML
  private Circle circulo;

  // Imagen que para mostrar una imagen.
  @FXML
  private ImageView img;

  // Botón para salir de la aplicación.
  @FXML
  private Button btnSalir;

  // ComboBox para seleccionar la ubicación.
  @FXML
  private ComboBox<String> ubicacion;

  // Botón para ver la cuenta del usuario.
  @FXML
  private Button btnVerCuenta;

//Botón para ver la cuenta del usuario.
  @FXML
  private Button btnVerAPI;

  // Botón para ver la cuenta del usuario.
  @FXML
  private Button btnAddEvento;

//Botón para ver los eventos
  @FXML
  private Button btnVerEventos;
  

  /**
   * Inicializa la interfaz con el controlador de login.
   *
   * @param stage           El escenario principal de la aplicación.
   * @param loginController El controlador del login.
   * @param nombre          El nombre del usuario.
   * @param root            El panel principal de la interfaz.
   * @param tipoCuenta      El tipo de cuenta del usuario.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  void initLogin(Stage stage, LoginController loginController, String nombre, BorderPane root, String tipoCuenta)
      throws IOException {
  this.stage = stage;
  this.controLogin = loginController;
  lblNombre.setText(nombre);
  lblTipoCuenta.setText(tipoCuenta);
  this.borderPane = root;

  // Elimina la línea que configura initStyle (ya debe configurarse antes de mostrar el Stage)
  // stage.initStyle(StageStyle.UNDECORATED); // NO necesario aquí

  stage.setMaximized(true);
  stage.setResizable(false);
  stage.getIcons().add(new Image("/images/logo.PNG"));
  stage.setTitle("GeoMusic");

  // Asume que el Stage ya ha sido mostrado desde LoginController
  escenaEventos();
}


  /**
   * Inicializa la interfaz con el controlador de ver cuenta.
   *
   * @param stage               El escenario principal de la aplicación.
   * @param verCuentaController El controlador de ver cuenta.
   * @param nombre              El nombre del usuario.
   * @param root                El panel principal de la interfaz.
   * @param tipoCuenta          El tipo de cuenta del usuario.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  void initVerCuenta(Stage stage, VerCuentaController verCuentaController, String nombre, BorderPane root,
      String tipoCuenta) throws IOException {
    this.stage = stage;
    this.verCuenta = verCuentaController;
    this.borderPane = root;

    // Comprobaciones para asegurar que los elementos FXML se han inicializado
    if (lblNombre == null || lblTipoCuenta == null || borderPane == null) {
      System.out.println("Error: Los componentes FXML no se han cargado correctamente en PrincipalAdminController.");
      return; // Detener si hay elementos sin inicializar
    }

    lblNombre.setText(nombre);
    lblTipoCuenta.setText(tipoCuenta);

    // Configuración adicional del stage
    stage.initStyle(StageStyle.UNDECORATED);
    stage.setMaximized(true);
    stage.setResizable(false);
    stage.getIcons().add(new Image("/images/logo.PNG"));
    stage.setTitle("GeoMusic");

    stage.show();
    escenaEventos();
  }

  /**
   * Inicializa la interfaz con el controlador de editar cuenta.
   *
   * @param stage                  El escenario principal de la aplicación.
   * @param editarCuentaController El controlador de editar cuenta.
   * @param nombre                 El nombre del usuario.
   * @param root                   El panel principal de la interfaz.
   * @param tipoCuenta             El tipo de cuenta del usuario.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  void initEditarCuenta(Stage stage, EditarCuentaController editarCuentaController, String nombre, BorderPane root,
      String tipoCuenta) throws IOException {
    this.stage = stage;
    this.editarCuenta = editarCuentaController;
    lblNombre.setText(nombre);
    lblTipoCuenta.setText(tipoCuenta);
    this.borderPane = root;

    stage.initStyle(StageStyle.UNDECORATED); // Quita la barra de título
    stage.setMaximized(true);
    stage.setResizable(false);
    stage.getIcons().add(new Image("/images/logo.PNG"));
    stage.setTitle("GeoMusic");

    stage.show();
    escenaEventos();
  }

  /**
   * Inicializa la interfaz con el controlador de creación de cuenta.
   *
   * @param stage           El escenario principal de la aplicación.
   * @param crearController El controlador de creación de cuenta.
   * @param nombre          El nombre del usuario.
   * @param border          El panel principal de la interfaz.
   * @param tipoCuenta      El tipo de cuenta del usuario.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  void initCrearCuenta(Stage stage, CrearCuentaController crearController, String nombre, BorderPane border,
      String tipoCuenta) throws IOException {
    this.stage = stage;
    this.crearCuenta = crearController;
    lblNombre.setText(nombre);
    lblTipoCuenta.setText(tipoCuenta);
    this.borderPane = border;

    stage.initStyle(StageStyle.UNDECORATED); // Quita la barra de título
    stage.setMaximized(true);
    stage.setResizable(false);
    stage.getIcons().add(new Image("/images/logo.PNG"));
    stage.setTitle("GeoMusic");

    stage.show();
    escenaEventos();
  }

  void initAddEvento(Stage stage, AddEventoController addEventoController, String nombre, BorderPane root,
      String tipoCuenta) throws IOException {
    this.stage = stage;
    this.addEvento = addEventoController;
    lblNombre.setText(nombre);
    lblTipoCuenta.setText(tipoCuenta);
    this.borderPane = root;

    stage.initStyle(StageStyle.UNDECORATED); // Quita la barra de título
    stage.setMaximized(true);
    stage.setResizable(false);
    stage.getIcons().add(new Image("/images/logo.PNG"));
    stage.setTitle("GeoMusic");

    stage.show();
    escenaEventos();
  }

  /**
   * Carga y muestra la escena de eventos.
   *
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  public void escenaEventos() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VerEventosAdmin.fxml"));
    AnchorPane root = loader.load();
    VerEventosAdminController verEventosController = loader.getController();

    borderPane.setCenter(root);
  }

  /**
   * Maneja el evento de clic para ver eventos.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void vistaEventos(MouseEvent event) throws IOException {
    escenaEventos();
    borderPane.setLeft(null);
  }

  /**
   * Maneja el evento de clic para salir de la aplicación.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void salir(MouseEvent event) throws IOException {
    escenaLogin();
  }

  /**
   * Cierra sesión y vuelve a mostrar el login.
   *
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  private void escenaLogin() throws IOException {
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
   * Maneja el evento de clic para ver la cuenta del usuario.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void verCuenta(MouseEvent event) throws IOException {
    escenaVerCuenta();
  }

  /**
   * Carga y muestra la escena de ver cuenta.
   *
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  private void escenaVerCuenta() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VerCuentaView.fxml"));
    AnchorPane root = loader.load();
    VerCuentaController verCuenta = loader.getController();

    // Establece borderPane en VerCuentaController
    verCuenta.setBorderPane(borderPane);

    borderPane.setCenter(root);
  }

  /**
   * Carga y muestra la escena de eventos.
   *
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  public void escenaAPI() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VerAPI.fxml"));
    AnchorPane root = loader.load();
    VerAPIController verAPIController = loader.getController();

    borderPane.setCenter(root);
  }

  /**
   * Maneja el evento de clic para ver eventos.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void vistaAPI(MouseEvent event) throws IOException {
    escenaAPI();
    borderPane.setLeft(null);
  }

  /**
   * Maneja el evento de clic para ver la escena para añadir un evento.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void addEvento(MouseEvent event) throws IOException {
    escenaAddEvento();
  }

  /**
   * Carga y muestra la escena de Add Evento.
   *
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  private void escenaAddEvento() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddEvento.fxml"));
    AnchorPane root = loader.load();
    AddEventoController addEventoController = loader.getController();

    borderPane.setCenter(root);
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
   * Establece la lista de eventos.
   *
   * @param listaEventos La lista de eventos.
   */
  public void setEventos(List<Evento> listaEventos) {
    this.eventos = listaEventos;
  }
}
