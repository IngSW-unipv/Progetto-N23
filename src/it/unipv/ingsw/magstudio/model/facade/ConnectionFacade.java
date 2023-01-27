package it.unipv.ingsw.magstudio.model.facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.ingsw.magstudio.model.connections.IConnectionStrategy;
import it.unipv.ingsw.magstudio.model.connections.MySQLConnection;
import it.unipv.ingsw.magstudio.model.connections.MySQLOverSSHConnection;

public class ConnectionFacade {
    /**
     * Strategie di Connessione supportate
     */
    public enum ConnectionStrategy {
        MYSQL, MYSQL_OVER_SSH;
    }

    private IConnectionStrategy strategy;
    private Connection connection;

    private static ConnectionFacade istance;
    private ConnectionFacade(){}

    /**
     * Restituisce l'istanza dell'oggetto ConnectionFacade.
     * Assicurarsi di aver impostato la strategia desiderata prima di connettersi.
     * @return
     */
    public static ConnectionFacade getIstance() {
        if (istance == null)
            istance = new ConnectionFacade();
        return istance;
    }

    /**
     * Metodo per impostare la strategia di Connessione
     * @param strategy La strategia scelta
     * @see ConnectionStrategy
     */
    public void setStrategy(ConnectionStrategy strategy){
        switch (strategy){
            case MYSQL -> setMySQLStrategy();
            case MYSQL_OVER_SSH -> setMySQLOverSSHStrategy();
        }
    }

    /**
     * Imposta la strategia di Connessione diretta a MySQL
     */
    private void setMySQLStrategy(){
        strategy = MySQLConnection.getIstance();
    }

    /**
     * Imposta la strategia di Connessione a MySQL attraverso un tunnel SSH
     */
    private void setMySQLOverSSHStrategy(){
        strategy = MySQLOverSSHConnection.getIstance();
    }

    /**
     * Attiva la connessione al DB secondo la strategia selezionata. Assicurarsi di aver impostato
     * la strategia desiderata prima di connettersi.
     * @return L'oggetto Connection
     * @throws SQLException
     * @see Connection
     */
    public Connection connect() throws SQLException {
        this.connection = strategy.connect();
        return this.connection;
    }

    /**
     * Chiude la connessione al DB della strategia scelta
     */
    public void close(){
        if(strategy.isOpen())
            strategy.disconnect();
    }

    /**
     * Controlla lo stato della connessione
     * @return Restituisce 'true' se la connessione è ancora aperta, altrimenti 'false'
     */
    public boolean isOpen(){
        return strategy.isOpen();
    }

    /**
     * Permette di controllare se l'accoppiata nome utente e password sono corrette
     * @param nomeUtente Il nome utente
     * @param password La password
     * @return L'esito dell'operazione, 'true' -> credenziali corrette; 'false' -> credenziali errate
     * @throws SQLException Eccezione lanciata in caso di errore con il server DB
     */
    public boolean controllaCredenziali(String nomeUtente, String password) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS N FROM T_PERSONA INNER JOIN PERSONA ON T_PERSONA.ID = PERSONA.ID WHERE NOME_UTENTE=? AND PASSWORD=?");
        ps.setString(1,nomeUtente);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if(rs.getInt("N") == 1){
            return true;
        }
        return false;
    }
}
