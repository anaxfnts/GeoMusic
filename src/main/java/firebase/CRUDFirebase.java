package firebase;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.UnavailableException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import io.grpc.StatusRuntimeException;
import javafx.scene.control.Alert;
import models.Cuenta;
import models.Evento;

/**
 * Clase para realizar operaciones CRUD en Firebase. Gestiona cuentas y eventos
 * en Firestore.
 */
public class CRUDFirebase {

  private static Firestore bd = null;

  /**
   * Constructor de la clase. Inicializa la conexión con Firebase.
   */
  public CRUDFirebase() {
    ConexionFirebase conexionFirebase = new ConexionFirebase();
    bd = conexionFirebase.iniciarFirebase();
  }

  /**
   * Agrega una cuenta a Firestore.
   *
   * @param cuenta la cuenta a agregar.
   * @return true si la operación es exitosa, false en caso contrario.
   */
  public static boolean addFirebaseCuenta(Cuenta cuenta) {
    boolean key = false;
    Map<String, Object> docCuenta = new HashMap<>();
    docCuenta.put("idCuenta", cuenta.getIdCuenta());
    docCuenta.put("nombre", cuenta.getNombre());
    docCuenta.put("correo", cuenta.getCorreo());
    docCuenta.put("contrasenya", cuenta.getContrasenya());
    docCuenta.put("ubicacion", cuenta.getUbicacion());
    docCuenta.put("usuario", cuenta.getUsuario());
    docCuenta.put("es_administrar", cuenta.getEs_administrador());

    ApiFuture<WriteResult> future = bd.collection("Cuentas").document(cuenta.getUsuario()).set(docCuenta);
    try {
      System.out.println("Update time : " + future.get().getUpdateTime());
      key = true;
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return key;
  }

  /**
   * Consulta un usuario en Firestore.
   *
   * @param usuario el nombre del usuario a consultar.
   * @return el nombre del usuario si existe, una cadena vacía en caso contrario.
   */
  public static String consultarUsuario(String usuario) {
    if (usuario == null || usuario.isEmpty()) {
      System.out.println("El nombre de usuario es nulo o vacío.");
      return "";
    }

    DocumentReference docRef = bd.collection("Cuentas").document(usuario);
    ApiFuture<DocumentSnapshot> future = docRef.get();
    DocumentSnapshot document;
    String usuarioConsultado = "";
    try {
      document = future.get();
      if (document.exists()) {
        usuarioConsultado = document.getString("usuario");
        System.out.println("Usuario encontrado: " + usuarioConsultado);
      } else {
        usuarioConsultado = usuario;
        System.out.println("Usuario no encontrado, disponible: " + usuarioConsultado);
      }
    } catch (ExecutionException e) {
      manejarExcepcion(e);
    } catch (ApiException e) {
      System.err.println("Error de API: " + e.getMessage());
      alertaError();
    } catch (Exception e) {
      System.err.println("Se produjo un error inesperado: " + e.getMessage());
      alertaError();
    }
    return usuarioConsultado;
  }

  /**
   * Consulta la contraseña de un usuario en Firestore.
   *
   * @param usuario el nombre del usuario cuya contraseña se desea consultar.
   * @return la contraseña del usuario si existe, una cadena vacía en caso
   *         contrario.
   */
  public static String consultarPasswd(String usuario) {
    if (usuario == null || usuario.isEmpty()) {
      System.out.println("La contraseña es nula o vacía.");
      return "";
    }

    DocumentReference docRef = bd.collection("Cuentas").document(usuario);
    ApiFuture<DocumentSnapshot> future = docRef.get();
    DocumentSnapshot document;
    String passwdConsultado = "";
    try {
      document = future.get();
      if (document.exists()) {
        passwdConsultado = document.getString("contrasenya");
        System.out.println("Contraseña coincidente: " + passwdConsultado);
      } else {
        passwdConsultado = usuario;
        System.out.println("Contraseña no coincide, proporcionada: " + passwdConsultado);
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      alertaError();
    }
    return passwdConsultado;
  }

  /**
   * Consulta la ubicación de un usuario en Firestore.
   *
   * @param usuario el nombre del usuario cuya ubicación se desea consultar.
   * @return la ubicación del usuario si existe, una cadena vacía en caso
   *         contrario.
   */
  public static String consultarUbicacion(String usuario) {
    if (usuario == null || usuario.isEmpty()) {
      System.out.println("La ubicación es nula o vacía.");
      return "";
    }

    DocumentReference docRef = bd.collection("Cuentas").document(usuario);
    ApiFuture<DocumentSnapshot> future = docRef.get();
    DocumentSnapshot document;
    String ubicacionConsultado = "";
    try {
      document = future.get();
      if (document.exists()) {
        ubicacionConsultado = document.getString("ubicacion");
        System.out.println("Ubicación: " + ubicacionConsultado);
      } else {
        ubicacionConsultado = usuario;
        System.out.println("Ubicación no coincide, proporcionada: " + ubicacionConsultado);
      }
    } catch (InterruptedException | ExecutionException e) {
      alertaError();
      e.printStackTrace();
    }
    return ubicacionConsultado;
  }

  /**
   * Consulta el correo de un usuario en Firestore.
   *
   * @param usuario el nombre del usuario cuyo correo se desea consultar.
   * @return el correo del usuario si existe, una cadena vacía en caso contrario.
   */
  public static String consultarCorreo(String usuario) {
    if (usuario == null || usuario.isEmpty()) {
      System.out.println("El correo es nulo o vacío.");
      return "";
    }

    DocumentReference docRef = bd.collection("Cuentas").document(usuario);
    ApiFuture<DocumentSnapshot> future = docRef.get();
    DocumentSnapshot document;
    String correoConsultado = "";
    try {
      document = future.get();
      if (document.exists()) {
        correoConsultado = document.getString("correo");
        System.out.println("Correo: " + correoConsultado);
      } else {
        correoConsultado = usuario;
        System.out.println("Correo no coincide, proporcionado: " + correoConsultado);
      }
    } catch (InterruptedException | ExecutionException e) {
      alertaError();
      e.printStackTrace();
    }
    return correoConsultado;
  }

  /**
   * Obtiene los datos de una cuenta en Firestore.
   *
   * @param usuario el nombre del usuario cuya cuenta se desea obtener.
   * @return una instancia de Cuenta con los datos del usuario, null si no existe.
   */
  public static Cuenta obtenerDatosCuenta(String usuario) {
    if (usuario == null || usuario.isEmpty()) {
      System.out.println("La contraseña es nula o vacía.");
      return null;
    }

    DocumentReference docRef = bd.collection("Cuentas").document(usuario);
    ApiFuture<DocumentSnapshot> future = docRef.get();
    DocumentSnapshot document;

    Cuenta cuenta = null;

    try {
      document = future.get();
      if (document.exists()) {
        cuenta = new Cuenta(document.getString("nombre"), document.getString("usuario"),
            document.getString("contrasenya"), document.getString("ubicacion"), document.getString("correo"),
            document.getString("tipo"));
        System.out.println("Datos obtenidos: " + cuenta.toString());
      } else {
        System.out.println("Datos no obtenidos: la cuenta no existe");
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    return cuenta;
  }

  /**
   * Consulta eventos en Firestore según la ubicación.
   *
   * @param ubicacion la ubicación de los eventos a consultar.
   * @return una lista de eventos en la ubicación especificada.
   */
  public static List<Evento> consultarEventos(String ubicacion) {
    ApiFuture<QuerySnapshot> future = bd.collection("Eventos").get();
    List<Evento> listaEventos = new ArrayList<>();

    try {
      QuerySnapshot querySnapshot = future.get();
      for (QueryDocumentSnapshot document : querySnapshot) {
        Evento evento = document.toObject(Evento.class);
        if (evento.getUbicacion().equals(ubicacion)) {
          listaEventos.add(evento);
          System.out.println(evento.toString());
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return listaEventos;
  }
  
  
  public static List<Evento> consultarEventosDisponibles(String ubicacion) {
    List<Evento> listaEventos = new ArrayList<>();
    ApiFuture<QuerySnapshot> future = bd.collection("Eventos")
        .whereEqualTo("ubicacion", ubicacion)
        .whereEqualTo("disponible", true)  // Filtro explícito por disponibilidad
        .get();

    try {
        QuerySnapshot querySnapshot = future.get();
        for (QueryDocumentSnapshot document : querySnapshot) {
            Evento evento = document.toObject(Evento.class);
            listaEventos.add(evento);
            System.out.println(evento.toString());
        }
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
    return listaEventos;
}



  /**
   * Consulta el nombre de un evento en Firestore.
   *
   * @param nombreEvento el nombre del evento a consultar.
   * @return el nombre del evento si existe, null en caso contrario.
   */
  public static String consultarNombreEvento(String nombreEvento) {
    ApiFuture<QuerySnapshot> future = bd.collection("Eventos").get();
    try {
      QuerySnapshot querySnapshot = future.get();
      for (QueryDocumentSnapshot document : querySnapshot) {
        Evento evento = document.toObject(Evento.class);
        if (evento.getNombreEvento().equals(nombreEvento)) {
          return evento.getNombreEvento();
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Muestra una alerta de error.
   */
  public static void alertaError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText("Hay un error de conexión. Inténtalo de nuevo más tarde");
    alert.showAndWait();
  }

  /**
   * Actualiza un campo en un documento de Firestore.
   *
   * @param documento  el ID del documento a actualizar.
   * @param campo      el campo a actualizar.
   * @param valorNuevo el nuevo valor para el campo.
   * @return true si la operación es exitosa, false en caso contrario.
   */
  public static boolean actualizarEnFirebase(String documento, String campo, String valorNuevo) {
    boolean key = false;

    if (documento == null || documento.isEmpty() || campo == null || campo.isEmpty() || valorNuevo == null
        || valorNuevo.isEmpty()) {
      System.out.println("Los parámetros documento, campo o valorNuevo son nulos o vacíos.");
      return false;
    }

    DocumentReference docRef = bd.collection("Cuentas").document(documento);

    try {
      ApiFuture<DocumentSnapshot> future = docRef.get();
      DocumentSnapshot document = future.get();

      if (document.exists()) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(campo, valorNuevo);

        ApiFuture<WriteResult> writeResult = docRef.update(updates);
        System.out.println("Update time: " + writeResult.get().getUpdateTime());
        key = true;
      } else {
        System.out.println("El documento con ID " + documento + " no existe.");
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
      alertaError();
    }

    return key;
  }
  
  
  /**
   * Actualiza un campo en un documento de Firebase correspondiente a un evento.
   *
   * @param eventoId   el ID del evento a actualizar.
   * @param campo      el campo a actualizar (por ejemplo, "disponible").
   * @param valorNuevo el nuevo valor para el campo.
   * @return true si la operación es exitosa, false en caso contrario.
   */
  public static boolean actualizarCampoEvento(String eventoId, String campo, Object valorNuevo) {
      boolean resultado = false;

      if (eventoId == null || eventoId.isEmpty() || campo == null || campo.isEmpty() || valorNuevo == null) {
          System.out.println("Los parámetros eventoId, campo o valorNuevo son nulos o vacíos.");
          return false;
      }

      // Referencia a la colección "Eventos" y el documento específico.
      DocumentReference docRef = bd.collection("Eventos").document(eventoId);

      try {
          // Verificar si el documento existe antes de actualizar.
          ApiFuture<DocumentSnapshot> future = docRef.get();
          DocumentSnapshot document = future.get();

          if (document.exists()) {
              // Crear un mapa para el campo que se va a actualizar.
              Map<String, Object> updates = new HashMap<>();
              updates.put(campo, valorNuevo);

              // Realizar la actualización del campo.
              ApiFuture<WriteResult> writeResult = docRef.update(updates);
              System.out.println("Update time: " + writeResult.get().getUpdateTime());
              resultado = true;
          } else {
              System.out.println("El documento con ID " + eventoId + " no existe.");
          }
      } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
          alertaErrorEvento();
      }

      return resultado;
  }

  
  /**
   * Muestra una alerta de error.
   */
  public static void alertaErrorEvento() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText("Ocurrió un error al intentar actualizar el campo en Firebase.");
    alert.showAndWait();
  }


  
  /**
   * Maneja excepciones al interactuar con Firestore.
   *
   * @param e la excepción a manejar.
   */
  private static void manejarExcepcion(ExecutionException e) {
    Throwable cause = e.getCause();
    if (cause instanceof UnavailableException) {
      System.err.println("Firestore no está disponible: " + cause.getMessage());
      alertaError();
      if (cause.getCause() instanceof UnknownHostException) {
        System.err.println("No se pudo resolver el host: " + cause.getCause().getMessage());
        alertaError();
      }
    } else if (cause instanceof StatusRuntimeException) {
      System.err.println("Error de estado de gRPC: " + cause.getMessage());
      alertaError();
      if (cause.getCause() instanceof UnknownHostException) {
        System.err.println("No se pudo resolver el host: " + cause.getCause().getMessage());
        alertaError();
      }
    } else {
      System.err.println("Error de ejecución: " + e.getMessage());
      alertaError();
    }
  }
}
