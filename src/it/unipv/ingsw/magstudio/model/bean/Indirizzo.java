package it.unipv.ingsw.magstudio.model.bean;

public class Indirizzo {
    private String nome;
    private String civico;
    private int cap;
    private String citta;
    private String provincia;
    private Regione regione;
    private TipoStrada tipoStrada;

    public Indirizzo(TipoStrada tipoStrada, String nome, String civico, int cap, String citta, String provincia, Regione regione) {
        this.nome = nome;
        this.civico=civico;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.regione = regione;
        this.tipoStrada = tipoStrada;
    }

    public String getNome() {
        return nome;
    }

    public String getCivico() {
        return civico;
    }
    public int getCap() {
        return cap;
    }

    public String getCitta() {
        return citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public Regione getRegione() {
        return regione;
    }

    public TipoStrada getTipoStrada() {
        return tipoStrada;
    }

    @Override
    public String toString() {
        return "Indirizzo:"+tipoStrada+" "+nome+" "+civico+" "+citta+" "+cap+" "+provincia+" "+regione;
    }
}
