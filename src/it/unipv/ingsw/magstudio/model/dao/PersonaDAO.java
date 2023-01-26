package it.unipv.ingsw.magstudio.model.dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import it.unipv.ingsw.magstudio.model.bean.*;
import it.unipv.ingsw.magstudio.model.exceptions.CfFormatException;
import it.unipv.ingsw.magstudio.model.exceptions.EmailFormatException;
import it.unipv.ingsw.magstudio.model.exceptions.TelefonoFormatException;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;

import java.sql.*;
import java.util.Date;

public class PersonaDAO implements IPersonaDAO{

    private ConnectionFacade connectionFacade;
    public PersonaDAO(ConnectionFacade connectionFacade) {
        this.connectionFacade=connectionFacade;

    }

    @Override
    public Persona selectByNomeUtente(String nomeUtente) {
        Persona out = null;

        if(connectionFacade.isOpen())
            connectionFacade.close();

        Connection connection=connectionFacade.connect();
        PreparedStatement ps= null;

        try {
            ps = connection.prepareStatement("SELECT * FROM PERSONA WHERE NOME_UTENTE=?");
            ps.setString(1,nomeUtente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date data = new Date();
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
                out = new Persona(rs.getString("NOME"), rs.getString("COGNOME"), rs.getString("CF"),
                        data, indirizzo, contatto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (EmailFormatException e) {
            throw new RuntimeException(e);
        } catch (TelefonoFormatException e) {
            throw new RuntimeException(e);
        } catch (CfFormatException e) {
            throw new RuntimeException(e);
        }finally {
            connectionFacade.close();
        }
        return out;
    }

    @Override
    public boolean insertPersona(Persona p) {
        return false;
    }

    public static void main(String[] args) {
        ConnectionFacade connectionFacade = new ConnectionFacade();
        connectionFacade.setStrategy(ConnectionFacade.ConnectionStrategy.MYSQL_OVER_SSH);

        PersonaDAO personaDAO = new PersonaDAO(connectionFacade);
        System.out.println(personaDAO.selectByNomeUtente("admin"));
    }
}
