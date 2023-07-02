package it.unipv.ingsw.magstudio.model.bean;


import it.unipv.ingsw.magstudio.model.dao.ProdottoDAO;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PosizioneTest {
    public static void main(String[] args) {
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        Optional<Prodotto> p = prodottoDAO.selectByCodice(new Prodotto(null,34,null));

        p.get().addPosizione(new Posizione(p.get(),22,22,22,22,22));

        prodottoDAO.updateProdotto(p.get());
    }


}
