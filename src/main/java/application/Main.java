package application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import firebase.CRUDFirebase;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.TicketmasterApi;
import views.PreviaController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            System.out.println("Cerrar la ventana está deshabilitado.");
        });

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

        CRUDFirebase conexion = new CRUDFirebase();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                TicketmasterApi api = new TicketmasterApi();

                // Lista de ciudades en lugar de provincias
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


                // Procesar y subir conciertos de cada ciudad
                for (String ciudad : ciudades) {
                    try {
                        System.out.println("Subiendo conciertos para la ciudad: " + ciudad);
                        api.procesarYSubirConciertos(ciudad); // Cambiado para trabajar con ciudad
                    } catch (Exception e) {
                        System.err.println("Error al subir conciertos de la ciudad " + ciudad + ": " + e.getMessage());
                    }
                }
                return null;
            }
        };

        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}