package application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import firebase.CRUDFirebase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.TicketmasterApi;
import views.PreviaController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Configuración inicial de la ventana principal
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            System.out.println("Cerrar la ventana está deshabilitado.");
        });

        // Cargar y mostrar la vista inicial (PreviaView)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PreviaView.fxml"));
        Parent root = loader.load();
        Scene escena = new Scene(root);
        primaryStage.setScene(escena);
        PreviaController controlador = loader.getController();
        controlador.setStage(primaryStage);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/images/logo.png"));
        primaryStage.setTitle("GeoMusic");
        primaryStage.show();
        
        // Conexión inicial (opcional, si la necesitas más tarde)
        CRUDFirebase conexion = new CRUDFirebase();

        // Iniciar la carga de conciertos en segundo plano
        iniciarCargaConciertosEnSegundoPlano();
    }

    private void iniciarCargaConciertosEnSegundoPlano() {
        // Lista de ciudades a procesar
        List<String> ciudades = Arrays.asList(
            "Alava", "Albacete", "Alicante", "Almeria", "Asturias", "Avila", 
            "Badajoz", "Baleares", "Barcelona", "Burgos", "Caceres", "Cadiz", 
            "Cantabria", "Castellon", "Ceuta", "Ciudad Real", "Cordoba", "Cuenca", 
            "Girona", "Granada", "Guadalajara", "Guipuzcoa", "Huelva", "Huesca", 
            "Jaen", "La Coruna", "La Rioja", "Las Palmas", "Leon", "Lleida", 
            "Lugo", "Madrid", "Malaga", "Melilla", "Murcia", "Navarra", 
            "Ourense", "Palencia", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", 
            "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", 
            "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"
        );

        // Crear una tarea para procesar los datos en segundo plano
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                TicketmasterApi api = new TicketmasterApi();

                // Procesar y subir conciertos de cada ciudad
                for (String ciudad : ciudades) {
                    try {
                        // Simula la operación para cada ciudad
                        api.procesarYSubirConciertos(ciudad);
                    } catch (Exception e) {
                        System.err.println("Error al subir conciertos de la ciudad " + ciudad + ": " + e.getMessage());
                    }
                }
                return null;
            }
        };

        // Al finalizar la tarea, mostrar un mensaje informativo
        task.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("GeoMusic");
                alert.setHeaderText("Conciertos cargados");
                alert.setContentText("Carga de conciertos completada.");
                alert.showAndWait();
            });
        });

        // Si ocurre un fallo, mostrar un mensaje de error
        task.setOnFailed(e -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("GeoMusic");
                alert.setHeaderText("Carga incompleta");
                alert.setContentText("Ocurrió un problema al cargar los conciertos.");
                alert.showAndWait();
            });
        });

        // Iniciar la tarea en un hilo separado
        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
