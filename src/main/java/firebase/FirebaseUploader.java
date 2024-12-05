package firebase;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.api.core.ApiFuture;
import models.Evento;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirebaseUploader {
  private Firestore db;

  public FirebaseUploader() {
    // Inicializa Firestore a través de la clase ConexionFirebase
    this.db = ConexionFirebase.getFirestore();
  }

  // Método para limpiar caracteres no permitidos en el ID del documento
  public String limpiarIdDocumento(String id) {
    // Reemplazar caracteres no permitidos por Firebase en el ID
    return id.replaceAll("[./#%?]", "-"); // Reemplaza caracteres no permitidos por un guión
  }

  // Método para formatear la fecha en el formato deseado (dd-MM-yy)
  public String formatearFecha(String fecha) {
    if (fecha.length() >= 10) {
      // Formato "YYYY-MM-DD" a "DD-MM-YY"
      return fecha.substring(8, 10) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(2, 4);
    } else {
      return fecha; // Si el formato no es el esperado, devuelve la fecha tal cual
    }
  }

  // Método para normalizar el nombre de la ciudad (por ejemplo, reemplazar
  // espacios por guiones o quitar tildes)
  public String normalizarCiudad(String ciudad) {
    // Remueve acentos y espacios
    return ciudad.replace(" ", "").replaceAll("[áéíóúÁÉÍÓÚ]", "").trim();
  }

  // Método para normalizar el nombre del artista (ej. quitar espacios y
  // caracteres especiales)
  public String normalizarArtista(String artista) {
    // Remueve espacios y caracteres no deseados
    return artista.replace(" ", "").trim();
  }

  // Método para subir un evento a Firebase con un ID personalizado
  public void subirEventoAFirebase(Evento evento, String idDocumentoOriginal) {
    // Aquí no volvemos a formatear la ciudad ni el artista, usamos el ID tal cual

    // Limpiar el ID del documento solo si es necesario (remover caracteres no
    // permitidos)
    String idDocumento = limpiarIdDocumento(idDocumentoOriginal); // Usar directamente el ID generado en el bucle

    // Subir el evento con el ID personalizado
    ApiFuture<WriteResult> future = db.collection("Eventos").document(idDocumento).set(evento);

    try {
      // Bloquear hasta que se complete la operación
      future.get();
      System.out.println("Evento agregado con ID: " + idDocumento);
    } catch (InterruptedException | ExecutionException e) {
      System.err.println("Error al agregar el evento: " + e.getMessage());
    }
  }
  
//Método para generar nombres de documentos
public List<String> generarNombresDocumentos(List<Evento> eventos) {
   List<String> nombresDocumentos = new ArrayList<>();

   for (Evento evento : eventos) {
       // Obtener directamente los componentes necesarios
       String fecha = evento.getFecha(); // Usar directamente la fecha del objeto Evento
       String ciudad = normalizarCiudad(evento.getUbicacion());
       String artista = normalizarArtista(evento.getArtista());

       // Concatenar los componentes para formar el ID del documento
       String idDocumento = limpiarIdDocumento(ciudad + artista + fecha);
       nombresDocumentos.add(idDocumento);
   }

   return nombresDocumentos;
}


}
