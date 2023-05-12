package it.unipv.ingsw.magstudio.model.connections;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    /**
     * Fornisce l'istanza dell'oggetto SSHConnection
     * @return L'istanza dell'oggetto
     */
    public static SSHConnection getIstance() {
        if(istance == null) {
            try (InputStream file = new FileInputStream("config.properties")) {
                Properties prop = new Properties();

                prop.load(file);

                istance = new SSHConnection.Builder()
                        .setHost(prop.getProperty("ssh.host"))
                        .setUser(prop.getProperty("ssh.username"))
                        .setPassword(prop.getProperty("ssh.password"))
                        .setRemoteHost(prop.getProperty("ssh.remoteHost"))
                        .setListenPort(Integer.parseInt(prop.getProperty("ssh.listenPort")))
                        .setRecivePort(Integer.parseInt(prop.getProperty("ssh.recivePort")))
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
     * Metodo per aprire la connessione SSH
     * @throws JSchException
     */
    public void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        System.out.println("Connected to " + host);
        session.setPortForwardingL(lport, internalHost, rport);
    }

    /**
     * Metodo per chiudere la connessione al Database MySQL
     */
    public void disconnect() {
        session.disconnect();
        System.out.println("Disconnected from " + host);
    }


    /**
     * Metodo per controllare se la connessione al db è aperta
     * @return true se la connessione è aperta, altrimenti false
     */
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
