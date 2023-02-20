package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;
import it.unipv.ingsw.magstudio.model.util.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestoreAccount {
    /**
     * Permette di controllare se l'accoppiata nome utente e password sono corrette
     * @param nomeUtente Il nome utente
     * @param password La password
     * @return L'esito dell'operazione, 'true' -> credenziali corrette; 'false' -> credenziali errate
     * @throws SQLException Eccezione lanciata in caso di errore con il server DB
     */
    public static boolean controllaCredenziali(String nomeUtente, String password) throws SQLException {
        password = Encryption.SHA256Encryptor(password);

        ConnectionFacade connectionFacade = ConnectionFacade.getIstance();
        Connection connection = connectionFacade.connect();

        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS N FROM T_PERSONA INNER JOIN PERSONA ON T_PERSONA.ID = PERSONA.ID WHERE NOME_UTENTE=? AND PASSWORD=?");
        ps.setString(1,nomeUtente);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int res = rs.getInt("N");
        connectionFacade.close();

        return res == 1;
    }

    public static boolean impostaPassword(String nomeUtente, String password) throws SQLException {
        password = Encryption.SHA256Encryptor(password);

        ConnectionFacade connectionFacade = ConnectionFacade.getIstance();
        Connection connection = connectionFacade.connect();

        PreparedStatement ps1 = connection.prepareStatement("SELECT ID FROM PERSONA WHERE NOME_UTENTE = ?");
        ps1.setString(1, nomeUtente);
        ResultSet rs = ps1.executeQuery();

        rs.next();
        int result = 0;
        try {
            String utente = rs.getString("ID");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO T_PERSONA VALUES(?, ?)");
            ps.setString(1,password);
            ps.setString(2,utente);
            result = ps.executeUpdate();
        }catch (Exception e){
            return false;
        }finally {
            connectionFacade.close();
        }

        return result == 1;
    }
}
