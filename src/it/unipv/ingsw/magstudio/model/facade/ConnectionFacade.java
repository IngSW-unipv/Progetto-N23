package it.unipv.ingsw.magstudio.model.facade;

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

    public void connect(){
        strategy.connect();
    }

    public void close(){
        if(strategy.isOpen())
            strategy.disconnect();
    }

    public ResultSet executeQuery(String query){
        return strategy.executeQuery(query);
    }

    public int executeUpdate(String query){
        return strategy.executeUpdate(query);
    }

    public boolean controllaCredenziali(String nomeUtente, String password) throws SQLException {
        //TODO: SQL injection
        ResultSet rs =executeQuery("SELECT COUNT(*) AS N FROM T_PERSONA, PERSONA WHERE NOME_UTENTE='"+nomeUtente+"' AND PASSWORD='"+password+"'");
        rs.next();
        if(rs.getInt("N") == 1){
            return true;
        }
        return false;
    }
}
