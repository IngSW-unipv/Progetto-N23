package it.unipv.ingsw.magstudio.model.bean;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODOTTO")
public class Prodotto {
    @Id
    @Column(name = "CODICE")
    private int codice;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    @Column(name = "IMMAGINE", columnDefinition="LONGBLOB")
    private byte[] immagine;

    @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Posizione> posizione;

    /**
     * Costruttore vuoto (utilizzato da Hibernate)
     */
    public Prodotto(){}


    /**
     * Crea un prodotto
     * @param nome - Il nome del prodotto
     * @param codice - Il codice del prodotto
     * @param descrizione - Descrizione del prodotto
     */
    public Prodotto(String nome, int codice, String descrizione) {
        this.nome = nome;
        this.codice = codice;
        this.descrizione = descrizione;
        this.posizione = new HashSet<>();
    }

    /**
     * Restituisce il nome del prodotto
     * @return Il nome del prodotto
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il codice del prodotto
     * @return Il codice del prodotto
     */
    public int getCodice(){
        return codice;
    }

    /**
     * Imposta il codice del prodotto
     * @param codice - Il nuovo codice del prodotto
     */
    public void setCodice(int codice) {
        this.codice = codice;
    }

    /**
     * Restituisce la descrizione del prodotto
     * @return La descrizione del prodotto
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta la descrizione del prodotto
     * @param descrizione - La nuova descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Restituisce tutte le posizioni occupate dal prodotto
     * @return Le posizioni
     */
    public Set<Posizione> getPosizione() {
        return posizione;
    }

    /**
     * Aggiunge una posizione
     * @param p - La posizione da aggiungere
     */
    public void addPosizione(Posizione p){
        this.posizione.add(p);
    }


    /**
     * Restituisce l'immagine del prodotto in byte
     * @return L'immagine
     * @see it.unipv.ingsw.magstudio.model.util.ImageFacade
     */
    public byte[] getImmagine() {
        return immagine;
    }

    /**
     * Imposta una nuova immagine
     * @param immagine - Immagine in byte
     * @see it.unipv.ingsw.magstudio.model.util.ImageFacade
     */
    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    @Override
    public String toString() {
       StringBuilder out = new StringBuilder();

       out.append("Nome: ")
               .append(this.getNome())
               .append(" - Codice: ")
               .append(this.getCodice())
               .append(" - Descrizione: ")
               .append(this.getDescrizione());
                this.getPosizione().forEach(pos -> {
                    out.append(" - ")
                        .append(pos.toString());
                });
       return out.toString();
    }
}
