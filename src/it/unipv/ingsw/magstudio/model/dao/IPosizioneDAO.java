package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Prodotto;

import java.sql.SQLException;

public interface IPosizioneDAO {
    public boolean insertPosizione(Prodotto p) throws SQLException;
}
