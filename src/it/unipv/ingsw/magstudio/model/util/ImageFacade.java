package it.unipv.ingsw.magstudio.model.util;

import javafx.scene.image.Image;
import java.io.*;

public class ImageFacade {
    /**
     * Legge un immagine e la restituisce in in byte
     * @param path - Il percorso dell'immagine
     * @return L'immagine in byte
     * @throws IOException
     */
    public static byte[] leggiImmagine(String path) throws IOException {
        File f = new File(path);
        InputStream ip = new FileInputStream(f);
        byte[] foto = new byte[(int) f.length()];
        ip.read(foto);

        ip.close();

        return foto;
    }

    /**
     * Converte l'immagine in byte in Image
     * @param image - L'immagine in byte
     * @return L'immagine come Image
     * @see Image
     */
    public static Image byteToImage(byte[] image){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
        Image img = new Image(inputStream);
        return img;
    }
}
