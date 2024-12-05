package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import firebase.CRUDFirebase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Evento;
import utils.GridPaneEventos;

public class VerEventosController implements Initializable {

  // GridPaneEventos es una clase personalizada para manejar la disposición de
  // eventos en una cuadrícula.
  private GridPaneEventos grid = new GridPaneEventos();

  // Listas estáticas para almacenar eventos y paneles de anclaje.
  private static List<Evento> listaEventos = new ArrayList<>();
  private static List<AnchorPane> paneles = new ArrayList<>();

  // GridPane para organizar los paneles de eventos.
  private GridPane nuevoGrid;

  // Cadena para almacenar la ubicación predeterminada.
  private String ubicacionPredeterminada;

  // FXML variables que representan componentes en la interfaz gráfica.
  @FXML
  private BorderPane borderPaneEventos;

  @FXML
  private ComboBox<String> ubicacion;

  /**
   * Método initialize que se llama automáticamente al cargar la vista.
   * 
   * @param location  URL de localización del recurso.
   * @param resources Conjunto de recursos para internacionalización.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    int fila = 0;
    int columna = 0;

    // Listener para cambios en el valor del ComboBox de ubicación.
    ubicacion.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
          // Actualiza los eventos mostrados según la nueva ubicación seleccionada.
          actualizarSegunUbicacion(newValue);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    // Listener para cuando el ComboBox se muestra.
    ubicacion.showingProperty().addListener((obs, oldValue, newValue) -> {
      if (newValue) {
        // Agrega las provincias al ComboBox.
        agregarProvincias();
      }
    });

    try {
      // Inicializa las provincias en el ComboBox.
      agregarProvincias();
      // Obtiene la ubicación predeterminada desde el controlador de login.
      ubicacionPredeterminada = LoginController.mostrarUbicacionPredeteminada();
      // Establece el valor del ComboBox en la ubicación predeterminada.
      ubicacion.setValue(ubicacionPredeterminada);
      // Consulta los eventos para la ubicación predeterminada.
      listaEventos = CRUDFirebase.consultarEventosDisponibles(ubicacionPredeterminada);
      // Crea los paneles para cada evento.
      paneles = grid.crearPaneles(listaEventos);
      // Crea el GridPane con los paneles de eventos.
      nuevoGrid = grid.crearGridPane(columna, fila, paneles);
      // Establece el nuevo GridPane en el centro del BorderPane.
      borderPaneEventos.setCenter(nuevoGrid);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Método para agregar las provincias al ComboBox.
   */
  void agregarProvincias() {
    // Lista de provincias de España.
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

    // Limpia los elementos actuales del ComboBox.
    ubicacion.getItems().clear();

    // Agrega todas las provincias al ComboBox.
    ubicacion.getItems().addAll(provincias);
  }

  /**
   * Método para limpiar las listas de eventos y paneles.
   */
  private static void limpiarListas() {
    listaEventos.clear();
    paneles.clear();
  }

  /**
   * Método para obtener la lista de eventos.
   * 
   * @return List<Evento> Lista de eventos.
   */
  public List<Evento> getListaEventos() {
    return listaEventos;
  }

  /**
   * Método para actualizar los eventos según la ubicación seleccionada.
   *
   * @param ubicacionSeleccionada La ubicación seleccionada para filtrar los eventos.
   * @throws IOException Si ocurre un error al consultar los eventos.
   */
  public void actualizarSegunUbicacion(String ubicacionSeleccionada) throws IOException {
    try {
        // Limpia las listas de eventos y paneles.
        limpiarListas();

        // Consulta solo los eventos disponibles para la ubicación seleccionada.
        listaEventos = CRUDFirebase.consultarEventosDisponibles(ubicacionSeleccionada);

        // Limpia los hijos del BorderPane.
        borderPaneEventos.getChildren().clear();
        paneles.clear();

        // Crea los paneles para cada evento filtrado.
        paneles = grid.crearPaneles(listaEventos);

        // Crea el GridPane con los paneles de eventos.
        nuevoGrid = grid.crearGridPane(0, 0, paneles);

        // Establece el nuevo GridPane en el centro del BorderPane.
        borderPaneEventos.setCenter(nuevoGrid);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
