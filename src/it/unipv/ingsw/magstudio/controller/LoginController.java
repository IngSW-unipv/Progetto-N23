package it.unipv.ingsw.magstudio.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.unipv.ingsw.magstudio.App;
import it.unipv.ingsw.magstudio.model.bean.Persona;
import it.unipv.ingsw.magstudio.model.dao.GestoreAccount;
import it.unipv.ingsw.magstudio.model.dao.PersonaDAO;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane sideBar, primoAccesso, login;

    @FXML
    private ImageView img;

    @FXML
    private MFXTextField nomeUtenteField, nuovoNomeUtenteField;

    @FXML
    private MFXPasswordField passwordField, ripetiPasswordField, nuovaPasswordField;

    @FXML
    private MFXButton loginButton, nuovoAccessoButton;

    @FXML
    private MFXProgressSpinner progressSpinner, progressSpinnerNuovoAccesso;
    private double x, y;
    private Stage stage;
    private HiveHubAlert alert;
    private int posizione;

    public void setStage(Stage stage) {
        this.stage = stage;
        alert = new HiveHubAlert(this.stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Impostazioni per movimento finestra
        sideBar.setOnMousePressed(mouseEvent -> {
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

        posizione = 0;
    }

    //Azione di Click su bottone Login
    public void loginButtonPressed() {
        String nomeUtente = nomeUtenteField.getText();
        String password = passwordField.getText();

        //Creazione del Task in Background per Thread
        Task<Optional<Persona>> task = new Task<>() {
            @Override
            protected Optional<Persona> call() {
                progressSpinner.setVisible(true);
                loginButton.setVisible(false);
                Optional<Persona> user = Optional.empty();

                try {
                    if (GestoreAccount.controllaCredenziali(nomeUtente, password)) {
                        user = new PersonaDAO().selectByNomeUtente(new Persona(nomeUtente));
                    } else {
                        Platform.runLater(() -> {
                            alert.errore("Credenziali utente errate");
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        alert.errore("Impossibile connettersi al Database");
                    });
                } finally {
                    progressSpinner.setVisible(false);
                    loginButton.setVisible(true);
                }
                return user;
            }

            ;
        };

        task.setOnSucceeded(event -> {
            Optional<Persona> user = task.getValue();
            if (user.isPresent()) {
                switchToDashboard();
                enableResizable();
            }
        });

        //Thread per richiesta al DataBase
        new Thread(task).start();
    }

    //Azione pressione tasto Enter da tastiera
    public void onEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && posizione < 1) {
            loginButtonPressed();
        } else if (keyEvent.getCode() == KeyCode.ENTER) {
            nuovoAccessoAction(null);
        }
    }

    public void primoAccessoMostra(MouseEvent mouseEvent) {
        primoAccesso.toFront();
        posizione = 1;
    }

    public void nuovoAccessoAction(ActionEvent mouseEvent) {
        String nomeUtente = nuovoNomeUtenteField.getText();
        String password = nuovaPasswordField.getText();
        String confermaPassword = ripetiPasswordField.getText();

        //Creazione del Task in Background per Thread
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                nuovoAccessoButton.setVisible(false);
                progressSpinnerNuovoAccesso.setVisible(true);
                boolean esito = false;

                try {
                    if (GestoreAccount.impostaPassword(nomeUtente, password)) {
                        esito = true;
                    } else {
                        Platform.runLater(() -> {
                            alert.errore("Errore, utente non trovato o già inizializzato ");
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        alert.errore("Impossibile connettersi al Database");
                    });
                } finally {
                    progressSpinnerNuovoAccesso.setVisible(false);
                    nuovoAccessoButton.setVisible(true);
                }
                return esito;
            }

            ;
        };

        task.setOnSucceeded(event -> {
            Boolean esito = task.getValue();
            if (esito) {
                Platform.runLater(() -> {
                    alert.informazione("Password aggiornata con successo");
                    enableResizable();
                });
                switchToDashboard();
            }
        });

        //Thread per richiesta al DataBase
        if (password.equals(confermaPassword) && !password.isBlank() && !password.isEmpty())
            new Thread(task).start();
        else
            alert.errore("Le password non coincidono");
    }

    private void switchToDashboard() {
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

    public void loginIndietro(ActionEvent actionEvent) {
        login.toFront();
        posizione = 0;
    }
    private void enableResizable() {
        if (stage != null) {
            stage.setResizable(true);
        }

    }
}
