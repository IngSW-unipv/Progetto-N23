package it.unipv.ingsw.magstudio.model.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe contenente alcuni metodi di criptatura di testi
 */
public class Encryption {
    private static MessageDigest md;

    /**
     * Metodo utilizzato per criptare un testo con l'algoritmo MD5
     * @param text Il testo da criptare
     * @return Il testo criptato
     */
    public static String MD5Encryptor(String text){
        byte[] bytesOfMessage = new byte[0];
        try {
            bytesOfMessage = text.getBytes(StandardCharsets.UTF_8);
            md = MessageDigest.getInstance("MD5");
            BigInteger i = new BigInteger(1,md.digest(bytesOfMessage));
            return String.format("%1$032X", i);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo utilizzato per criptare un testo con l'algoritmo SHA-256
     * @param text Il testo da criptare
     * @return Il testo criptato
     */
    public static String SHA256Encryptor(String text){
        byte[] bytesOfMessage = new byte[0];
        try {
            bytesOfMessage = text.getBytes(StandardCharsets.UTF_8);
            md = MessageDigest.getInstance("SHA-256");
            BigInteger i = new BigInteger(1,md.digest(bytesOfMessage));
            return String.format("%1$032X", i);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

