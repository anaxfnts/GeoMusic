package utils;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Evento;

public class GridPaneAPITable {

    /**
     * Método para crear un TableView con columnas dinámicas para mostrar eventos.
     *
     * @param eventos Lista de eventos que se mostrarán en el TableView.
     * @return TableView<Evento> configurado con las columnas y datos de los eventos.
     */
    public TableView<Evento> crearTableViewConColumnasDinamicas(List<Evento> eventos) {
        // Crear un nuevo TableView para mostrar eventos
        TableView<Evento> tableView = new TableView<>();

        // Crear las columnas y asociarlas a las propiedades de la clase Evento
        TableColumn<Evento, String> nombreEventoColumna = new TableColumn<>("Evento");
        nombreEventoColumna.setCellValueFactory(new PropertyValueFactory<>("nombreEvento"));

        TableColumn<Evento, String> artistaColumna = new TableColumn<>("Artista");
        artistaColumna.setCellValueFactory(new PropertyValueFactory<>("artista"));

        TableColumn<Evento, String> fechaColumna = new TableColumn<>("Fecha");
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<Evento, String> ubicacionColumna = new TableColumn<>("Ubicación");
        ubicacionColumna.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        TableColumn<Evento, String> validezColumna = new TableColumn<>("Validez");
        validezColumna.setCellValueFactory(new PropertyValueFactory<>("validez"));

        // Añadir todas las columnas al TableView
        tableView.getColumns().addAll(nombreEventoColumna, artistaColumna, fechaColumna, ubicacionColumna, validezColumna);

        // Añadir la lista de eventos como datos del TableView
        tableView.setItems(FXCollections.observableArrayList(eventos));

        // Configuración opcional (ajuste automático de columnas)
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }
}
