package views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import firebase.CRUDFirebase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import models.Evento;
import utils.GridPaneAPITable;

public class VerAPIController implements Initializable {

    private GridPaneAPITable gridHelper = new GridPaneAPITable();

    private static List<Evento> listaEventos = new ArrayList<>();
    private TableView<Evento> tableView;

    @FXML
    private BorderPane borderPaneEventos;

    @FXML
    private ComboBox<String> ubicacion;

    private String ubicacionPredeterminada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Listener para cambios en el ComboBox de ubicación
        ubicacion.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                actualizarSegunUbicacion(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ubicacion.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                agregarProvincias();
            }
        });

        try {
            agregarProvincias();
            ubicacionPredeterminada = LoginController.mostrarUbicacionPredeteminada();
            ubicacion.setValue(ubicacionPredeterminada);
            actualizarSegunUbicacion(ubicacionPredeterminada);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        ubicacion.getItems().clear();
        ubicacion.getItems().addAll(provincias);
    }

    public void actualizarSegunUbicacion(String ubicacionSeleccionada) throws IOException {
        listaEventos = CRUDFirebase.consultarEventos(ubicacionSeleccionada);

        if (tableView == null) {
            tableView = gridHelper.crearTableViewConColumnasDinamicas(listaEventos);
            borderPaneEventos.setCenter(tableView);
        } else {
            tableView.getItems().clear();
            tableView.getItems().addAll(listaEventos);
        }
    }
}
