package it.unipv.ingsw.magstudio.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
}
