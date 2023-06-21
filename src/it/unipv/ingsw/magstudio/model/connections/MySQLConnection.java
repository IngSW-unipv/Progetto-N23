package it.unipv.ingsw.magstudio.model.connections;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class MySQLConnection implements IConnectionStrategy {
    private static MySQLConnection istance;
    private final String url;
    private final String host;
    private final String username;
    private final String password;
    private final int port;
    private final String schema;
    private Connection connection;

    private MySQLConnection(Builder builder) {
        this.host = builder.host;
        this.username = builder.username;
        this.password = builder.password;
        this.port = builder.port;
        this.schema = builder.schema;
        this.url = String.format("jdbc:mysql://%s:%d/%s", this.host,this.port,this.schema);
    }

    /**
     * Fornisce l'istanza dell'oggetto MySQLConnection
     * @return L'istanza di MySQLConnection
     */
    public static MySQLConnection getIstance() {
        if(istance == null){
            try (InputStream file = new FileInputStream("config.properties")) {
                Properties prop = new Properties();

                prop.load(file);

                istance = new MySQLConnection.Builder()
                        .setHost(prop.getProperty("mysql.host"))
                        .setPort(Integer.parseInt(prop.getProperty("mysql.port")))
                        .setSchema(prop.getProperty("mysql.schema"))
                        .setUsername(prop.getProperty("mysql.username"))
                        .setPassword(prop.getProperty("mysql.password"))
                .build();
            } catch (FileNotFoundException ex) {
                System.out.println("File di Properties non trovato");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return istance;
    }


    /**
     * Metodo per effettuare la connessione al server MySQL
     * @return L'oggetto Connection
     * @throws SQLException
     */
    @Override
    public Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL Database");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    /**
     * Metodo per chiudere la connessione al Database MySQL
     */
    @Override
    public void disconnect() {
        try {
            connection.close();
            System.out.println("Disconnected from MySQL Database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo per controllare se la connessione al db è aperta
     * @return true se la connessione è aperta, altrimenti false
     */
    @Override
    public boolean isOpen() {
        boolean out;
        try {
            out = !connection.isClosed();
        } catch (SQLException e) {
            out = false;
        }
        return out;
    }

    private static class Builder {
        private String host;
        private String username;
        private String password;
        private int port;
        private String schema;

        public Builder setHost(String host){
            this.host = host;
            return this;
        }
        public Builder setPort(int port) {
            this.port = port;
            return this;
        }
        public Builder setSchema(String schema) {
            this.schema = schema;
            return this;
        }
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public MySQLConnection build() {
            return new MySQLConnection(this);
        }
    }
}