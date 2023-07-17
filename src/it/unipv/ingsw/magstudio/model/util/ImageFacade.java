package it.unipv.ingsw.magstudio.model.util;

import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageFacade {
    public static byte[] leggiImmagine(String path) throws IOException {
        File f = new File(path);
        InputStream ip = new FileInputStream(f);
        byte[] foto = new byte[(int) f.length()];
        ip.read(foto);

        ip.close();

        return foto;
    }

    public static Image byteToImage(byte[] image){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
        Image img = new Image(inputStream);
        return img;
    }
}
