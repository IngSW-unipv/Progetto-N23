package it.unipv.ingsw.magstudio.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import it.unipv.ingsw.magstudio.model.bean.Persona;
import it.unipv.ingsw.magstudio.model.bean.Regione;
import it.unipv.ingsw.magstudio.model.bean.TipoStrada;
import it.unipv.ingsw.magstudio.model.dao.PersonaDAO;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class DashboardController implements Initializable {
    @FXML
    private VBox sideBar;
    @FXML
    private BorderPane creaUtentePane, modificaUtentePane;
    @FXML
    private MFXTextField crea_utente_cap;

    @FXML
    private MFXTextField crea_utente_cf;

    @FXML
    private MFXTextField crea_utente_citta;

    @FXML
    private MFXTextField crea_utente_civico;

    @FXML
    private MFXTextField crea_utente_cognome;

    @FXML
    private MFXDatePicker crea_utente_dataNascita;

    @FXML
    private MFXTextField crea_utente_email;

    @FXML
    private MFXTextField crea_utente_indirizzo;

    @FXML
    private MFXTextField crea_utente_nome;

    @FXML
    private MFXTextField crea_utente_nomeUtente;

    @FXML
    private MFXTextField crea_utente_provincia;

    @FXML
    private MFXComboBox<Regione> crea_utente_regione;

    @FXML
    private MFXTextField crea_utente_telefono;

    @FXML
    private MFXComboBox<TipoStrada> crea_utente_tipoIndirizzo;

    private List<MFXTextField> crea_utente_nodes;
    private Stage stage;
    private double x,y;
    private HiveHubAlert alert;

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setMaximized(true);
        alert = new HiveHubAlert(this.stage);
    }

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

        this.crea_utente_nodes = new ArrayList<>();
        this.crea_utente_nodes.addAll(List.of(
                crea_utente_cap,
                crea_utente_cf,
                crea_utente_citta,
                crea_utente_civico,
                crea_utente_cognome,
                crea_utente_dataNascita,
                crea_utente_email,
                crea_utente_indirizzo,
                crea_utente_nome,
                crea_utente_nomeUtente,
                crea_utente_provincia,
                crea_utente_telefono
        ));

        Arrays.stream(Regione.values()).forEach(regione -> {
            crea_utente_regione.getItems().add(regione);
        });

        Arrays.stream(TipoStrada.values()).forEach(regione -> {
            crea_utente_tipoIndirizzo.getItems().add(regione);
        });
    }

    public void modificaUtenteAction(MouseEvent mouseEvent) {
        modificaUtentePane.toFront();
    }

    public void creaUtenteAction(MouseEvent mouseEvent) {
        creaUtentePane.toFront();
    }


    public void creaUtenteCrea(ActionEvent actionEvent) {

    }

    public void creaUtenteReset(ActionEvent actionEvent) {
        crea_utente_nodes.forEach(node -> node.setText(""));
    }

    public void exitButton(MouseEvent mouseEvent) {
        stage.close();
    }

    public void creaUtente(){
        // Raccolgo le informazioni dalla grafica
        try {
            // Creo l'oggetto Persona
            new PersonaDAO().insertPersona(new Persona(null));  // Carico la persona
        }catch (Exception e){
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }

    public void cercaUtente(){
        String nomeUtente = crea_utente_nomeUtente.getText(); // Prendo il nome utente
        try {
            // Cerco l'utente attraverso il nome utente
            Optional<Persona> p = new PersonaDAO().selectByNomeUtente(new Persona(nomeUtente));
            // Controllo se è stato trovato un utente
            if(p.isPresent()){
                //Mostro le informazioni
            }else {
                alert.errore("Utente non trovato!"); //Segnalo di non aver trovato nulla
            }
        }catch (Exception e){
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }

    public void modificaUtente(){
        // Raccolgo le informazioni dalla grafica
        try {
            Persona p = new Persona(null);  // Creo l'oggetto Persona
            new PersonaDAO().updatePersona(p);  // Aggiorno la persona
        }catch (Exception e){
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }

    public void eliminaUtente(){
        // Raccolgo le informazioni dalla grafica
        try {
            Persona p = new Persona(null);  // Creo l'oggetto Persona
            new PersonaDAO().dropPersona(p);  // Elimino la persona
        }catch (Exception e){
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }
}
