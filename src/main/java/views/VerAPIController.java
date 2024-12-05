package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import firebase.CRUDFirebase;
import firebase.FirebaseUploader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Evento;
import utils.GridPaneAPITable;

public class VerAPIController implements Initializable {

    // GridPaneAPITable es una clase personalizada para manejar la disposición de eventos en una cuadrícula.
    private GridPaneAPITable grid = new GridPaneAPITable();

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

    @FXML
    private Button btnGuardar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Listener para cambios en el valor del ComboBox de ubicación.
        ubicacion.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    actualizarSegunUbicacion(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Listener para cuando el ComboBox se muestra.
        ubicacion.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                agregarProvincias();
            }
        });

        try {
            agregarProvincias();
            ubicacionPredeterminada = LoginController.mostrarUbicacionPredeteminada();
            ubicacion.setValue(ubicacionPredeterminada);
            listaEventos = CRUDFirebase.consultarEventos(ubicacionPredeterminada);
            paneles = grid.crearPaneles(listaEventos);
            nuevoGrid = grid.crearGridPane(paneles);
            borderPaneEventos.setCenter(nuevoGrid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para agregar las provincias al ComboBox.
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

    // Método para limpiar las listas de eventos y paneles.
    private static void limpiarListas() {
        listaEventos.clear();
        paneles.clear();
    }

    public void actualizarSegunUbicacion(String ubicacionSeleccionada) throws IOException {
        try {
            limpiarListas();
            listaEventos = CRUDFirebase.consultarEventos(ubicacionSeleccionada);

            borderPaneEventos.setCenter(null);
            paneles = grid.crearPaneles(listaEventos);
            nuevoGrid = grid.crearGridPane(paneles);
            borderPaneEventos.setCenter(nuevoGrid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Actualiza el campo "disponible" de todos los eventos en la ubicación seleccionada en Firestore.
     *
     * @param event El evento de ratón que dispara el método.
     * @throws IOException Si ocurre un error de E/S.
     * @throws Exception   Si ocurre un error general.
     */
    @FXML
    void actualizarDisponibilidad(MouseEvent event) throws IOException, Exception {
        String ubicacionSeleccionada = obtenerUbicacionSeleccionada();
        if (ubicacionSeleccionada == null || ubicacionSeleccionada.isEmpty()) {
            mostrarAlertaWarning("La ubicación seleccionada no puede ser nula o vacía.");
            return;
        }

        // Obtener eventos desde Firestore para la ubicación seleccionada.
        List<Evento> eventos = CRUDFirebase.consultarEventos(ubicacionSeleccionada);
        if (eventos == null || eventos.isEmpty()) {
            mostrarAlertaWarning("No se encontraron eventos en la ubicación seleccionada.");
            return;
        }

        // Generar nombres de documentos para los eventos.
        FirebaseUploader uploader = new FirebaseUploader();
        List<String> nombresDocumentos = uploader.generarNombresDocumentos(eventos);

        boolean actualizacionExitosa = true;

        // Recorrer los paneles del GridPane y actualizar la disponibilidad.
        for (int i = 0; i < paneles.size(); i++) {
            AnchorPane panel = paneles.get(i);
            APITableController controlador = (APITableController) panel.getProperties().get("controller");

            if (controlador != null) {
                boolean nuevaDisponibilidad = controlador.obtenerEstadoCheckBoxDisponible();
                String nombreDocumento = nombresDocumentos.get(i);

                boolean result = CRUDFirebase.actualizarCampoEvento(nombreDocumento, "disponible", nuevaDisponibilidad);
                if (!result) {
                    actualizacionExitosa = false;
                }
            }
        }

        if (actualizacionExitosa) {
            mostrarAlertaInfo("Disponibilidad actualizada exitosamente para todos los eventos.");
        } else {
            mostrarAlertaWarning("Ocurrió un error al actualizar uno o más eventos.");
        }
    }

    /**
     * Obtiene la ubicación seleccionada.
     * Este método debe ser implementado según tu lógica de selección en la UI.
     */
    private String obtenerUbicacionSeleccionada() {
        // Implementa la lógica para obtener la ubicación seleccionada (ej. un ComboBox).
        return ubicacion.getValue(); // Supone que 'ubicacion' es un ComboBox.
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

    
   
}
