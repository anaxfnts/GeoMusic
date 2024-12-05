package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.Evento;
import views.EventoController;

public class GridPaneEventos {

  // Método para crear un GridPane con los paneles proporcionados.
  public GridPane crearGridPane(int columna, int fila, List<AnchorPane> paneles) {

    GridPane nuevo = new GridPane();

    nuevo.setHgap(90);
    nuevo.setVgap(50);
    nuevo.setAlignment(Pos.CENTER);
    // Recorremos la lista de paneles y los agregamos al GridPane.
    for (AnchorPane panele : paneles) {
      nuevo.add(panele, columna, fila);
      fila++;
    }
    return nuevo;
  }

  // Método para crear una lista de paneles de eventos basada en la lista de
  // eventos proporcionada.
  public List<AnchorPane> crearPaneles(List<Evento> eventosList) throws IOException {
    List<AnchorPane> paneles = new ArrayList<>();

    for (int i = 0; i < eventosList.size(); i++) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Evento.fxml"));
      AnchorPane root = loader.load();
      EventoController controlador = loader.getController();

      // Enviamos los datos de cada evento al controlador de eventos.
      controlador.setDatos(eventosList.get(i));

      paneles.add(root);
    }
    return paneles;
  }

}
