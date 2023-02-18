package it.unipv.ingsw.magstudio.controller;

import it.unipv.ingsw.magstudio.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private VBox sideBar;
    @FXML
    private BorderPane mainPane;

    private Stage stage;
    private double x,y;

    private UtentiController utentiController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sideBar.setOnMousePressed (mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        sideBar.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
            stage.setOpacity(0.6);
        });

        sideBar.setOnDragDone(mouseEvent -> {
            stage.setOpacity(1);
        });

        sideBar.setOnMouseReleased(mouseEvent -> {
            stage.setOpacity(1);
        });

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Utenti.fxml"));
        try {
            mainPane.setCenter(fxmlLoader.load());
            utentiController = fxmlLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setMaximized(true);
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
}
