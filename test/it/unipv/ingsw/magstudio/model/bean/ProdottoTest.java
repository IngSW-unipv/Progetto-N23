package it.unipv.ingsw.magstudio.model.bean;

import it.unipv.ingsw.magstudio.model.dao.ProdottoDAO;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Properties;

public class ProdottoTest {
    public static void main(String[] args) throws SQLException {
        ConnectionFacade.getIstance().setStrategy(ConnectionFacade.ConnectionStrategy.MYSQL_OVER_SSH);
        ConnectionFacade.getIstance().connect();

        Prodotto p = new Prodotto(new Posizione(0,0,0,0),"RTX 4090 TI",200,1,"SCHEDA VAIDEO PAWA CON I LED RGB DA GAMING");

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        prodottoDAO.insertProdotto(p);
        ConnectionFacade.getIstance().close();
    }
}
