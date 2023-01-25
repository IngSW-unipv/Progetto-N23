package it.unipv.ingsw.magstudio.model.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private static MessageDigest md;
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

