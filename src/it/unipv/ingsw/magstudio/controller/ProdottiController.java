package it.unipv.ingsw.magstudio.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import it.unipv.ingsw.magstudio.model.bean.Posizione;
import it.unipv.ingsw.magstudio.model.bean.Prodotto;
import it.unipv.ingsw.magstudio.model.dao.ProdottoDAO;
import it.unipv.ingsw.magstudio.model.util.GeneratoreCodici;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProdottiController implements Initializable {
    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane creaProdottoPane,
            eliminaProdottoPane,
            modificaProdottoPane,
            ricercaProdottoPane;

    @FXML
    private MFXTextField crea_prodotti_nome,
            crea_prodotti_quantita,
            crea_prodotti_codice,
            crea_prodotti_descrizione,
            crea_prodotti_fornitore,
            crea_prodotti_scaffale,
            crea_prodotti_ripiano;

    @FXML
    private Label ricerca_prodotto_codice;

    @FXML
    private Label ricerca_prodotto_descrizione;

    @FXML
    private Label ricerca_prodotto_nome;

    @FXML
    private Label elimina_prodotto_nome, elimina_prodotto_codice, elimina_prodotto_descrizione;

    @FXML
    private TableView<Posizione> ricerca_prodotto_posizioni;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_area;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_livello;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_scompartimento;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_quantita;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_scaffale;

    @FXML
    private MFXTextField ricerca_prodotto_codice_cerca, elimina_prodotto_codice_cerca;

    @FXML
    private ImageView codiceBarreCrea, qrCodeCrea, crea_prodotti_immagine;
    @FXML
    private ImageView codiceBarreCerca, codiceBarreElimina;

    @FXML
    private ImageView qrCodeCerca, qrCodeElimina;


    @FXML
    private List<MFXTextField> crea_prodotto_nodes, modifica_prodotto_nodes;
    private Stage stage;

    private HiveHubAlert alert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.crea_prodotto_nodes = new ArrayList<>();
        this.crea_prodotto_nodes.addAll(List.of(
                crea_prodotti_nome,
                crea_prodotti_codice,
                crea_prodotti_fornitore,
                crea_prodotti_descrizione,
                crea_prodotti_quantita,
                crea_prodotti_scaffale,
                crea_prodotti_ripiano
        ));

        this.modifica_prodotto_nodes = new ArrayList<>();
        this.modifica_prodotto_nodes.addAll(List.of(
                crea_prodotti_nome,
                crea_prodotti_codice,
                crea_prodotti_fornitore,
                crea_prodotti_descrizione,
                crea_prodotti_quantita,
                crea_prodotti_scaffale,
                crea_prodotti_ripiano
        ));

        crea_prodotti_codice.textProperty().addListener((a,b,c) -> {
            Pattern pattern = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(c);
            if(!matcher.find()){
               c = "";
            }
            codiceBarreCrea.setImage(GeneratoreCodici.generaBarCode(c, (int) codiceBarreCrea.getFitWidth(), (int) codiceBarreCrea.getFitHeight()));
            qrCodeCrea.setImage(GeneratoreCodici.generaQrCode(c, (int) qrCodeCrea.getFitWidth(), (int) qrCodeCrea.getFitHeight()));
        });

        ricerca_tabella_scaffale.setCellValueFactory(new PropertyValueFactory<>("scaffale"));
        ricerca_tabella_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        ricerca_tabella_livello.setCellValueFactory(new PropertyValueFactory<>("livello"));
        ricerca_tabella_scompartimento.setCellValueFactory(new PropertyValueFactory<>("scompartimento"));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        alert = new HiveHubAlert(this.stage);
    }

    public void showCreaProdotto(){
        mainPane.toFront();
        creaProdottoPane.toFront();
    }

    public  void showEliminaProdotto(){
        mainPane.toFront();
        eliminaProdottoPane.toFront();
    }

    public void showModificaProdotto(){
        mainPane.toFront();
        modificaProdottoPane.toFront();
    }

    public void showRicercaProdotto(){
        mainPane.toFront();
        ricercaProdottoPane.toFront();
    }

    public void modificaProdottoReset(ActionEvent actionEvent) {
        modifica_prodotto_nodes.forEach(node -> node.setText(""));
    }

    public void creaProdottoReset(ActionEvent actionEvent) {
        crea_prodotto_nodes.forEach(node -> node.setText(""));
    }

    public void eliminaProdottoReset(ActionEvent actionEvent) {
    }

    public void eliminaProdottoConferma(ActionEvent actionEvent) {
    }

    public void modificaProdottoModifica(ActionEvent actionEvent) {
    }

    public void creaProdottoCrea(ActionEvent actionEvent) {
        try{
            String nome = crea_prodotti_nome.getText();
            int quantita = Integer.parseInt(crea_prodotti_quantita.getText());
            String codice = crea_prodotti_codice.getText();
            //String fornitore = crea_prodotti_fornitore.getText();
            String descrizione = crea_prodotti_descrizione.getText();
            //String scaffale = crea_prodotti_scaffale.getText();
            //String ripiano = crea_prodotti_ripiano.getText();

            Prodotto p = new Prodotto(
                    nome,
                    quantita,
                    Integer.parseInt(codice),
                    descrizione
            );
            alert.informazione("Prodotto creato con successo");
            new ProdottoDAO().insertProdotto(p);
            creaProdottoReset(null);
        }catch (Exception e){
            alert.errore(e.getMessage());
        }
    }

    public void scegliImmagine(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File"); //TODO: mettere scelta file solo immagini
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
            crea_prodotti_immagine.setImage(SwingFXUtils.toFXImage(ImageIO.read(selectedFile),null));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void ricercaProdottoCerca(MouseEvent event) {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Integer codice = null;
        try {
            codice = Integer.parseInt(ricerca_prodotto_codice_cerca.getText());
            Optional<Prodotto> p = prodottoDAO.selectByCodice(new Prodotto(null,null,0, codice,null ));

            if(p.isPresent()){
                this.ricerca_prodotto_nome.setText(p.get().getNome());
                this.ricerca_prodotto_codice.setText(Integer.toString(p.get().getCodice()));
                this.ricerca_prodotto_descrizione.setText(p.get().getDescrizione());

                this.codiceBarreCerca.setImage(GeneratoreCodici.generaBarCode(Integer.toString(p.get().getCodice()), (int) codiceBarreCrea.getFitWidth(), (int) codiceBarreCrea.getFitHeight()));
                this.qrCodeCerca.setImage(GeneratoreCodici.generaQrCode(Integer.toString(p.get().getCodice()), (int) qrCodeCrea.getFitWidth(), (int) qrCodeCrea.getFitHeight()));

                ObservableList<Posizione> data = FXCollections.observableArrayList(
                        p.get().getPosizione().stream().toList()
                );
                ricerca_prodotto_posizioni.setItems(data);
            }else {
                alert.informazione("Nessun prodotto trovato con il codice fornito");
            }
        }catch (NumberFormatException ex){
            alert.errore("Il codice è composto da sole cifre");
        }
    }

    public void eliminaProdottoCerca(MouseEvent mouseEvent) {
        Integer codice = null;
        try{
            codice = Integer.parseInt(elimina_prodotto_codice_cerca.getText());
            Optional<Prodotto> p = new ProdottoDAO().selectByCodice(new Prodotto(null,0,codice,null));
            if(p.isPresent()){
                Prodotto prodotto = p.get();
                elimina_prodotto_nome.setText(prodotto.getNome());
                elimina_prodotto_codice.setText(Integer.toString(prodotto.getCodice()));
                elimina_prodotto_descrizione.setText(prodotto.getDescrizione());
                codiceBarreElimina.setImage(GeneratoreCodici.generaBarCode(Integer.toString(prodotto.getCodice()), (int) codiceBarreElimina.getFitWidth(), (int) codiceBarreElimina.getFitHeight()));
                qrCodeElimina.setImage(GeneratoreCodici.generaQrCode(Integer.toString(prodotto.getCodice()), (int) qrCodeElimina.getFitWidth(), (int) qrCodeElimina.getFitHeight()));
            }else {
                alert.informazione("Nessun prodotto trovato");
            }
        }catch (NumberFormatException ex){
            alert.errore("Il codice è formato da sole cifre");
        }


    }
}
