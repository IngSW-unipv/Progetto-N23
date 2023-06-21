package it.unipv.ingsw.magstudio.model.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Indirizzo {
    @Column(name = "INDIRIZZO_NOME")
    private String nome;
    @Column(name = "INDIRIZZO_CIVICO")
    private String civico;
    @Column(name = "INDIRIZZO_CAP")
    private int cap;
    @Column(name = "INDIRIZZO_CITTA")
    private String citta;
    @Column(name = "INDIRIZZO_PROVINCIA")
    private String provincia;
    @Enumerated(EnumType.STRING)
    @Column(name = "INDIRIZZO_REGIONE")
    private Regione regione;
    @Enumerated(EnumType.STRING)
    @Column(name = "INDIRIZZO_TIPO_STRADA")
    private TipoStrada tipoStrada;

    /**
     * Crea un Indirizzo
     * @param tipoStrada Tipo di strada che si vuole creare
     * @param nome Il nome della strada
     * @param civico Il civico dell' indirizzo
     * @param cap Il CAP della Città
     * @param citta Il nome della Città
     * @param provincia Il nome della Provincia, scritta per esteso
     * @param regione La Regione in cui si trova l'indirizzo
     * @see TipoStrada
     * @see Regione
     */
    public Indirizzo(TipoStrada tipoStrada, String nome, String civico, int cap, String citta, String provincia, Regione regione) {
        this.nome = nome;
        this.civico=civico;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.regione = regione;
        this.tipoStrada = tipoStrada;
    }

    public Indirizzo(){}
    /**
     * Restituisce il nome della strada
     * @return Il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il civico dell'indirizzo
     * @return Il civico
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Restituisce il CAP della Città
     * @return Il CAP della Città
     */
    public int getCap() {
        return cap;
    }

    /**
     * Restituisce la Città dell'indirizzo
     * @return La Città
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Restituisce la Provincia dell'indirizzo
     * @return La Provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Resituisce la Regione dell'indirizzo
     * @return La Regione
     */
    public Regione getRegione() {
        return regione;
    }

    /**
     * Restituisce il tipo di strada
     * @return Il Tipo di Strada
     */
    public TipoStrada getTipoStrada() {
        return tipoStrada;
    }

    @Override
    public String toString() {
        return tipoStrada+" "+nome+" "+civico+", "+citta+", "+cap+" ("+provincia+"), "+regione;
    }
}
