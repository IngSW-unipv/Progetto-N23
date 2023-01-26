package it.unipv.ingsw.magstudio.model.facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.ingsw.magstudio.model.connections.IConnectionStrategy;
import it.unipv.ingsw.magstudio.model.connections.MySQLConnection;
import it.unipv.ingsw.magstudio.model.connections.MySQLOverSSHConnection;

public class ConnectionFacade {
    public enum ConnectionStrategy {
        MYSQL, MYSQL_OVER_SSH;
    }

    private IConnectionStrategy strategy;
    private Connection connection;
    public ConnectionFacade(){}

    public void setStrategy(ConnectionStrategy strategy){
        switch (strategy){
            case MYSQL -> setMySQLStrategy();
            case MYSQL_OVER_SSH -> setMySQLOverSSHStrategy();
        }
    }

    private void setMySQLStrategy(){
        strategy = MySQLConnection.getIstance();
    }

    private void setMySQLOverSSHStrategy(){
        strategy = MySQLOverSSHConnection.getIstance();
    }

    public Connection connect(){
        this.connection = strategy.connect();
        return this.connection;
    }

    public void close(){
        if(strategy.isOpen())
            strategy.disconnect();
    }

    public boolean controllaCredenziali(String nomeUtente, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS N FROM T_PERSONA, PERSONA WHERE NOME_UTENTE=? AND PASSWORD=?");
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
