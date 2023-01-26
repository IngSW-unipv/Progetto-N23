package it.unipv.ingsw.magstudio.model.connections;

import java.sql.*;

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

    public static MySQLConnection getIstance() {
        if(istance == null){
            istance = new MySQLConnection.Builder()
                    .setHost("127.0.0.1")
                    .setPort(5656)
                    .setSchema("HIVEHUB")
                    .setUsername("sql-hivehub")
                    .setPassword("Hivehub2023!")
                    .build();
        }
        return istance;
    }

    @Override
    public Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL Database");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
            System.out.println("Disconnected from MySQL Database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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