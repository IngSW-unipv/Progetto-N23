package it.unipv.ingsw.magstudio.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane sideBar;

    @FXML
    private ImageView img;

    @FXML
    private MFXPasswordField passwordField;

    private double x, y;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        img.setVisible(true);

        double time = 1.5;
        ParallelTransition parallelTransition = new ParallelTransition();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(time), img);
        translateTransition.setFromY(300);
        translateTransition.setToY(img.getY());

        parallelTransition.getChildren().add(translateTransition);

        FadeTransition transition = new FadeTransition(Duration.seconds(time), img);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);

        parallelTransition.getChildren().add(transition);

        parallelTransition.play();
    }

    public void closeAction(MouseEvent mouseEvent) {
        stage.close();
    }
}
