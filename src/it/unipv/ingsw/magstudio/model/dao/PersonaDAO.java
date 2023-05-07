package it.unipv.ingsw.magstudio.model.dao;


import it.unipv.ingsw.magstudio.model.bean.*;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;

import java.sql.*;
import java.util.Date;
import java.util.Optional;

public class PersonaDAO implements IPersonaDAO{

    private final ConnectionFacade connectionFacade;

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

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PERSONA WHERE NOME_UTENTE=?")) {
            ps.setString(1, p.getNomeUtente());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date data = new Date(rs.getDate("DATA_NASCITA").getTime());
                Indirizzo indirizzo = new Indirizzo(TipoStrada.valueOf(rs.getString("INDIRIZZO_TIPO_STRADA")), rs.getString("INDIRIZZO_NOME"),
                        rs.getString("INDIRIZZO_CIVICO"), rs.getInt("INDIRIZZO_CAP"), rs.getString("INDIRIZZO_CITTA"),
                        rs.getString("INDIRIZZO_PROVINCIA"), Regione.valueOf(rs.getString("INDIRIZZO_REGIONE")));
                Contatto contatto = null;
                if (rs.getString("CONTATTO_EMAIL") != null && rs.getLong("CONTATTO_TELEFONO") != 0) {
                    contatto = new Contatto(rs.getString("CONTATTO_EMAIL"), rs.getLong("CONTATTO_TELEFONO"));
                } else if (rs.getString("CONTATTO_EMAIL") != null) {
                    contatto = new Contatto(rs.getString("CONTATTO_EMAIL"));
                } else {
                    contatto = new Contatto(rs.getLong("CONTATTO_TELEFONO"));
                }

                out = Optional.of(new Persona(rs.getString("NOME_UTENTE"), rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("CF"),
                        data, indirizzo, contatto));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            connectionFacade.close();
        }
        return out;
    }

    @Override
    public boolean insertPersona(Persona p) throws SQLException {
        PreparedStatement ps = null;
        int queryResult = 0;
        try{
            Connection connection = connectionFacade.connect();
            String query = "INSERT INTO PERSONA(" +
                    "NOME_UTENTE, " +
                    "NOME," +
                    "COGNOME," +
                    "CF," +
                    "DATA_NASCITA," +
                    "INDIRIZZO_NOME," +
                    "INDIRIZZO_CIVICO," +
                    "INDIRIZZO_CAP," +
                    "INDIRIZZO_CITTA," +
                    "INDIRIZZO_PROVINCIA," +
                    "INDIRIZZO_TIPO_STRADA," +
                    "INDIRIZZO_REGIONE," +
                    "CONTATTO_EMAIL," +
                    "CONTATTO_TELEFONO" +
                    ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(query);

            ps.setString(1,p.getNomeUtente().toUpperCase());
            ps.setString(2,p.getNome().toUpperCase());
            ps.setString(3,p.getCognome().toUpperCase());
            ps.setString(4,p.getCf().toUpperCase());
            ps.setString(5,p.getDataNascita());
            ps.setString(6,p.getIndirizzo().getNome().toUpperCase());
            ps.setString(7,p.getIndirizzo().getCivico().toUpperCase());
            ps.setInt(8,p.getIndirizzo().getCap());
            ps.setString(9,p.getIndirizzo().getCitta().toUpperCase());
            ps.setString(10,p.getIndirizzo().getProvincia().toUpperCase());
            ps.setString(11,p.getIndirizzo().getTipoStrada().name());
            ps.setString(12,p.getIndirizzo().getRegione().name());
            if(p.getContatto().getEmail().isEmpty()){
                ps.setString(13,null);
            }else{
                ps.setString(13,p.getContatto().getEmail().get().toUpperCase());
            }
            if(p.getContatto().getTelefono().isEmpty()){
                ps.setString(14,null);
            }else{
                ps.setLong(14,p.getContatto().getTelefono().get());
            }

            queryResult = ps.executeUpdate();
        }finally {
            ps.close();
            connectionFacade.close();
        }

        return queryResult > 0;
    }

    @Override
    public boolean updatePersona(Persona p) throws SQLException {
        PreparedStatement ps = null;
        int queryResult = 0;
        try{
            Connection connection = connectionFacade.connect();
            String query = "UPDATE PERSONA SET " +
                    "INDIRIZZO_NOME = ?, " +
                    "INDIRIZZO_CIVICO = ?, " +
                    "INDIRIZZO_CAP = ?," +
                    "INDIRIZZO_CITTA = ?," +
                    "INDIRIZZO_PROVINCIA = ?," +
                    "INDIRIZZO_TIPO_STRADA = ?," +
                    "INDIRIZZO_REGIONE = ?," +
                    "CONTATTO_EMAIL = ?," +
                    "CONTATTO_TELEFONO = ? " +
                    "WHERE NOME_UTENTE = ?";
            ps = connection.prepareStatement(query);

            ps.setString(1,p.getIndirizzo().getNome().toUpperCase());
            ps.setString(2,p.getIndirizzo().getCivico().toUpperCase());
            ps.setInt(3,p.getIndirizzo().getCap());
            ps.setString(4,p.getIndirizzo().getCitta().toUpperCase());
            ps.setString(5,p.getIndirizzo().getProvincia().toUpperCase());
            ps.setString(6,p.getIndirizzo().getTipoStrada().name());
            ps.setString(7,p.getIndirizzo().getRegione().name());
            if(p.getContatto().getEmail().isEmpty()){
                ps.setString(8,null);
            }else{
                ps.setString(8,p.getContatto().getEmail().get().toUpperCase());
            }
            if(p.getContatto().getTelefono().isEmpty()){
                ps.setString(9,null);
            }else{
                ps.setLong(9,p.getContatto().getTelefono().get());
            }

            ps.setString(10,p.getNomeUtente().toUpperCase());

            queryResult = ps.executeUpdate();
        }finally {
            ps.close();
            connectionFacade.close();
        }

        return queryResult > 0;
    }

    @Override
    public boolean dropPersona(Persona p) throws SQLException {
        PreparedStatement ps = null;
        int queryResult = 0;
        try{
            Connection connection = connectionFacade.connect();

            ps = connection.prepareStatement("SELECT ID FROM PERSONA WHERE NOME_UTENTE=?");
            ps.setString(1,p.getNomeUtente());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("ID");
            ps.close();

            ps = connection.prepareStatement("DELETE FROM T_PERSONA WHERE ID = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();

            ps = connection.prepareStatement("DELETE FROM PERSONA WHERE NOME_UTENTE = ?");
            ps.setString(1,p.getNomeUtente());
            queryResult = ps.executeUpdate();
        }finally {
            ps.close();
            connectionFacade.close();
        }

        return queryResult > 0;
    }
}
