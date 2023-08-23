package it.unipv.ingsw.magstudio.controller;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import it.unipv.ingsw.magstudio.model.bean.Coordinata;
import it.unipv.ingsw.magstudio.model.bean.Posizione;
import it.unipv.ingsw.magstudio.model.bean.Prodotto;
import it.unipv.ingsw.magstudio.model.dao.ProdottoDAO;
import it.unipv.ingsw.magstudio.model.util.GeneratoreCodici;
import it.unipv.ingsw.magstudio.model.util.HiveHubAlert;
import it.unipv.ingsw.magstudio.model.util.ImageFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.exception.ConstraintViolationException;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;
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
    MFXProgressSpinner progressSpinner_ricerca;

    @FXML
    private MFXTextField crea_prodotti_nome,
            crea_prodotti_codice,
            crea_prodotti_descrizione,
            crea_prodotti_area,
            crea_prodotti_scompartimento,
            crea_prodotti_livello,
            crea_prodotti_scaffale,
            crea_prodotti_qnt;

    @FXML
    private Label ricerca_prodotto_codice, ricerca_prodotto_descrizione, ricerca_prodotto_nome;

    @FXML
    private Label elimina_prodotto_nome, elimina_prodotto_codice, elimina_prodotto_descrizione;
    @FXML
    private MFXTextField modifica_prodotti_nome,
            modifica_prodotti_codice,
            modifica_prodotti_descrizione,
            modifica_prodotti_area,
            modifica_prodotti_livello,
            modifica_prodotti_scaffale,
            modifica_prodotti_scompartimento,
            modifica_prodotti_qnt;

    @FXML
    private TableView<Posizione> ricerca_prodotto_posizioni,
            crea_prodotto_posizioni,
            modifica_prodotto_posizioni,
            elimina_prodotto_posizioni;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_area, crea_tabella_area, elimina_tabella_area, modifica_tabella_area;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_livello, crea_tabella_livello, elimina_tabella_livello, modifica_tabella_livello;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_scompartimento, crea_tabella_scompartimento, elimina_tabella_scompartimento, modifica_tabella_scompartimento;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_quantita, crea_tabella_quantita, elimina_tabella_quantita, modifica_tabella_quantita;

    @FXML
    private TableColumn<Posizione, Integer> ricerca_tabella_scaffale, crea_tabella_scaffale, elimina_tabella_scaffale, modifica_tabella_scaffale;

    @FXML
    private MFXTextField ricerca_prodotto_codice_cerca, elimina_prodotto_codice_cerca;

    @FXML
    private ImageView codiceBarreCrea, qrCodeCrea, crea_prodotti_immagine,codiceBarreModifica, qrCodeModifica;
    @FXML
    private ImageView codiceBarreCerca, codiceBarreElimina, elimina_prodotti_immagine, cerca_prodotti_immagine, modifica_prodotti_immagine;

    @FXML
    private ImageView qrCodeCerca, qrCodeElimina;

    private List<MFXTextField> crea_prodotto_nodes, modifica_prodotto_nodes;
    private List<Label> elimina_prodotto_label;
    private Stage stage;
    private HiveHubAlert alert;

    private String tmp_path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //RAGGRUPPO TUTTI I NODI DEL PANE CREA PRODOTTO
        this.crea_prodotto_nodes = new ArrayList<>();
        this.crea_prodotto_nodes.addAll(List.of(
                crea_prodotti_nome,
                crea_prodotti_codice,
                crea_prodotti_descrizione,
                crea_prodotti_area,
                crea_prodotti_scompartimento,
                crea_prodotti_livello,
                crea_prodotti_scaffale,
                crea_prodotti_qnt
        ));

        //RAGGRUPPO TUTTI I NODI DEL PANE MODIFICA PRODOTTO
        this.modifica_prodotto_nodes = new ArrayList<>();
        this.modifica_prodotto_nodes.addAll(List.of(
                modifica_prodotti_nome,
                modifica_prodotti_codice,
                modifica_prodotti_descrizione,
                modifica_prodotti_area,
                modifica_prodotti_livello,
                modifica_prodotti_scaffale,
                modifica_prodotti_scompartimento,
                modifica_prodotti_qnt
        ));

        //RAGGRUPPO TUTTE LE LABEL DEL PANE ELIMINA PRODOTTO
        this.elimina_prodotto_label = new ArrayList<>();
        this.elimina_prodotto_label.addAll(List.of(
                elimina_prodotto_codice,
                elimina_prodotto_descrizione,
                elimina_prodotto_nome
        ));

        //CREO IL LISTENER PER LA GENERAZIONE DEL QR CODE E DEL BAR CODE
        crea_prodotti_codice.textProperty().addListener((a,b,c) -> {
            Pattern pattern = Pattern.compile("^[0-9]*$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(c);
            if(!matcher.find()){
               c = "";
            }
            codiceBarreCrea.setImage(GeneratoreCodici.generaBarCode(c, (int) codiceBarreCrea.getFitWidth(), (int) codiceBarreCrea.getFitHeight()));
            qrCodeCrea.setImage(GeneratoreCodici.generaQrCode(c, (int) qrCodeCrea.getFitWidth(), (int) qrCodeCrea.getFitHeight()));
        });

        //IMPOSTO LE COLONNE DELLA TABELLA DEL PANE CREA PRODOTTO
        crea_tabella_scaffale.setCellValueFactory(new PropertyValueFactory<>("scaffale"));
        crea_tabella_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        crea_tabella_livello.setCellValueFactory(new PropertyValueFactory<>("livello"));
        crea_tabella_scompartimento.setCellValueFactory(new PropertyValueFactory<>("scompartimento"));
        crea_tabella_quantita.setCellValueFactory(new PropertyValueFactory<>("qnt"));

        //IMPOSTO LE COLONNE DELLA TABELLA DEL PANE CERCA PRODOTTO
        ricerca_tabella_scaffale.setCellValueFactory(new PropertyValueFactory<>("scaffale"));
        ricerca_tabella_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        ricerca_tabella_livello.setCellValueFactory(new PropertyValueFactory<>("livello"));
        ricerca_tabella_scompartimento.setCellValueFactory(new PropertyValueFactory<>("scompartimento"));
        ricerca_tabella_quantita.setCellValueFactory(new PropertyValueFactory<>("qnt"));

        //IMPOSTO LE COLONNE DELLA TABELLA DEL PANE ELIMINA PRODOTTO
        elimina_tabella_scaffale.setCellValueFactory(new PropertyValueFactory<>("scaffale"));
        elimina_tabella_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        elimina_tabella_livello.setCellValueFactory(new PropertyValueFactory<>("livello"));
        elimina_tabella_scompartimento.setCellValueFactory(new PropertyValueFactory<>("scompartimento"));
        elimina_tabella_quantita.setCellValueFactory(new PropertyValueFactory<>("qnt"));

        //IMPOSTO LE COLONNE DELLA TABELLA DEL PANE MODIFICA PRODOTTO
        modifica_tabella_scaffale.setCellValueFactory(new PropertyValueFactory<>("scaffale"));
        modifica_tabella_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        modifica_tabella_livello.setCellValueFactory(new PropertyValueFactory<>("livello"));
        modifica_tabella_scompartimento.setCellValueFactory(new PropertyValueFactory<>("scompartimento"));
        modifica_tabella_quantita.setCellValueFactory(new PropertyValueFactory<>("qnt"));

        modifica_prodotto_posizioni.setOnMousePressed(event -> {
            Posizione p = modifica_prodotto_posizioni.getSelectionModel().getSelectedItem();
            modifica_prodotti_area.setText(Integer.toString(p.getArea()));
            modifica_prodotti_livello.setText(Integer.toString(p.getLivello()));
            modifica_prodotti_scompartimento.setText(Integer.toString(p.getScompartimento()));
            modifica_prodotti_scaffale.setText(Integer.toString(p.getScaffale()));
            modifica_prodotti_qnt.setText(Integer.toString(p.getQnt()));

            modifica_prodotto_posizioni.refresh();
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        alert = new HiveHubAlert(this.stage);
    }

    /*
    CAMBIO PANE
     */

    public void showCreaProdotto(){
        mainPane.toFront();
        creaProdottoPane.toFront();
        creaProdottoReset(null);
    }

    public  void showEliminaProdotto(){
        mainPane.toFront();
        eliminaProdottoPane.toFront();
        eliminaProdottoReset(null);
    }

    public void showModificaProdotto(){
        mainPane.toFront();
        modificaProdottoPane.toFront();
        modificaProdottoReset(null);
    }

    public void showRicercaProdotto(){
        mainPane.toFront();
        ricercaProdottoPane.toFront();
        cercaProdottoReset();
    }


    /*
    CREA PRODOTTO
     */
    public void creaProdottoReset(ActionEvent actionEvent) {
        crea_prodotto_nodes.forEach(node -> node.setText(""));
        crea_prodotto_posizioni.getItems().clear();
        crea_prodotti_immagine.setImage(null);
    }

    public void creaProdottoCrea(ActionEvent actionEvent) {
        try{
            String nome = crea_prodotti_nome.getText();
            String codice = crea_prodotti_codice.getText();
            String descrizione = crea_prodotti_descrizione.getText();

            Prodotto p = new Prodotto(
                    nome,
                    Integer.parseInt(codice),
                    descrizione
            );

            crea_prodotto_posizioni.getItems().forEach(posizione -> {
                p.addPosizione(
                        new Posizione(
                                p,
                                new Coordinata(posizione.getScaffale(),
                                posizione.getArea(),
                                posizione.getLivello(),
                                posizione.getScompartimento()),
                                posizione.getQnt()
                        )
                );
            });

            if(tmp_path != null){
                byte[] foto = ImageFacade.leggiImmagine(tmp_path);
                p.setImmagine(foto);
            }

            new ProdottoDAO().insertProdotto(p);
            alert.informazione("Prodotto creato con successo");
            creaProdottoReset(null);
        }catch (ConstraintViolationException e){
            alert.errore("Attenzione! Codice prodotto o posizione già in uso!");
        }catch (Exception e){
            e.printStackTrace();
            alert.errore(e.getMessage());
        }
    }

    public void scegliImmagine(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
            this.tmp_path = selectedFile.getPath();
            crea_prodotti_immagine.setImage(SwingFXUtils.toFXImage(ImageIO.read(selectedFile),null));
        }catch (Exception e){
            this.tmp_path = null;
            e.printStackTrace();
        }
    }

    public void creaProdottiAggiungiPosizione(MouseEvent mouseEvent) {
        try {
            int scaffale = Integer.parseInt(this.crea_prodotti_scaffale.getText());
            int area = Integer.parseInt(this.crea_prodotti_area.getText());
            int livello = Integer.parseInt(this.crea_prodotti_livello.getText());
            int scompartimento = Integer.parseInt(this.crea_prodotti_scompartimento.getText());
            int qnt = Integer.parseInt(this.crea_prodotti_qnt.getText());

            crea_prodotto_posizioni.getItems().add(new Posizione(null,new Coordinata(scaffale,area,livello,scompartimento),qnt));

            this.crea_prodotti_scaffale.setText("");
            this.crea_prodotti_area.setText("");
            this.crea_prodotti_livello.setText("");
            this.crea_prodotti_scompartimento.setText("");
            this.crea_prodotti_qnt.setText("");
        }catch (NumberFormatException e){
            alert.errore("Tutti i campi devono essere dei numeri interi");
        }
    }

    public void creaProdottiEliminaPosizione(MouseEvent mouseEvent) {
        this.crea_prodotto_posizioni.getItems().remove(
                this.crea_prodotto_posizioni.getSelectionModel().getSelectedItem()
        );
    }

    /*
    MODIFICA PRODOTTO
     */

    public void modificaProdottoReset(ActionEvent actionEvent) {
        modifica_prodotto_nodes.forEach(node -> node.setText(""));
        modifica_prodotti_codice.setDisable(false);

        modifica_prodotti_immagine.setImage(null);
        qrCodeModifica.setImage(null);
        codiceBarreModifica.setImage(null);

        modifica_prodotto_posizioni.getItems().clear();

    }

    public void modificaProdottoModifica(ActionEvent actionEvent) {
        try{
            String nome = modifica_prodotti_nome.getText();
            String codice = modifica_prodotti_codice.getText();
            String descrizione = modifica_prodotti_descrizione.getText();

            Prodotto p = new Prodotto(
                    nome,
                    Integer.parseInt(codice),
                    descrizione
            );

            modifica_prodotto_posizioni.getItems().forEach(posizione -> {
                p.addPosizione(
                        new Posizione(
                                p,
                                new Coordinata(posizione.getScaffale(),
                                    posizione.getArea(),
                                    posizione.getLivello(),
                                    posizione.getScompartimento()),
                                posizione.getQnt()
                        )
                );
            });

            new ProdottoDAO().updateProdotto(p);
            alert.informazione("Prodotto modificato con successo");
            modificaProdottoReset(null);
        }catch (Exception e){
            e.printStackTrace();
            alert.errore(e.getMessage());
        }
    }

    public void modificaProdottiCerca(MouseEvent mouseEvent) {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Optional<Prodotto> p;
        try{
            p = prodottoDAO.selectByCodice(new Prodotto(null,Integer.parseInt(modifica_prodotti_codice.getText()),null));
            if(p.isPresent()){
                Prodotto prodotto = p.get();
                this.modifica_prodotti_codice.setDisable(true);
                this.modifica_prodotti_nome.setText(prodotto.getNome());
                this.modifica_prodotti_descrizione.setText(prodotto.getDescrizione());
                this.codiceBarreModifica.setImage(
                        GeneratoreCodici.generaBarCode(
                                String.valueOf(prodotto.getCodice()),
                                (int)this.codiceBarreModifica.getFitWidth(),
                                (int)this.codiceBarreModifica.getFitHeight()
                        )
                );
                this.qrCodeModifica.setImage(
                        GeneratoreCodici.generaQrCode(
                                String.valueOf(prodotto.getCodice()),
                                (int)this.qrCodeModifica.getFitWidth(),
                                (int)this.qrCodeModifica.getFitHeight()
                        )
                );

                modifica_prodotto_posizioni.getItems().clear();
                prodotto.getPosizione().forEach(posizione -> {
                    modifica_prodotto_posizioni.getItems().add(
                            new Posizione(
                                    prodotto,
                                    new Coordinata(posizione.getScaffale(),
                                            posizione.getArea(),
                                            posizione.getLivello(),
                                            posizione.getScompartimento()),
                                    posizione.getQnt()
                            )
                    );
                });

                if(prodotto.getImmagine() != null){
                    this.modifica_prodotti_immagine.setImage(
                            ImageFacade.byteToImage(prodotto.getImmagine())
                    );
                }else {
                    this.modifica_prodotti_immagine.setImage(null);
                }
            }else{
                alert.informazione("Nessun prodotto trovato con il codice fornito");
            }
        }catch (NumberFormatException e){
            p = Optional.empty();
            alert.errore("Il codice è formato da sole cifre");
        }
    }

    public void modificaProdottiEliminaPosizione(MouseEvent mouseEvent) {
        this.modifica_prodotto_posizioni.getItems().remove(
                this.modifica_prodotto_posizioni.getSelectionModel().getSelectedItem()
        );
        modifica_prodotto_posizioni.refresh();
    }

    public void modificaProdottiAggiungiPosizione(MouseEvent mouseEvent) {
        try{
            int scaffale = Integer.parseInt(this.modifica_prodotti_scaffale.getText());
            int area = Integer.parseInt(this.modifica_prodotti_area.getText());
            int livello = Integer.parseInt(this.modifica_prodotti_livello.getText());
            int scompartimento = Integer.parseInt(this.modifica_prodotti_scompartimento.getText());
            int qnt = Integer.parseInt(this.modifica_prodotti_qnt.getText());

            Posizione posizione = new Posizione(
                    null,
                    new Coordinata(
                            scaffale,
                            area,
                            livello,
                            scompartimento
                    ),
                    qnt
            );
            this.modifica_prodotto_posizioni.getItems().stream().toList().forEach(posizione1 -> {
                if(posizione.equals(posizione1)){
                    this.modifica_prodotto_posizioni.getItems().remove(posizione1);
                }
            });
            this.modifica_prodotto_posizioni.getItems().add(
                    posizione
            );

            this.modifica_prodotti_scaffale.setText("");
            this.modifica_prodotti_area.setText("");
            this.modifica_prodotti_livello.setText("");
            this.modifica_prodotti_scompartimento.setText("");
            this.modifica_prodotti_qnt.setText("");
        }catch(NumberFormatException e){
            alert.errore("Tutti i campi devono essere dei numeri interi");
        }
        modifica_prodotto_posizioni.refresh();
    }

    /*
    CERCA PRODOTTO
     */

    public void cercaProdottoReset(){
        ricerca_prodotto_codice_cerca.setText("");

        ricerca_prodotto_nome.setText("");
        ricerca_prodotto_descrizione.setText("");
        ricerca_prodotto_codice.setText("");

        ricerca_prodotto_posizioni.getItems().clear();

        cerca_prodotti_immagine.setImage(null);
        qrCodeCerca.setImage(null);
        codiceBarreCerca.setImage(null);
    }

    @FXML
    void ricercaProdottoCerca(MouseEvent event) {
        ((MaterialIconView)event.getSource()).setVisible(false);
        progressSpinner_ricerca.setVisible(true);

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Integer codice = null;
        try {
            codice = Integer.parseInt(ricerca_prodotto_codice_cerca.getText());
            Optional<Prodotto> p = prodottoDAO.selectByCodice(new Prodotto(null, codice,null ));

            if(p.isPresent()){
                this.ricerca_prodotto_nome.setText(p.get().getNome());
                this.ricerca_prodotto_codice.setText(Integer.toString(p.get().getCodice()));
                this.ricerca_prodotto_descrizione.setText(p.get().getDescrizione());

                this.codiceBarreCerca.setImage(
                        GeneratoreCodici.generaBarCode(
                                Integer.toString(p.get().getCodice()),
                                (int) codiceBarreCerca.getFitWidth(),
                                (int) codiceBarreCerca.getFitHeight()
                        )
                );
                this.qrCodeCerca.setImage(
                        GeneratoreCodici.generaQrCode(
                                Integer.toString(p.get().getCodice()),
                                (int) qrCodeCerca.getFitWidth(),
                                (int) qrCodeCerca.getFitHeight()
                        )
                );

                if(p.get().getImmagine() != null){
                    Image image = ImageFacade.byteToImage(p.get().getImmagine());
                    this.cerca_prodotti_immagine.setImage(image);
                }else {
                    this.cerca_prodotti_immagine.setImage(null);
                }

                ricerca_prodotto_posizioni.getItems().clear();
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
        ((MaterialIconView)event.getSource()).setVisible(true);
        progressSpinner_ricerca.setVisible(false);
    }

    /*
    ELIMINA PRODOTTO
     */
    public void eliminaProdottoReset(ActionEvent actionEvent) {
        this.elimina_prodotto_label.forEach(label -> {
            label.setText("");
        });
        this.elimina_prodotto_codice_cerca.setText("");
        this.elimina_prodotto_posizioni.getItems().clear();
        this.codiceBarreElimina.setImage(null);
        this.qrCodeElimina.setImage(null);
        this.elimina_prodotti_immagine.setImage(null);
    }

    public void eliminaProdottoConferma(ActionEvent actionEvent) {
        Prodotto p = new Prodotto(
                null,
                Integer.parseInt(elimina_prodotto_codice.getText()),
                null
        );

        elimina_prodotto_posizioni.getItems().forEach(p::addPosizione);

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        prodottoDAO.dropProdotto(p);

        alert.informazione("Prodotto eliminato con successo");
        eliminaProdottoReset(null);
    }

    public void eliminaProdottoCerca(MouseEvent mouseEvent) {
        Integer codice = null;
        try{
            codice = Integer.parseInt(elimina_prodotto_codice_cerca.getText());
            Optional<Prodotto> p = new ProdottoDAO().selectByCodice(new Prodotto(null,codice,null));
            if(p.isPresent()){
                Prodotto prodotto = p.get();

                elimina_prodotto_nome.setText(prodotto.getNome());
                elimina_prodotto_codice.setText(Integer.toString(prodotto.getCodice()));
                elimina_prodotto_descrizione.setText(prodotto.getDescrizione());

                codiceBarreElimina.setImage(GeneratoreCodici.generaBarCode(Integer.toString(prodotto.getCodice()), (int) codiceBarreElimina.getFitWidth(), (int) codiceBarreElimina.getFitHeight()));
                qrCodeElimina.setImage(GeneratoreCodici.generaQrCode(Integer.toString(prodotto.getCodice()), (int) qrCodeElimina.getFitWidth(), (int) qrCodeElimina.getFitHeight()));

                elimina_prodotto_posizioni.getItems().clear();
                prodotto.getPosizione().forEach(posizione -> {
                    elimina_prodotto_posizioni.getItems().add(
                            new Posizione(
                                    prodotto,
                                    new Coordinata(posizione.getScaffale(),
                                        posizione.getArea(),
                                        posizione.getLivello(),
                                        posizione.getScompartimento()),
                                    posizione.getQnt())
                    );
                });

                if(prodotto.getImmagine() != null){
                    Image image = ImageFacade.byteToImage(prodotto.getImmagine());
                    elimina_prodotti_immagine.setImage(image);
                }else{
                    elimina_prodotti_immagine.setImage(null);
                }
            }else {
                alert.informazione("Nessun prodotto trovato");
            }
        }catch (NumberFormatException ex){
            alert.errore("Il codice è formato da sole cifre");
        }
    }
}