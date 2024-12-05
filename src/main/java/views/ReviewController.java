package views;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ReviewController {

  // Lista para mostrar las reviews.
  @FXML
  private ListView<String> reviewList;

  // Campo de texto para ingresar la review.
  @FXML
  private TextField reviewField;

  // Campo de texto para ingresar la puntuación.
  @FXML
  private TextField ratingField;

  // Referencia a la base de datos Firestore.
  private Firestore db;

  // Identificador del evento al que pertenecen las reviews.
  private String eventId;

  // Conjunto para rastrear las reviews cargadas.
  private Set<String> loadedReviewIds = new HashSet<>();

  /**
   * Método de inicialización que se llama automáticamente al cargar la interfaz.
   */
  public void initialize() {
    // Obtiene la instancia de Firestore.
    db = firebase.ConexionFirebase.getFirestore();
  }

  /**
   * Método para cargar las reviews de un evento específico.
   *
   * @param eventIdRec El identificador del evento cuyas reviews se van a cargar.
   */
  public void loadReviews(String eventIdRec) {
    eventId = eventIdRec;
    ApiFuture<QuerySnapshot> future = db.collection("Reviews").whereEqualTo("eventId", eventId).get();
    // Usa un thread pool para ejecutar la carga de reviews en segundo plano.
    Executors.newCachedThreadPool().submit(() -> {
      try {
        QuerySnapshot querySnapshot = future.get();
        Platform.runLater(() -> {
          querySnapshot.getDocuments().forEach(document -> {
            String documentId = document.getId();
            // Solo añade las reviews que no han sido cargadas anteriormente.
            if (!loadedReviewIds.contains(documentId)) {
              loadedReviewIds.add(documentId);
              String reviewText = document.getString("correo") + ": " + document.getString("content") + " - "
                  + document.getString("rating") + " puntos.";
              // Añade la review a la lista.
              reviewList.getItems().add(reviewText);
            }
          });
        });
      } catch (Exception e) {
        Platform.runLater(() -> {
          System.err.println("Error cargando reviews: " + e.getMessage());
        });
      }
    });
  }

  /**
   * Método para publicar una nueva review.
   */
  @FXML
  public void postReview() {
    DocumentReference docRef = db.collection("Reviews").document();
    Map<String, Object> review = new HashMap<>();
    // Crea un mapa con la información de la review.
    review.put("correo", LoginController.loggedInUserMail);
    review.put("content", reviewField.getText());
    review.put("eventId", eventId);
    review.put("rating", ratingField.getText());

    ApiFuture<WriteResult> future = docRef.set(review);
    // Usa un thread pool para ejecutar la publicación de la review en segundo
    // plano.
    Executors.newCachedThreadPool().submit(() -> {
      try {
        WriteResult resultado = future.get();
        Platform.runLater(() -> {
          // Muestra una alerta informativa.
          alertaInfo();

          // Limpia los campos de texto.
          reviewField.clear();
          ratingField.clear();

          String documentId = docRef.getId();
          loadedReviewIds.add(documentId);
          String reviewText = LoginController.loggedInUserMail + ": " + review.get("content") + " - "
              + review.get("rating") + " puntos.";
          // Añade la nueva review a la lista.
          reviewList.getItems().add(reviewText);
        });
      } catch (Exception e) {
        Platform.runLater(() -> {
          System.err.println("Error añadiendo la review: " + e.getMessage());
          // Muestra una alerta de error.
          alertaError();
        });
      }
    });
  }

  /**
   * Método para mostrar una alerta de error.
   */
  public static void alertaError() {
    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setTitle("Error");
    errorAlert.setHeaderText("Falló al publicar la review");
    errorAlert.setContentText("Inténtalo más tarde");
    errorAlert.showAndWait();
  }

  /**
   * Método para mostrar una alerta informativa.
   */
  public static void alertaInfo() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Review publicada");
    alert.setHeaderText(null);
    alert.setContentText("Tu puntuación ha sido publicada correctamente");
    alert.showAndWait();
  }
}
