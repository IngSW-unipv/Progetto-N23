package it.unipv.ingsw.magstudio.controller;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import it.unipv.ingsw.magstudio.model.bean.*;
import it.unipv.ingsw.magstudio.model.dao.PersonaDAO;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class UtentiController implements Initializable {
    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane creaUtentePane, modificaUtentePane, ricercaUtentePane, eliminaUtentePane;
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
    private MFXTextField crea_utente_nomeUtente, modifica_utente_nomeUtente,
            cerca_utente_nomeUtente, elimina_utente_nomeUtente;

    @FXML
    private MFXTextField crea_utente_provincia, modifica_utente_provincia;

    @FXML
    private MFXComboBox<Regione> crea_utente_regione, modifica_utente_regione;

    @FXML
    private MFXTextField crea_utente_telefono, modifica_utente_telefono;

    @FXML
    private MFXComboBox<TipoStrada> crea_utente_tipoIndirizzo, modifica_utente_tipoIndirizzo;

    @FXML
    private MFXProgressSpinner progressSpinner_modifica, progressSpinner_cerca, progressSpinner_elimina;

    @FXML
    private Label cerca_utente_nome_utente, cerca_utente_nome_cognome, cerca_utente_cf,
            cerca_utente_dataNascita, cerca_utente_indirizzo, cerca_utente_telefono, cerca_utente_email;

    @FXML
    private Label elimina_utente_nome_utente, elimina_utente_nome_cognome, elimina_utente_cf,
            elimina_utente_dataNascita, elimina_utente_indirizzo, elimina_utente_telefono, elimina_utente_email;


    private List<MFXTextField> crea_utente_nodes;
    private List<MFXTextField> modifica_utente_nodes;
    private List<Label> cerca_utente_nodes;
    private List<Label> elimina_utente_nodes;

    private HiveHubAlert alert;
    private Stage stage;

    //          ************
    //          * GENERALE *
    //          *++*++++++++
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        cerca_utente_nodes = new ArrayList<>();
        cerca_utente_nodes.addAll(List.of(
                cerca_utente_nome_utente,
                cerca_utente_nome_cognome,
                cerca_utente_cf,
                cerca_utente_dataNascita,
                cerca_utente_indirizzo,
                cerca_utente_telefono,
                cerca_utente_email
        ));
        cerca_utente_nodes.forEach(element -> element.setText(""));

        elimina_utente_nodes = new ArrayList<>();
        elimina_utente_nodes.addAll(List.of(
                elimina_utente_nome_utente,
                elimina_utente_nome_cognome,
                elimina_utente_cf,
                elimina_utente_dataNascita,
                elimina_utente_indirizzo,
                elimina_utente_telefono,
                elimina_utente_email
        ));
        elimina_utente_nodes.forEach(element -> element.setText(""));

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

    public void setStage(Stage stage) {
        this.stage = stage;
        alert = new HiveHubAlert(this.stage);
    }

    public void showCreaUtenti(){
        mainPane.toFront();
        creaUtentePane.toFront();
    }

    public void showModificaUtenti(){
        mainPane.toFront();
        modificaUtentePane.toFront();
    }

    public void showCercaUtenti(){
        mainPane.toFront();
        ricercaUtentePane.toFront();
        cerca_utente_nodes.forEach(element -> element.setText(""));
    }

    public void showRimuoviUtenti(){
        mainPane.toFront();
        eliminaUtentePane.toFront();
        elimina_utente_nodes.forEach(element -> element.setText(""));
    }

    //          ***************
    //          * CREA UTENTE *
    //          ***************

    //metodo per la creazione di un nuovo utente
    public void creaUtenteCrea(ActionEvent actionEvent) {
        // Raccolgo le informazioni dalla grafica
        try {
            // Creo l'oggetto Persona
            Date data = new Date(crea_utente_dataNascita.getText());
            Indirizzo indirizzo = new Indirizzo(
                    TipoStrada.valueOf(crea_utente_tipoIndirizzo.getText()),
                    crea_utente_indirizzo.getText(),
                    crea_utente_civico.getText(),
                    Integer.parseInt(crea_utente_cap.getText()),
                    crea_utente_citta.getText(),
                    crea_utente_provincia.getText(),
                    Regione.valueOf(crea_utente_regione.getText())
            );

            Contatto contatto;
            String email = crea_utente_email.getText();
            String telefono = crea_utente_telefono.getText();

            if(email.isBlank() || email.isEmpty() && !telefono.isEmpty() && !telefono.isBlank())
                contatto = new Contatto(Long.parseLong(telefono));
            else if(!email.isBlank() && !email.isEmpty() && telefono.isEmpty() || telefono.isBlank())
                contatto = new Contatto(email);
            else
                contatto = new Contatto(email, Long.parseLong(telefono));

            Persona p = new Persona(
                    crea_utente_nomeUtente.getText(),
                    crea_utente_nome.getText(),
                    crea_utente_cognome.getText(),
                    crea_utente_cf.getText(),
                    data,
                    indirizzo,
                    contatto
            );

            new PersonaDAO().insertPersona(p);  // Carico la persona
            alert.informazione("Utente creato con successo");
            creaUtenteReset(null);
        }catch (Exception e){
            alert.errore(e.getMessage());    // Mostro messaggio errore
        }
    }

    public void creaUtenteReset(ActionEvent actionEvent) {
        crea_utente_nodes.forEach(node -> node.setText(""));
    }

    //          *******************
    //          * MODIFICA UTENTE *
    //          *******************

    //metodo per la modifica di un utente già esistente
    public void modificaUtenteModifica(ActionEvent actionEvent) {
        // Raccolgo le informazioni dalla grafica
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                boolean esito = false;
                try {
                    // Creo l'oggetto Persona
                    Indirizzo indirizzo = new Indirizzo(
                            TipoStrada.valueOf(modifica_utente_tipoIndirizzo.getText()),
                            modifica_utente_indirizzo.getText(),
                            modifica_utente_civico.getText(),
                            Integer.parseInt(modifica_utente_cap.getText()),
                            modifica_utente_citta.getText(),
                            modifica_utente_provincia.getText(),
                            Regione.valueOf(modifica_utente_regione.getText())
                    );

                    Contatto contatto;
                    String email = modifica_utente_email.getText();
                    String telefono = modifica_utente_telefono.getText();

                    if(email.isBlank() || email.isEmpty() && !telefono.isEmpty() && !telefono.isBlank())
                        contatto = new Contatto(Long.parseLong(telefono));
                    else if(!email.isBlank() && !email.isEmpty() && telefono.isEmpty() || telefono.isBlank())
                        contatto = new Contatto(email);
                    else
                        contatto = new Contatto(email, Long.parseLong(telefono));

                    Persona p = new Persona(
                            modifica_utente_nomeUtente.getText(),
                            modifica_utente_nome.getText(),
                            modifica_utente_cognome.getText(),
                            modifica_utente_cf.getText(),
                            new Date(),
                            indirizzo,
                            contatto
                    );

                    esito = new PersonaDAO().updatePersona(p);  // Aggiorno la persona
                }catch (Exception e){
                    Platform.runLater(() -> {
                        alert.errore(e.getMessage());    // Mostro messaggio errore
                    });
                }
                return esito;
            }
        };

        task.setOnSucceeded(event -> {
            Boolean esito = task.getValue();
            if(esito){
                Platform.runLater(() -> {
                    alert.informazione("Utente modificato con successo");
                    modificaUtenteReset(null);
                });
            }else {
                Platform.runLater(() -> {
                    alert.informazione("Errore nella modifica dell'utente");
                });
            }
        });

        new Thread(task).start();
    }

    public void modifica_utente_cerca(MouseEvent mouseEvent){
        ((MaterialIconView)mouseEvent.getSource()).setVisible(false);
        progressSpinner_modifica.setVisible(true);

        Task<Optional<Persona>> task = new Task<>() {
            @Override
            protected Optional<Persona> call() {
                return cercaUtenteTask(new Persona(modifica_utente_nomeUtente.getText()));
            }
        };

        task.setOnSucceeded(event -> {
            Optional<Persona> p = task.getValue();
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
                modifica_utente_nomeUtente.setDisable(true);
            }else {
                modificaUtenteReset(null);
                Platform.runLater(() -> {
                    alert.informazione("Utente non trovato");
                });
            }
            progressSpinner_modifica.setVisible(false);
            ((MaterialIconView)mouseEvent.getSource()).setVisible(true);
        });

        new Thread(task).start();
    }

    public void modificaUtenteReset(ActionEvent actionEvent) {
        modifica_utente_nodes.forEach(node -> node.setText(""));
        modifica_utente_nomeUtente.setDisable(false);
    }


    //          ****************
    //          * CERCA UTENTE *
    //          ****************
    public void cercaUtenteCerca(MouseEvent mouseEvent) {
        ((MaterialIconView)mouseEvent.getSource()).setVisible(false); // Nascondo tasto cerca
        progressSpinner_cerca.setVisible(true); // Mostro spinner

        Task<Optional<Persona>> task = new Task<>() { // Ricerco l'utente
            @Override
            protected Optional<Persona> call() {
                return cercaUtenteTask(new Persona(cerca_utente_nomeUtente.getText()));
            }
        };

        // Aggiorno interfaccia grafica
        task.setOnSucceeded(event -> {
            Optional<Persona> p = task.getValue();
            if(p.isPresent()){
                Persona p1 = p.get();
                cerca_utente_nome_utente.setText(p1.getNomeUtente());
                cerca_utente_nome_cognome.setText(p1.getNome() + " " + p1.getCognome());
                cerca_utente_cf.setText(p1.getCf());
                cerca_utente_dataNascita.setText(p1.getDataNascita());
                cerca_utente_indirizzo.setText(p1.getIndirizzo().toString());
                if(p1.getContatto().getEmail().isEmpty())
                    cerca_utente_email.setText("Non indicata");
                else
                    cerca_utente_email.setText(p1.getContatto().getEmail().get());
                if(p1.getContatto().getTelefono().isEmpty())
                    cerca_utente_telefono.setText("Non indicato");
                else
                    cerca_utente_telefono.setText(p1.getContatto().getTelefono().get().toString());
            }else {
                Platform.runLater(() -> {
                    alert.informazione("Utente non trovato");
                });
            }
            ((MaterialIconView)mouseEvent.getSource()).setVisible(true); // Mostro tasto cerca
            progressSpinner_cerca.setVisible(false);// Nascondo spinner
        });

        new Thread(task).start();
    }

    private Optional<Persona> cercaUtenteTask(Persona utente){
        Optional<Persona> p = Optional.empty();
        try {
            p = new PersonaDAO().selectByNomeUtente(utente);
        } catch (SQLException e) {
            Platform.runLater(() -> {
                alert.errore(e.getMessage());
            });
        }
        return p;
    }


    //          ******************
    //          * ELIMINA UTENTE *
    //          ******************
    public void eliminaUtenteCerca(MouseEvent mouseEvent) {
        ((MaterialIconView)mouseEvent.getSource()).setVisible(false); // Nascondo tasto cerca
        progressSpinner_elimina.setVisible(true); // Mostro spinner

        Task<Optional<Persona>> task = new Task<>() { // Ricerco l'utente
            @Override
            protected Optional<Persona> call() {
                return cercaUtenteTask(new Persona(elimina_utente_nomeUtente.getText()));
            }
        };

        // Aggiorno interfaccia grafica
        task.setOnSucceeded(event -> {
            Optional<Persona> p = task.getValue();
            if(p.isPresent()){
                Persona p1 = p.get();
                elimina_utente_nome_utente.setText(p1.getNomeUtente());
                elimina_utente_nome_cognome.setText(p1.getNome() + " " + p1.getCognome());
                elimina_utente_cf.setText(p1.getCf());
                elimina_utente_dataNascita.setText(p1.getDataNascita());
                elimina_utente_indirizzo.setText(p1.getIndirizzo().toString());
                if(p1.getContatto().getEmail().isEmpty())
                    elimina_utente_email.setText("Non indicata");
                else
                    elimina_utente_email.setText(p1.getContatto().getEmail().get());
                if(p1.getContatto().getTelefono().isEmpty())
                    elimina_utente_telefono.setText("Non indicato");
                else
                    elimina_utente_telefono.setText(p1.getContatto().getTelefono().get().toString());
            }else {
                Platform.runLater(() -> {
                    alert.informazione("Utente non trovato");
                    eliminaUtenteReset(null);
                });
            }
            ((MaterialIconView)mouseEvent.getSource()).setVisible(true); // Mostro tasto cerca
            progressSpinner_elimina.setVisible(false);// Nascondo spinner
        });

        new Thread(task).start();
    }

    public void eliminaUtenteReset(ActionEvent actionEvent) {
        elimina_utente_nodes.forEach(element -> element.setText(""));
    }

    public void eliminaUtenteElimina(ActionEvent actionEvent) {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                boolean esito = false;
                try {
                    esito = new PersonaDAO().dropPersona(new Persona(elimina_utente_nomeUtente.getText()));
                } catch (SQLException e) {
                    Platform.runLater(() -> {
                        alert.errore(e.getMessage());
                    });
                }
                return esito;
            }
        };

        task.setOnSucceeded(event -> {
            Boolean esito = task.getValue();
            if(esito){
                Platform.runLater(() -> {
                    alert.informazione("Utente eliminato con successo");
                });
            }else {
                Platform.runLater(() -> {
                    alert.informazione("Utente non trovato");
                });
            }
            eliminaUtenteReset(null);
        });

        new Thread(task).start();
    }
}
