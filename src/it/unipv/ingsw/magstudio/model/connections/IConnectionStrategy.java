package it.unipv.ingsw.magstudio.model.connections;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Interfaccia necessaria per poter implementare una Nuova Strategia di connessione al DB
 */
public interface IConnectionStrategy {
    Connection connect();
    void disconnect();
    boolean isOpen();
}
