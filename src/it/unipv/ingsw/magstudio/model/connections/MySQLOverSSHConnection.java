package it.unipv.ingsw.magstudio.model.connections;

import java.sql.ResultSet;

public class MySQLOverSSHConnection implements IConnectionStrategy {
    private static MySQLOverSSHConnection istance;
    private final MySQLConnection sqlConnection;
    private final SSHConnection sshConnection;

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
    public void connect() {
        try {
            sshConnection.connect();
            sqlConnection.connect();
        }catch (Exception e){
            sshConnection.disconnect();
        }
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

    @Override
    public ResultSet executeQuery(String query) {
        return sqlConnection.executeQuery(query);
    }

    @Override
    public int executeUpdate(String query) {
        return sqlConnection.executeUpdate(query);
    }
}
