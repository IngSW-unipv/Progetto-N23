package it.unipv.ingsw.magstudio.controller;

import it.unipv.ingsw.magstudio.model.util.GeneratoreCodici;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProdottiController implements Initializable {
    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane creaProdottoPane, eliminaProdottoPane,
            modificaProdottoPane, ricercaProdottoPane;

    @FXML
    private ImageView codiceBarreCrea, qrCodeCrea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codiceBarreCrea.getParent().toFront();
        codiceBarreCrea.setImage(GeneratoreCodici.generaBarCode("https://github.com/IngSW-unipv/Progetto-N23.git", (int) codiceBarreCrea.getFitWidth(), (int) codiceBarreCrea.getFitHeight()));
        qrCodeCrea.getParent().toFront();
        qrCodeCrea.setImage(GeneratoreCodici.generaQrCode("https://github.com/IngSW-unipv/Progetto-N23.git", (int) qrCodeCrea.getFitWidth(), (int) qrCodeCrea.getFitHeight()));
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
    }

    public void creaProdottoReset(ActionEvent actionEvent) {
    }

    public void eliminaProdottoReset(ActionEvent actionEvent) {
    }
}
