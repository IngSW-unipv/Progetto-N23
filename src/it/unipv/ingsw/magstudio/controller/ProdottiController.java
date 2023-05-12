package it.unipv.ingsw.magstudio.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import it.unipv.ingsw.magstudio.model.util.GeneratoreCodici;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProdottiController implements Initializable {
    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane creaProdottoPane,
            eliminaProdottoPane,
            modificaProdottoPane,
            ricercaProdottoPane;

    @FXML
    private MFXTextField crea_prodotti_nome,
            crea_prodotti_quantita,
            crea_prodotti_codice,
            crea_prodotti_descrizione,
            crea_prodotti_fornitore,
            crea_prodotti_scaffale,
            crea_prodotti_ripiano;

    @FXML
    private ImageView codiceBarreCrea, qrCodeCrea, crea_prodotti_immagine;

    @FXML
    private List<MFXTextField> crea_prodotto_nodes, modifica_prodotto_nodes;
    private Stage stage;

    private HiveHubAlert alert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.crea_prodotto_nodes = new ArrayList<>();
        this.crea_prodotto_nodes.addAll(List.of(
                crea_prodotti_nome,
                crea_prodotti_codice,
                crea_prodotti_fornitore,
                crea_prodotti_descrizione,
                crea_prodotti_quantita,
                crea_prodotti_scaffale,
                crea_prodotti_ripiano
        ));

        this.modifica_prodotto_nodes = new ArrayList<>();
        this.modifica_prodotto_nodes.addAll(List.of(
                crea_prodotti_nome,
                crea_prodotti_codice,
                crea_prodotti_fornitore,
                crea_prodotti_descrizione,
                crea_prodotti_quantita,
                crea_prodotti_scaffale,
                crea_prodotti_ripiano
        ));

        crea_prodotti_codice.textProperty().addListener((a,b,c) -> {
            Pattern pattern = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(c);
            if(!matcher.find()){
               c = "";
            }
            codiceBarreCrea.setImage(GeneratoreCodici.generaBarCode(c, (int) codiceBarreCrea.getFitWidth(), (int) codiceBarreCrea.getFitHeight()));
            qrCodeCrea.setImage(GeneratoreCodici.generaQrCode(c, (int) qrCodeCrea.getFitWidth(), (int) qrCodeCrea.getFitHeight()));
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showCreaProdotto(){
        mainPane.toFront();
        creaProdottoPane.toFront();
    }

    public  void showEliminaProdotto(){
        mainPane.toFront();
        eliminaProdottoPane.toFront();
    }

    public void showModificaProdotto(){
        mainPane.toFront();
        modificaProdottoPane.toFront();
    }

    public void showRicercaProdotto(){
        mainPane.toFront();
        ricercaProdottoPane.toFront();
    }

    public void modificaProdottoReset(ActionEvent actionEvent) {
        modifica_prodotto_nodes.forEach(node -> node.setText(""));
    }

    public void creaProdottoReset(ActionEvent actionEvent) {
        crea_prodotto_nodes.forEach(node -> node.setText(""));
    }

    public void eliminaProdottoReset(ActionEvent actionEvent) {
    }

    public void eliminaProdottoConferma(ActionEvent actionEvent) {
    }

    public void modificaProdottoModifica(ActionEvent actionEvent) {
    }

    public void creaProdottoCrea(ActionEvent actionEvent) {

        try{
            String nome = crea_prodotti_nome.getText();
            int quantita = Integer.parseInt(crea_prodotti_quantita.getText());
            String codice = crea_prodotti_codice.getText();
            String fornitore = crea_prodotti_fornitore.getText();
            String descrizione = crea_prodotti_descrizione.getText();
            String scaffale = crea_prodotti_scaffale.getText();
            String ripiano = crea_prodotti_ripiano.getText();

            System.out.println(nome + quantita + codice + fornitore + descrizione + scaffale + ripiano);
            // alert.informazione("Prodotto creato con successo");
            creaProdottoReset(null);
        }catch (Exception e){
            // alert.errore(e.getMessage());
        }
    }

    public void scegliImmagine(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File"); //TODO: mettere scelta file solo immagini
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
            crea_prodotti_immagine.setImage(SwingFXUtils.toFXImage(ImageIO.read(selectedFile),null));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
