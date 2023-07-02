package it.unipv.ingsw.magstudio.model.bean;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "POSIZIONE")
public class Posizione {
    @EmbeddedId
    private Coordinata posizione;

    @Column(name = "QNT")
    private int qnt;

    @ManyToOne
    @JoinColumn(name = "CODICE")
    private Prodotto prodotto;

    public Posizione(){}

    public Posizione(Prodotto p, int scaffale, int area, int livello, int scompartimento, int qnt) {
        this.prodotto = p;
        this.posizione = new Coordinata(scaffale,area,livello,scompartimento);
        this.qnt = qnt;
    }

    public Coordinata getPosizione() {
        return posizione;
    }

    public int getQnt() {
        return qnt;
    }

    public int getLivello(){
        return getPosizione().getLivello();
    }

    public int getScaffale(){
        return  getPosizione().getScaffale();
    }

    public int getArea(){
        return  getPosizione().getArea();
    }

    public int getScompartimento(){
        return  getPosizione().getScompartimento();
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    @Override
    public int hashCode() {
        Coordinata c = getPosizione();
        return Objects.hash(c.getScaffale(),c.getArea(),c.getLivello(),c.getScompartimento());
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

        return (getPosizione().getScaffale() == posizione.getPosizione().getScaffale())&&
                (getPosizione().getArea() == posizione.getPosizione().getArea()) &&
                (getPosizione().getLivello() == posizione.getPosizione().getLivello())&&
                (getPosizione().getScompartimento() == posizione.getPosizione().getScompartimento());
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append("Scaffale: ")
                .append(getPosizione().getScaffale())
                .append(" - Area: ")
                .append(getPosizione().getArea())
                .append(" - Livello: ")
                .append(getPosizione().getLivello())
                .append(" - Scompartimento: ")
                .append(getPosizione().getScompartimento())
                .append(" - Quantità: ")
                .append(getQnt());

        return out.toString();
    }
}
