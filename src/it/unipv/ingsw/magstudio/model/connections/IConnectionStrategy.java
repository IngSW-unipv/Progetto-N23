package it.unipv.ingsw.magstudio.model.connections;

import java.sql.ResultSet;

public interface IConnectionStrategy {
    void connect();
    void disconnect();
    boolean isOpen();
    ResultSet executeQuery(String query);
    int executeUpdate(String query);
}
