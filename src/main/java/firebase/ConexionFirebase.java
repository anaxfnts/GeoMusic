package firebase;

import java.io.IOException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

/**
 * Clase para gestionar la conexión con Firebase. Permite inicializar y obtener
 * una instancia de Firestore.
 */
public class ConexionFirebase {

  private static Firestore firestore;

  /**
   * Inicializa la conexión con Firebase y configura Firestore.
   *
   * @return una instancia de Firestore.
   */
  public static Firestore iniciarFirebase() {
    try {
      // Cargar el archivo de configuración desde el classpath
      GoogleCredentials credentials = GoogleCredentials.fromStream(ConexionFirebase.class.getClassLoader()
          .getResourceAsStream("json/geomusic-95f90-ac133bf8f399.json"));

      FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials)
          .setDatabaseUrl("https://geomusic-95f90.firebaseio.com/").build();

      FirebaseApp.initializeApp(options);

      System.out.println("Firebase iniciada correctamente");

      // Inicializar Firestore solo después de que Firebase esté completamente
      // inicializado
      firestore = FirestoreClient.getFirestore();

    } catch (IOException e) {
      // Manejar la excepción de manera significativa para tu aplicación
      System.err.println("Error al inicializar Firebase: " + e.getMessage());
    }

    return firestore;
  }

  /**
   * Obtiene la instancia de Firestore. Si no está inicializada, la inicializa.
   *
   * @return la instancia de Firestore.
   */
  public static Firestore getFirestore() {
    if (firestore == null) {
      // Si la instancia de Firestore aún no está inicializada, inicialízala
      firestore = iniciarFirebase();
    }
    return firestore;
  }
}
