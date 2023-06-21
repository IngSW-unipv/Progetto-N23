package it.unipv.ingsw.magstudio.model.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.scene.image.Image;
public class GeneratoreCodici {
    public static Image generaQrCode(String testo, int larghezza, int altezza){
        Image image = null;
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(testo, BarcodeFormat.QR_CODE, larghezza, altezza);
            image = SwingFXUtils.toFXImage(MatrixToImageWriter.toBufferedImage(matrix), null );
        }catch (Exception ignored){}
        return image;
    }

    public static Image generaBarCode(String testo, int larghezza, int altezza){
        Image image = null;
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(testo, BarcodeFormat.CODE_128, larghezza, altezza);
            image = SwingFXUtils.toFXImage(MatrixToImageWriter.toBufferedImage(matrix), null );
        }catch (Exception ignored){}
        return image;
    }
}
