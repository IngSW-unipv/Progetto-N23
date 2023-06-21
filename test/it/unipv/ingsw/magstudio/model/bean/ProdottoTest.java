package it.unipv.ingsw.magstudio.model.bean;

import java.sql.SQLException;

public class ProdottoTest {
    public static void main(String[] args) throws SQLException {
        Prodotto p = new Prodotto(new Posizione(0,0,0,0),"RTX 4090 TI",200,1,"SCHEDA VAIDEO PAWA CON I LED RGB DA GAMING");
        p.addPosizione(new Posizione(0,0,0,1));
        p.addPosizione(new Posizione(0,0,0,1));
        System.out.println(p.toString());
    }
}
