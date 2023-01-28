package it.unipv.ingsw.magstudio.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import it.unipv.ingsw.magstudio.model.bean.Persona;
import it.unipv.ingsw.magstudio.model.dao.PersonaDAO;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;
import it.unipv.ingsw.magstudio.model.util.Encryption;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
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
    private MFXButton loginButton;

    @FXML
    private MFXProgressSpinner progressSpinner;

    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;

    private ConnectionFacade connectionFacade;
    private double x, y;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionFacade = ConnectionFacade.getIstance();
        connectionFacade.setStrategy(ConnectionFacade.ConnectionStrategy.MYSQL);

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

    /**
     * metodo che crea un errore da testo in ingresso
     * @param text Stringa che destrive il tipo di errore
     */
    private void alertErrore(String text){

        this.dialogContent = MFXGenericDialogBuilder.build()
                .setShowMinimize(false)
                .setShowAlwaysOnTop(false)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialogs Preview")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.setMinSize(400, 100);

        MFXFontIcon errorIcon = new MFXFontIcon("mfx-exclamation-circle-filled", 25);
        dialogContent.setHeaderIcon(errorIcon);
        dialogContent.setHeaderText("Errore - HiveHub");
        Label textErrore = new Label(text);
        textErrore.setStyle("-fx-font-size: 18;");
        dialogContent.setContent(textErrore);
        dialogContent.getStyleClass().add("mfx-error-dialog");
        dialog.showDialog();
    }

    //Azione di Click su bottone Login
    public void loginButtonPressed(){
        String nomeUtente = nomeUtenteField.getText();
        String password = Encryption.SHA256Encryptor(passwordField.getText());

        //Creazione del Task in Background per Thread
        Task<Persona> task = new Task<>() {
            @Override
            protected Persona call(){
                progressSpinner.setVisible(true);
                loginButton.setVisible(false);
                Persona user = null;

                try {
                    connectionFacade.connect();
                    if(connectionFacade.controllaCredenziali(nomeUtente, password)){
                        PersonaDAO personaDAO = new PersonaDAO();

                        user = personaDAO.selectByNomeUtente(new Persona(nomeUtente));

                        return user;
                    }else {
                        Platform.runLater(() -> {
                            alertErrore("Credenziali utente errate");
                        });
                    }
                }catch (SQLException e){
                    Platform.runLater(() -> {
                        alertErrore("Impossibile connettersi al Database");
                    });
                }finally {
                    progressSpinner.setVisible(false);
                    loginButton.setVisible(true);
                    connectionFacade.close();
                }
                return user;
            };
        };

        task.setOnSucceeded(event -> {
            Persona user = task.getValue();
            if(user != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Benvenuto! - HiveHub");
                alert.setHeaderText("Benvenuto!");
                alert.setContentText(user.toString());

                alert.showAndWait();
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
