package it.unipv.ingsw.magstudio.model.bean;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "POSIZIONE")
public class Posizione {
    @Id
    @Column(name = "SCAFFALE")
    private int scaffale;


    @Column(name = "AREA")
    private int area;


    @Column(name = "LIVELLO")
    private int livello;
    @Column(name = "SCOMPARTIMENTO")
    private int scompartimento;

    @ManyToOne
    @JoinColumn(name = "CODICE")
    private Prodotto prodotto;

    public Posizione(){}

    public Posizione(Prodotto p, int scaffale, int area, int livello, int scompartimento) {
        this.prodotto = p;
        this.scaffale = scaffale;
        this.area = area;
        this.livello = livello;
        this.scompartimento = scompartimento;
    }

    public int getScaffale() {
        return scaffale;
    }

    public void setScaffale(int scaffale) {
        this.scaffale = scaffale;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public int getScompartimento() {
        return scompartimento;
    }

    public void setScompartimento(int scompartimento) {
        this.scompartimento = scompartimento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.scaffale,this.area,this.livello,this.scompartimento);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Posizione posizione = (Posizione) obj;

        return (this.scaffale == posizione.scaffale)&&
                (this.area == posizione.area) &&
                (this.livello == posizione.livello)&&
                (this.scompartimento == posizione.scompartimento);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append("Scaffale: ")
                .append(getScaffale())
                .append(" - Area: ")
                .append(getArea())
                .append(" - Livello: ")
                .append(getLivello())
                .append(" - Scompartimento: ")
                .append(getScompartimento());

        return out.toString();
    }
}
