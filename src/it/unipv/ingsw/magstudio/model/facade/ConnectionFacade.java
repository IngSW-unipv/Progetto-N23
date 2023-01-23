package it.unipv.ingsw.magstudio.model.facade;

import java.sql.ResultSet;
import it.unipv.ingsw.magstudio.model.connections.IConnectionStrategy;
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
        //TODO: implementare MySQL in locale
        System.out.println("Non ancora implementato");
        strategy = null;
    }

    private void setMySQLOverSSHStrategy(){
        strategy = new MySQLOverSSHConnection();
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
}
