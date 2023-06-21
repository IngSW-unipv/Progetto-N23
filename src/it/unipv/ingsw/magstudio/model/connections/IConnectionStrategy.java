package it.unipv.ingsw.magstudio.model.connections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interfaccia necessaria per poter implementare una Nuova Strategia di connessione al DB
 */
public interface IConnectionStrategy {
    Connection connect() throws SQLException;
    void disconnect();
    boolean isOpen();
}
