package it.unipv.ingsw.magstudio.model.connections;

import com.jcraft.jsch.JSchException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLOverSSHConnection implements IConnectionStrategy {
    private static MySQLOverSSHConnection istance;
    private final MySQLConnection sqlConnection;
    private final SSHConnection sshConnection;
    private Connection connection;

    private MySQLOverSSHConnection(){
        this.sshConnection = SSHConnection.getIstance();
        this.sqlConnection = MySQLConnection.getIstance();
    }

    /**
     * Fornisce l'istanza dell'oggetto MySQLOverSSHConnection
     * @return L'istanza dell'oggetto
     */
    public static MySQLOverSSHConnection getIstance() {
        if(istance == null)
            istance = new MySQLOverSSHConnection();
        return istance;
    }

    /**
     * Metodo per effettuare la connessione al server MySQL
     * @return L'oggetto Connection
     * @throws SQLException
     */
    @Override
    public Connection connect() throws SQLException {
        try {
            sshConnection.connect();
            this.connection = sqlConnection.connect();
        } catch (JSchException e) {
            sshConnection.disconnect();
            throw new RuntimeException(e);
        }
        return this.connection;
    }

    /**
     * Metodo per chiudere la connessione al Database MySQL
     */
    @Override
    public void disconnect() {
        sqlConnection.disconnect();
        sshConnection.disconnect();
    }

    /**
     * Metodo per controllare se la connessione al db è aperta
     * @return true se la connessione è aperta, altrimenti false
     */
    @Override
    public boolean isOpen() {
        return sqlConnection.isOpen() || sshConnection.isOpen();
    }
}
