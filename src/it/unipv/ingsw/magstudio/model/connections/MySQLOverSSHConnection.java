package it.unipv.ingsw.magstudio.model.connections;

import java.sql.ResultSet;

public class MySQLOverSSHConnection implements IConnectionStrategy {
    private MySQLConnection sqlConnection;
    private SSHConnection sshConnection;

    public MySQLOverSSHConnection(){
        this.sshConnection = new SSHConnection.Builder()
                .setHost("195.231.85.22")
                .setUser("hivehub")
                .setPassword("hivehub")
                .setRemoteHost("127.0.0.1")
                .setListenPort(5656)
                .setRecivePort(3306)
        .build();

        this.sqlConnection = new MySQLConnection.Builder()
                .setHost("127.0.0.1")
                .setPort(5656)
                .setSchema("HIVEHUB")
                .setUsername("sql-hivehub")
                .setPassword("Hivehub2023!")
        .build();
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
