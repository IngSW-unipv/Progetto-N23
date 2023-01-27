package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Persona;

import java.sql.SQLException;

public interface IPersonaDAO {
    //TODO: migliorare
    public Persona selectByNomeUtente(String nomeUtente) throws SQLException;
    public boolean insertPersona(Persona p);
}
