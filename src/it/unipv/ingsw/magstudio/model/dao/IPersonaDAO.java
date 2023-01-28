package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Persona;

import java.sql.SQLException;

public interface IPersonaDAO {
    public Persona selectByNomeUtente(Persona p) throws SQLException;
    public boolean insertPersona(Persona p);
}
