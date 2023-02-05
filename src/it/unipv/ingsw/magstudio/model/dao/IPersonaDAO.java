package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Persona;

import java.sql.SQLException;
import java.util.Optional;

public interface IPersonaDAO {
    public Optional<Persona> selectByNomeUtente(Persona p) throws SQLException;
    public boolean insertPersona(Persona p);
    public boolean updatePersona(Persona p);
    public boolean dropPersona(Persona p);
}
