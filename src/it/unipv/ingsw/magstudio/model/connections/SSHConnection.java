package it.unipv.ingsw.magstudio.model.connections;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHConnection{
    private static SSHConnection istance;
    private final String host;
    private final String user;
    private final String password;

    private final String internalHost;
    private final int lport;
    private final int rport;

    private Session session;

    private SSHConnection(Builder builder) {
        this.host = builder.host;
        this.user = builder.user;
        this.password = builder.password;
        this.internalHost = builder.internalHost;
        this.lport = builder.lport;
        this.rport = builder.rport;
    }

    public static SSHConnection getIstance() {
        if(istance == null)
            istance = new SSHConnection.Builder()
                    .setHost("195.231.85.22")
                    .setUser("hivehub")
                    .setPassword("hivehub")
                    .setRemoteHost("127.0.0.1")
                    .setListenPort(5656)
                    .setRecivePort(3306)
            .build();
        return istance;
    }

    public void connect() {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("Connected to " + host);
            session.setPortForwardingL(lport, internalHost, rport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        session.disconnect();
        System.out.println("Disconnected from " + host);
    }


    public boolean isOpen() {
        return session.isConnected();
    }

    private static class Builder {
        private String host;
        private String user;
        private String password;
        private String internalHost;
        private int lport, rport;

        public Builder setRemoteHost(String internalHost) {
            this.internalHost = internalHost;
            return this;
        }
        public Builder setListenPort(int lport) {
            this.lport = lport;
            return this;
        }
        public Builder setRecivePort(int rport) {
            this.rport = rport;
            return this;
        }
        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public SSHConnection build() {
            return new SSHConnection(this);
        }
    }
}
