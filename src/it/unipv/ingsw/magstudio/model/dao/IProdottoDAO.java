package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Posizione;
import it.unipv.ingsw.magstudio.model.bean.Prodotto;

import java.sql.SQLException;
import java.util.Optional;

public interface IProdottoDAO {
    public Optional<Prodotto> selectByCodice(Prodotto p);
    public boolean insertProdotto(Prodotto p);
    public boolean updateProdotto(Prodotto p);
    public boolean dropProdotto(Prodotto p);
}
