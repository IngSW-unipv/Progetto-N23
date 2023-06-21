package it.unipv.ingsw.magstudio.model.bean;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PRODOTTO")
public class Prodotto {
    @Id
    @Column(name = "CODICE")
    private int codice;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "QNT")
    private int qnt;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL)
    private Set<Posizione> posizione;

    public Prodotto(){}

    public Prodotto(Posizione p, String nome, int qnt, int codice, String descrizione) {
        this.nome = nome;
        this.qnt = qnt;
        this.codice = codice;
        this.descrizione = descrizione;
        this.posizione = new HashSet<>();
        this.posizione.add(p);
    }

    public Prodotto(String nome, int qnt, int codice, String descrizione) {
        this.nome = nome;
        this.qnt = qnt;
        this.codice = codice;
        this.descrizione = descrizione;
        this.posizione = new HashSet<>();
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

    public Set<Posizione> getPosizione() {
        return posizione;
    }

    public void addPosizione(Posizione p){
        this.posizione.add(p);
    }

    @Override
    public String toString() {
       StringBuilder out = new StringBuilder();

       out.append("Nome: ")
               .append(this.getNome())
               .append(" - Quantità: ")
               .append(this.getQnt())
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
