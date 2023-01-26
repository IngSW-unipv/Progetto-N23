package it.unipv.ingsw.magstudio.model.connections;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IConnectionStrategy {
    Connection connect();
    void disconnect();
    boolean isOpen();
}
