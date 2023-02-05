package it.unipv.ingsw.magstudio.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.unipv.ingsw.magstudio.App;
import it.unipv.ingsw.magstudio.model.bean.Persona;
import it.unipv.ingsw.magstudio.model.dao.PersonaDAO;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;
import it.unipv.ingsw.magstudio.model.util.Encryption;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
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
    private MFXButton loginButton;

    @FXML
    private MFXProgressSpinner progressSpinner;
    private double x, y;
    private Stage stage;
    private HiveHubAlert alert;

    public void setStage(Stage stage) {
        this.stage = stage;
        alert = new HiveHubAlert(this.stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Impostazioni per movimento finestra
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

        //Inizio Animazione Logo
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

    //Azione di Click su bottone Login
    public void loginButtonPressed(){
        String nomeUtente = nomeUtenteField.getText();
        String password = Encryption.SHA256Encryptor(passwordField.getText());

        //Creazione del Task in Background per Thread
        Task<Optional<Persona>> task = new Task<>() {
            @Override
            protected Optional<Persona> call(){
                progressSpinner.setVisible(true);
                loginButton.setVisible(false);
                Optional<Persona> user = Optional.empty();

                try {
                    if(ConnectionFacade.getIstance().controllaCredenziali(nomeUtente, password)){

                        user = new PersonaDAO().selectByNomeUtente(new Persona(nomeUtente));

                        return user;
                    }else {
                        Platform.runLater(() -> {
                            alert.errore("Credenziali utente errate");
                        });
                    }
                }catch (SQLException e){
                    Platform.runLater(() -> {
                        alert.errore("Impossibile connettersi al Database");
                    });
                }finally {
                    progressSpinner.setVisible(false);
                    loginButton.setVisible(true);
                }
                return user;
            };
        };

        task.setOnSucceeded(event -> {
            Optional<Persona> user = task.getValue();
            if(user.isPresent()){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Dashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setTitle("HiveHub - Dashboard");

                    DashboardController controller = fxmlLoader.getController();

                    stage.setScene(scene);
                    controller.setStage(stage);

                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Thread per richiesta al DataBase
        new Thread(task).start();
    }

    //Azione pressione tasto Enter da tastiera
    public void onEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            loginButtonPressed();
        }
    }
}
