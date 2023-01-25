package it.unipv.ingsw.magstudio.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;
import it.unipv.ingsw.magstudio.model.util.Encryption;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane sideBar;

    @FXML
    private ImageView img;

    @FXML
    private MFXTextField nomeUtenteField;

    @FXML
    private MFXPasswordField passwordField;

    @FXML
    private Label erroreLabel;

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

    public void loginButtonPressed(ActionEvent actionEvent) throws SQLException, InterruptedException {
        String nomeUtente = nomeUtenteField.getText();
        String password = Encryption.SHA256Encryptor(passwordField.getText());

        ConnectionFacade connectionFacade = new ConnectionFacade();
        connectionFacade.setStrategy(ConnectionFacade.ConnectionStrategy.MYSQL_OVER_SSH);

        //TODO: migliorare velocità per togliere lag
        boolean esito = false;

        try {
            esito = connectionFacade.controllaCredenziali(nomeUtente,password);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectionFacade.close();
        }

        if (!esito){
            erroreLabel.setText("Utente non riconosciuto");
            erroreLabel.setVisible(true);
        }else {
            //cambiare scena
            erroreLabel.setVisible(false);
        }
    }
}
