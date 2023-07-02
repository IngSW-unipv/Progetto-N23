package it.unipv.ingsw.magstudio.model.bean;

import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ProdottoTest {
    public static void main(String[] args) throws SQLException, IOException {
        Prodotto p = new Prodotto("RTX 4090 TI",1,"SCHEDA VAIDEO PAWA CON I LED RGB DA GAMING");

        Image image = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File"); //TODO: mettere scelta file solo immagini
        File selectedFile = fileChooser.showOpenDialog(null);
        try {
            image = SwingFXUtils.toFXImage(ImageIO.read(selectedFile),null);
        }catch (Exception e){
            e.printStackTrace();
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();

        p.setImmagine(byteArray);

    }
}
