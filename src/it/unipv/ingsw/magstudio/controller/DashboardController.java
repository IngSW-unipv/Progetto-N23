package it.unipv.ingsw.magstudio.controller;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import it.unipv.ingsw.magstudio.model.bean.*;
import it.unipv.ingsw.magstudio.model.dao.PersonaDAO;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class DashboardController implements Initializable {
    @FXML
    private VBox sideBar;
    @FXML
    private BorderPane creaUtentePane, modificaUtentePane;
    @FXML
    private MFXTextField crea_utente_cap, modifica_utente_cap;

    @FXML
    private MFXTextField crea_utente_cf, modifica_utente_cf;

    @FXML
    private MFXTextField crea_utente_citta, modifica_utente_citta;

    @FXML
    private MFXTextField crea_utente_civico, modifica_utente_civico;

    @FXML
    private MFXTextField crea_utente_cognome, modifica_utente_cognome;

    @FXML
    private MFXDatePicker crea_utente_dataNascita, modifica_utente_dataNascita;

    @FXML
    private MFXTextField crea_utente_email, modifica_utente_email;

    @FXML
    private MFXTextField crea_utente_indirizzo, modifica_utente_indirizzo;

    @FXML
    private MFXTextField crea_utente_nome, modifica_utente_nome;

    @FXML
    private MFXTextField crea_utente_nomeUtente, modifica_utente_nomeUtente;

    @FXML
    private MFXTextField crea_utente_provincia, modifica_utente_provincia;

    @FXML
    private MFXComboBox<Regione> crea_utente_regione, modifica_utente_regione;

    @FXML
    private MFXTextField crea_utente_telefono, modifica_utente_telefono;

    @FXML
    private MFXComboBox<TipoStrada> crea_utente_tipoIndirizzo, modifica_utente_tipoIndirizzo;

    @FXML
    private MFXProgressSpinner progressSpinner_modifica;


    private List<MFXTextField> crea_utente_nodes;
    private List<MFXTextField> modifica_utente_nodes;

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

        this.modifica_utente_nodes = new ArrayList<>();
        this.modifica_utente_nodes.addAll(List.of(
                modifica_utente_cap,
                modifica_utente_cf,
                modifica_utente_citta,
                modifica_utente_civico,
                modifica_utente_cognome,
                modifica_utente_dataNascita,
                modifica_utente_email,
                modifica_utente_indirizzo,
                modifica_utente_nome,
                modifica_utente_nomeUtente,
                modifica_utente_provincia,
                modifica_utente_telefono
        ));

        Arrays.stream(Regione.values()).forEach(regione -> {
            crea_utente_regione.getItems().add(regione);
            modifica_utente_regione.getItems().add(regione);
        });

        Arrays.stream(TipoStrada.values()).forEach(regione -> {
            crea_utente_tipoIndirizzo.getItems().add(regione);
            modifica_utente_tipoIndirizzo.getItems().add(regione);
        });
        crea_utente_dataNascita.setConverterSupplier(()->new DateStringConverter("yyyy/MM/dd",crea_utente_dataNascita.getLocale()));
        modifica_utente_dataNascita.setConverterSupplier(()->new DateStringConverter("yyyy/MM/dd",modifica_utente_dataNascita.getLocale()));
    }

    public void modificaUtenteAction(MouseEvent mouseEvent) {
        modificaUtentePane.toFront();
    }

    public void creaUtenteAction(MouseEvent mouseEvent) {
        creaUtentePane.toFront();
    }


    //metodo per la creazione di un nuovo utente
    public void creaUtenteCrea(ActionEvent actionEvent) {
        // Raccolgo le informazioni dalla grafica
        try {
            // Creo l'oggetto Persona
            new PersonaDAO().insertPersona(new Persona(
                    crea_utente_nomeUtente.getText(),
                    crea_utente_nome.getText(),
                    crea_utente_cognome.getText(),
                    crea_utente_cf.getText(),
                    new Date(crea_utente_dataNascita.getText()),
                    new Indirizzo(
                            TipoStrada.valueOf(crea_utente_tipoIndirizzo.getText()),
                            crea_utente_indirizzo.getText(),
                            crea_utente_civico.getText(),
                            Integer.parseInt(crea_utente_cap.getText()),
                            crea_utente_citta.getText(),
                            crea_utente_provincia.getText(),
                            Regione.valueOf(crea_utente_regione.getText())
                    ),
                    new Contatto(
                            crea_utente_email.getText(),
                            Long.parseLong(crea_utente_telefono.getText())
                    )
            ));  // Carico la persona
        }catch (Exception e){
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }

    //metodo per la modifica di un utente già esistente
    public void modificaUtenteModifica(ActionEvent actionEvent) {
        // Raccolgo le informazioni dalla grafica
        try {
            Persona p = new Persona(
                    modifica_utente_nomeUtente.getText(),
                    modifica_utente_nome.getText(),
                    modifica_utente_cognome.getText(),
                    modifica_utente_cf.getText(),
                    new Date(),
                    new Indirizzo(
                            TipoStrada.valueOf(modifica_utente_tipoIndirizzo.getText()),
                            modifica_utente_indirizzo.getText(),
                            modifica_utente_civico.getText(),
                            Integer.parseInt(modifica_utente_cap.getText()),
                            modifica_utente_citta.getText(),
                            modifica_utente_provincia.getText(),
                            Regione.valueOf(modifica_utente_regione.getText())
                    ),
                    new Contatto(
                            modifica_utente_email.getText(),
                            Long.parseLong(modifica_utente_telefono.getText())
                    )
            );  // Creo l'oggetto Persona
            new PersonaDAO().updatePersona(p);  // Aggiorno la persona
        }catch (Exception e){
            e.printStackTrace();
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }

    public void creaUtenteReset(ActionEvent actionEvent) {
        crea_utente_nodes.forEach(node -> node.setText(""));
    }

    public void exitButton(MouseEvent mouseEvent) {
        stage.close();
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

    public void eliminaUtente(){
        // Raccolgo le informazioni dalla grafica
        try {
            Persona p = new Persona(null);  // Creo l'oggetto Persona
            new PersonaDAO().dropPersona(p);  // Elimino la persona
        }catch (Exception e){
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }

    public void modifica_utente_cerca(MouseEvent mouseEvent){
        ((MaterialIconView)mouseEvent.getSource()).setVisible(false);
        progressSpinner_modifica.setVisible(true);
        Task<Optional<Persona>> task = new Task<Optional<Persona>>() {
            @Override
            protected Optional<Persona> call() {
                Optional<Persona> p = Optional.empty();
                try {
                    p = new PersonaDAO().selectByNomeUtente(new Persona(modifica_utente_nomeUtente.getText()));
                    if(p.isPresent()){
                        Persona p1 = p.get();
                        modifica_utente_nomeUtente.setText(p1.getNomeUtente());
                        modifica_utente_nome.setText(p1.getNome());
                        modifica_utente_cognome.setText(p1.getCognome());
                        modifica_utente_cf.setText(p1.getCf());
                        modifica_utente_dataNascita.setText(p1.getDataNascita());
                        modifica_utente_tipoIndirizzo.selectItem(p1.getIndirizzo().getTipoStrada());
                        modifica_utente_indirizzo.setText(p1.getIndirizzo().getNome());
                        modifica_utente_civico.setText(p1.getIndirizzo().getCivico());
                        modifica_utente_citta.setText(p1.getIndirizzo().getCitta());
                        modifica_utente_cap.setText(String.valueOf(p1.getIndirizzo().getCap()));
                        modifica_utente_provincia.setText(p1.getIndirizzo().getProvincia());
                        modifica_utente_regione.selectItem(p1.getIndirizzo().getRegione());
                        if(p1.getContatto().getEmail().isEmpty())
                            modifica_utente_email.setText("");
                        else
                            modifica_utente_email.setText(p1.getContatto().getEmail().get());
                        if(p1.getContatto().getTelefono().isEmpty())
                            modifica_utente_telefono.setText("");
                        else
                            modifica_utente_telefono.setText(p1.getContatto().getTelefono().get().toString());
                    }else {
                        Platform.runLater(() -> {
                            alert.informazione("Utente non trovato");
                        });
                    }
                } catch (SQLException e) {
                    Platform.runLater(() -> {
                        alert.errore(e.getMessage());
                    });
                }
                return p;
            }
        };

        task.setOnSucceeded(event -> {
            progressSpinner_modifica.setVisible(false);
            ((MaterialIconView)mouseEvent.getSource()).setVisible(true);
        });

        new Thread(task).start();
    }

    public void modificaUtenteReset(ActionEvent actionEvent) {
        modifica_utente_nodes.forEach(node -> node.setText(""));
    }
}
