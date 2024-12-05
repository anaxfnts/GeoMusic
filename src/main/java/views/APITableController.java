package views;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Evento;

public class APITableController {

  private Evento evento = null;
  
  @FXML
  private Label lblNombreEvento;

  @FXML
  private Label lblArtista;
  
  @FXML
  private TextField txtFecha;
  
  @FXML
  private TextField txtUbicacion;
  
  @FXML
  public CheckBox checkBoxDisponible;

  /**
   * Establece los datos del evento en los campos correspondientes.
   *
   * @param eventos El evento con los datos a establecer.
   * @throws FileNotFoundException Si ocurre un error al encontrar el archivo de
   *                               imagen.
   */
  public void setDatos(Evento eventos) {
    evento = eventos;
    lblNombreEvento.setText(eventos.getNombreEvento());
    lblArtista.setText(eventos.getArtista());
    txtFecha.setText(eventos.getFecha());
    txtFecha.setEditable(false);
    txtUbicacion.setText(eventos.getUbicacion());
    txtUbicacion.setEditable(false);
    checkBoxDisponible.setSelected(eventos.isDisponible());
  }

  /**
   * Devuelve el estado del checkbox de disponibilidad.
   *
   * @return true si el checkbox está seleccionado, false en caso contrario.
   */
  public boolean obtenerEstadoCheckBoxDisponible() {
    return checkBoxDisponible.isSelected();
  }
  
  /**
   * Inicializa el controlador.
   *
   * @param location  La ubicación utilizada para resolver rutas relativas del
   *                  objeto raíz, o null si no se conoce.
   * @param resources Los recursos utilizados para localizar el objeto raíz, o
   *                  null si no se conocen.
   */
  public void initialize(URL location, ResourceBundle resources) {
    txtFecha.setEditable(false);
    txtUbicacion.setEditable(false);
  }
}
