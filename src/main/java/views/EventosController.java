package views;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class EventosController {

  // Referencia al panel de eventos.
  @FXML
  private Pane paneEventos;

  /**
   * Maneja el evento de ratón para cambiar a la vista de eventos.
   *
   * @param event El evento de ratón que dispara el método.
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  @FXML
  void vistaEventos(MouseEvent event) throws IOException {
    vistaVerEventos();
  }

  /**
   * Carga la vista de eventos y la establece en el centro del panel raíz.
   *
   * @throws IOException Si ocurre un error de E/S al cargar la vista.
   */
  private void vistaVerEventos() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PrincipalView.fxml"));
    AnchorPane root = loader.load();
    // Se establece la vista de eventos en el centro del panel raíz.
    LoginController.root.setCenter(root);
  }
}
