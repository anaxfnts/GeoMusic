package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import firebase.FirebaseUploader;
import models.Evento;

public class TicketmasterApi {
  private static final String API_KEY = "03FZCT9HOG593EsPGxUh5EjJutqKyOh9";
  private static final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json";

  // Método para obtener eventos musicales por ciudad con manejo de errores
  public JsonArray obtenerConciertosPorCiudad(String ciudad) throws Exception {
    String urlString = BASE_URL + "?countryCode=ES&city=" + ciudad + "&classificationName=music&apikey=" + API_KEY;

    URL url = new URL(urlString);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    // Añadir tiempos de espera
    conn.setConnectTimeout(5000);
    conn.setReadTimeout(5000);

    int responseCode = conn.getResponseCode();

    if (responseCode != 200) {
      throw new Exception("Error al hacer la solicitud a Ticketmaster API: " + responseCode);
    }

    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

    if (jsonObject.has("_embedded")) {
      return jsonObject.getAsJsonObject("_embedded").getAsJsonArray("events");
    } else {
      return new JsonArray();
    }
  }

//Método para procesar y subir solo los campos relevantes a Firebase
public void procesarYSubirConciertos(String ciudad) throws Exception {
   System.out.println("Procesando conciertos para la ciudad: " + ciudad);

   try {
       JsonArray eventos = obtenerConciertosPorCiudad(ciudad);
       System.out.println("Conciertos obtenidos: " + eventos.size());

       FirebaseUploader firebaseUploader = new FirebaseUploader();

       for (JsonElement evento : eventos) {
           JsonObject eventoObj = evento.getAsJsonObject();

           // Extraer los campos correctamente
           String nombreEvento = eventoObj.has("name") ? eventoObj.get("name").getAsString() : "Sin nombre"; // Nombre del evento
           String fecha = eventoObj.has("dates") && eventoObj.getAsJsonObject("dates").has("start")
               ? eventoObj.getAsJsonObject("dates").getAsJsonObject("start").get("localDate").getAsString()
               : "Sin fecha"; // Fecha (en formato YYYY-MM-DD)

           String descripcion = eventoObj.has("info") ? eventoObj.get("info").getAsString() : nombreEvento; // Descripción real o nombre del evento

           // Extraer la ubicación correctamente (nombre del lugar y ciudad)
           String ubicacion = eventoObj.has("_embedded") && eventoObj.getAsJsonObject("_embedded").has("venues")
               ? eventoObj.getAsJsonObject("_embedded").getAsJsonArray("venues").get(0).getAsJsonObject().get("name").getAsString()
               : "Sin ubicación"; // Nombre del lugar

           String ciudadEvento = eventoObj.has("_embedded") && eventoObj.getAsJsonObject("_embedded").has("venues")
               ? eventoObj.getAsJsonObject("_embedded").getAsJsonArray("venues").get(0).getAsJsonObject().get("city")
                   .getAsJsonObject().get("name").getAsString()
               : ciudad; // Ciudad

           String artista = eventoObj.has("_embedded") && eventoObj.getAsJsonObject("_embedded").has("attractions")
               ? eventoObj.getAsJsonObject("_embedded").getAsJsonArray("attractions").get(0).getAsJsonObject().get("name")
                   .getAsString()
               : nombreEvento; // Nombre del artista

           // URL de la imagen del evento
           String imagenEvento = eventoObj.has("images") && eventoObj.getAsJsonArray("images").size() > 0
               ? eventoObj.getAsJsonArray("images").get(0).getAsJsonObject().get("url").getAsString()
               : "Sin imagen"; // URL de la imagen

           // Formatear la fecha para el ID en formato DD-MM-YY
           String fechaFormateada = fecha.equals("Sin fecha") ? "00-00-00"
               : fecha.substring(8, 10) + "-" + fecha.substring(5, 7) + "-" + fecha.substring(0, 4);

           // Concatenar el lugar con el nombre del artista en el formato "Lugar - Artista"
           String nombreEventoFinal = ubicacion + " - " + artista;

           // Normalizar nombre del artista (remover espacios)
           String artistaNormalizado = artista.replace(" ", "");

           // Construir el ID con el formato CiudadArtistaFecha
           String idDocumento = ciudad + artistaNormalizado + fechaFormateada;

           // Imprimir los valores para ver por qué los campos se guardan incorrectamente
           System.out.println("Nombre del evento: " + nombreEventoFinal);
           System.out.println("Fecha: " + fecha);
           System.out.println("Descripción: " + descripcion);
           System.out.println("Ubicación (ciudad): " + ciudadEvento);
           System.out.println("Artista: " + artista);
           System.out.println("URL de la imagen: " + imagenEvento);
           System.out.println("ID generado: " + idDocumento);

           // Crear un objeto Evento con los datos correctos en el orden adecuado
           Evento nuevoEvento = new Evento(
               nombreEventoFinal, // Nombre del evento concatenado con el lugar y artista
               descripcion,       // Descripción real del evento
               fechaFormateada,   // Fecha en formato DD-MM-YYYY
               imagenEvento,      // URL de la imagen del evento
               artista,           // Artista
               ciudadEvento,      // Ciudad del evento como ubicación
               true               // Disponible por defecto
           );

           // Subir el evento a Firebase
           firebaseUploader.subirEventoAFirebase(nuevoEvento, idDocumento);
       }

   } catch (Exception e) {
       System.err.println("Error al procesar los conciertos: " + e.getMessage());
       e.printStackTrace();
   }
}

}
