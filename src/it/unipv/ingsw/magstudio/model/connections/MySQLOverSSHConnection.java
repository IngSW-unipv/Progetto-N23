package it.unipv.ingsw.magstudio.model.connections;

import java.sql.Connection;
import java.sql.ResultSet;

public class MySQLOverSSHConnection implements IConnectionStrategy {
    private static MySQLOverSSHConnection istance;
    private final MySQLConnection sqlConnection;
    private final SSHConnection sshConnection;
    private Connection connection;

    private MySQLOverSSHConnection(){
        this.sshConnection = SSHConnection.getIstance();
        this.sqlConnection = MySQLConnection.getIstance();
    }

    public static MySQLOverSSHConnection getIstance() {
        if(istance == null)
            istance = new MySQLOverSSHConnection();
        return istance;
    }

    @Override
    public Connection connect() {
        try {
            sshConnection.connect();
            this.connection = sqlConnection.connect();
        }catch (Exception e){
            sshConnection.disconnect();
        }
        return this.connection;
    }

    @Override
    public void disconnect() {
        sqlConnection.disconnect();
        sshConnection.disconnect();
    }

    @Override
    public boolean isOpen() {
        return sqlConnection.isOpen() || sshConnection.isOpen();
    }
}
