package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.Persona;

import java.util.Optional;

public interface IPersonaDAO {
    public Optional<Persona> selectByNomeUtente(Persona p);
    public void insertPersona(Persona p);
    public boolean updatePersona(Persona p);
    public boolean dropPersona(Persona p);
}
