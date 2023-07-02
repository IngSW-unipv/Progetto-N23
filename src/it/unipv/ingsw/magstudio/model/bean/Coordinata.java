package it.unipv.ingsw.magstudio.model.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Coordinata {
    @Column(name = "SCAFFALE")
    private int scaffale;


    @Column(name = "AREA")
    private int area;


    @Column(name = "LIVELLO")
    private int livello;
    @Column(name = "SCOMPARTIMENTO")
    private int scompartimento;

    public Coordinata() {
    }

    public Coordinata(int scaffale, int area, int livello, int scompartimento) {
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
}
