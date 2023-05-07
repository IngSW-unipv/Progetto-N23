package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Posizione;
import it.unipv.ingsw.magstudio.model.bean.Prodotto;

import java.sql.SQLException;
import java.util.Optional;

public interface IProdottoDAO {
    public Optional<Prodotto> selectByCodice(Prodotto p) throws SQLException;
    public Optional<Prodotto> selectByPosizione(Posizione p) throws SQLException;
    public Optional<Prodotto> selectByNome(Prodotto p) throws SQLException;
    public boolean insertProdotto(Prodotto p) throws SQLException;
    public boolean updateProdotto(Prodotto p) throws SQLException;
    public boolean dropProdotto(Prodotto p) throws SQLException;
}
