package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

  // Método para convertir una cadena de texto a su representación en SHA-256
  public static String convertirSHA256(String password) {
    MessageDigest md = null;
    try {
      // Se instancia el algoritmo de hash SHA-256
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      // Manejo de excepción en caso de que no se encuentre el algoritmo
      e.printStackTrace();
      return null;
    }

    // Se calcula el hash de la contraseña
    byte[] hash = md.digest(password.getBytes());
    StringBuffer sb = new StringBuffer();

    // Se convierte el hash de bytes a una representación hexadecimal
    for (byte b : hash) {
      sb.append(String.format("%02x", b));
    }

    // Se devuelve la cadena hexadecimal resultante
    return sb.toString();
  }
}
