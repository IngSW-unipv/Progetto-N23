package it.unipv.ingsw.magstudio.model.bean;

import java.util.ArrayList;
import java.util.List;

public class Prodotto {
    private String nome;
    private int qnt, codice;
    private String descrizione;
    private List<Posizione> posizione;

    public Prodotto(Posizione p, String nome, int qnt, int codice, String descrizione) {
        this.nome = nome;
        this.qnt = qnt;
        this.codice = codice;
        this.descrizione = descrizione;
        this.posizione = new ArrayList<>();
        this.posizione.add(p);
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

    public List<Posizione> getPosizione() {
        return posizione;
    }

    public void addPosizione(Posizione p){
        //PROBLEMA: possibile ripetizione di una stessa posizione
        this.posizione.add(p);
    }
}
