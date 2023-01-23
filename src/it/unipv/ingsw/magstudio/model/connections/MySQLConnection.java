package it.unipv.ingsw.magstudio.model.connections;

import java.sql.*;

public class MySQLConnection implements IConnectionStrategy {
    private final String url;
    private String host;
    private String username;
    private String password;
    private int port;
    private String schema;
    private Connection connection;

    private MySQLConnection(Builder builder) {
        this.host = builder.host;
        this.username = builder.username;
        this.password = builder.password;
        this.port = builder.port;
        this.schema = builder.schema;
        this.url = String.format("jdbc:mysql://%s:%d/%s", this.host,this.port,this.schema);
    }

    @Override
    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL Database");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    @Override
    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static class Builder {
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