package it.unipv.ingsw.magstudio.model.dao;


import it.unipv.ingsw.magstudio.model.bean.*;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;

import java.sql.*;
import java.util.Date;
import java.util.Optional;

public class PersonaDAO implements IPersonaDAO{

    private ConnectionFacade connectionFacade;

    /**
     * Crea un nuovo oggetto PersonaDAO
     */
    public PersonaDAO() {
        this.connectionFacade=ConnectionFacade.getIstance();
    }

    /**
     * Restituisce l'oggetto Persona identificato dal nome utente passato
     *
     * @param p L'oggetto Persona con il nome utente
     * @return L'oggetto Persona
     * @throws SQLException
     * @see Persona
     */
    @Override
    public Optional<Persona> selectByNomeUtente(Persona p) throws SQLException {
        Optional<Persona> out = Optional.empty();

        Connection connection=connectionFacade.connect();
        PreparedStatement ps= null;

        try {
            ps = connection.prepareStatement("SELECT * FROM PERSONA WHERE NOME_UTENTE=?");
            ps.setString(1,p.getNomeUtente());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date data = new Date(rs.getDate("DATA_NASCITA").getTime());
                Indirizzo indirizzo = new Indirizzo(TipoStrada.valueOf(rs.getString("INDIRIZZO_TIPO_STRADA")), rs.getString("INDIRIZZO_NOME"),
                        rs.getString("INDIRIZZO_CIVICO"), rs.getInt("INDIRIZZO_CAP"), rs.getString("INDIRIZZO_CITTA"),
                        rs.getString("INDIRIZZO_PROVINCIA"), Regione.valueOf(rs.getString("INDIRIZZO_REGIONE")));
                Contatto contatto = null;
                if(rs.getString("CONTATTO_EMAIL") != null && rs.getLong("CONTATTO_TELEFONO") != 0) {
                    contatto = new Contatto(rs.getString("CONTATTO_EMAIL"),rs.getLong("CONTATTO_TELEFONO"));
                } else if (rs.getString("CONTATTO_EMAIL") != null) {
                    contatto = new Contatto(rs.getString("CONTATTO_EMAIL"));
                }else {
                    contatto = new Contatto(rs.getLong("CONTATTO_TELEFONO"));
                }

                out = Optional.of(new Persona(rs.getString("NOME_UTENTE"),rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("CF"),
                        data, indirizzo, contatto));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            connectionFacade.close();
        }
        return out;
    }

    @Override
    public boolean insertPersona(Persona p) {
        //TODO: da implementare
        return false;
    }

    @Override
    public boolean updatePersona(Persona p) {
        //TODO: da implementare
        return false;
    }

    @Override
    public boolean dropPersona(Persona p) {
        //TODO: da implementare
        return false;
    }
}
