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

    public Prodotto(){}


    public Prodotto(String nome, int codice, String descrizione) {
        this.nome = nome;
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

    public Set<Posizione> getPosizione() {
        return posizione;
    }

    public void addPosizione(Posizione p){
        this.posizione.add(p);
    }

    public byte[] getImmagine() {
        return immagine;
    }

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
