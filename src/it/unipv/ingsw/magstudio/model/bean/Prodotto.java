package it.unipv.ingsw.magstudio.model.bean;

public class Prodotto {
    private String nome;
    private int qnt, codice;
    private String descrizione;
    private Posizione posizione;

    public Prodotto(int scaffale, int area, int livello, int scompartimento, String nome, int qnt, int codice, String descrizione) {
        this.nome = nome;
        this.qnt = qnt;
        this.codice = codice;
        this.descrizione = descrizione;
        this.posizione = new Posizione(scaffale,area,livello,scompartimento);
    }

    public String getNome() {
        return nome;
    }

    public int getCodice(){
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }
}
