package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.Evento;
import views.APITableController;

public class GridPaneAPITable {

    // Método para crear un GridPane con los paneles proporcionados.
    public GridPane crearGridPane(List<AnchorPane> paneles) {
        GridPane nuevo = new GridPane();
        nuevo.setHgap(30);
        nuevo.setVgap(50);
        nuevo.setAlignment(Pos.CENTER);

        int fila = 0;
        int columna = 0;

        // Recorremos la lista de paneles y los agregamos al GridPane.
        for (AnchorPane panel : paneles) {
            nuevo.add(panel, columna, fila);
            columna++;

            // Cambia de fila cada 2 columnas.
            if (columna >= 2) {
                columna = 0;
                fila++;
            }
        }
        return nuevo;
    }

    // Método para crear una lista de paneles de eventos basada en la lista de
    // eventos proporcionada.
//    public List<AnchorPane> crearPaneles(List<Evento> eventosList) throws IOException {
//        List<AnchorPane> paneles = new ArrayList<>();
//
//        for (Evento evento : eventosList) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/APITable.fxml"));
//            AnchorPane root = loader.load();
//            APITableController controlador = loader.getController();
//
//            // Enviamos los datos de cada evento al controlador de eventos.
//            controlador.setDatos(evento);
//
//            paneles.add(root);
//        }
//        return paneles;
//    }
    
    public List<AnchorPane> crearPaneles(List<Evento> eventosList) throws IOException {
      List<AnchorPane> paneles = new ArrayList<>();

      for (Evento evento : eventosList) {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/APITable.fxml"));
          AnchorPane root = loader.load();
          APITableController controlador = loader.getController();

          // Enviamos los datos de cada evento al controlador.
          controlador.setDatos(evento);

          // Guardamos el controlador en las propiedades del panel para acceder más tarde.
          root.getProperties().put("controller", controlador);

          paneles.add(root);
      }
      return paneles;
  }

}
