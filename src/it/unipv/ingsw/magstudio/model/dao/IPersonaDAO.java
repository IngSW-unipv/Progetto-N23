package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Persona;

public interface IPersonaDAO {
    //TODO: migliorare
    public Persona selectByNomeUtente(String nomeUtente);
    public boolean insertPersona(Persona p);
}
