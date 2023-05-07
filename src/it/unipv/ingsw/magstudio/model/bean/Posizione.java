package it.unipv.ingsw.magstudio.model.bean;

public class Posizione {
    private int scaffale, area, livello, scompartimento;

    public Posizione(int scaffale, int area, int livello, int scompartimento) {
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
    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append(getScaffale())
                .append(getArea())
                .append(getLivello())
                .append(getScompartimento());

        return out.toString();
    }
}
