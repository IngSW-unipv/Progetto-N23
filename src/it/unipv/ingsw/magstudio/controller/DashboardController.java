package it.unipv.ingsw.magstudio.controller;

import it.unipv.ingsw.magstudio.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private HBox dragBox;
    @FXML
    private BorderPane mainPane;
    @FXML
    private StackPane centerPane;

    private Stage stage;
    private double x,y;

    private UtentiController utentiController;
    private ProdottiController prodottiController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dragBox.setOnMousePressed (mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        dragBox.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
            stage.setOpacity(0.6);
        });

        dragBox.setOnDragDone(mouseEvent -> {
            stage.setOpacity(1);
        });

        dragBox.setOnMouseReleased(mouseEvent -> {
            stage.setOpacity(1);
        });

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Prodotti.fxml"));
        try {

            centerPane.getChildren().add(fxmlLoader.load());
            prodottiController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Utenti.fxml"));
        try {
            centerPane.getChildren().add(fxmlLoader.load());
            utentiController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        //this.stage.setMaximized(true);
        utentiController.setStage(this.stage);
    }

    public void exitButton(MouseEvent mouseEvent) {
        stage.close();
    }

    public void creaUtenteAction(MouseEvent mouseEvent) {
        utentiController.showCreaUtenti();
    }

    public void modificaUtenteAction(MouseEvent mouseEvent) {
        utentiController.showModificaUtenti();
    }

    public void ricercaUtenteAction(MouseEvent mouseEvent) {
        utentiController.showCercaUtenti();
    }

    public void eliminaUtenteAction(MouseEvent mouseEvent) {
        utentiController.showRimuoviUtenti();
    }

    public void ricercaProdottoAction(MouseEvent mouseEvent) {
        prodottiController.showRicercaProdotto();
    }

    public void modificaProdottoAction(MouseEvent mouseEvent) {
        prodottiController.showModificaProdotto();
    }

    public void eliminaProdottoAction(MouseEvent mouseEvent) {
        prodottiController.showEliminaProdotto();
    }

    public void creaProdottoAction(MouseEvent mouseEvent) {
        prodottiController.showCreaProdotto();
    }
}
