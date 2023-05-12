package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.*;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProdottoDAO implements IProdottoDAO{
    private final ConnectionFacade connectionFacade;

    public ProdottoDAO(){
        this.connectionFacade = ConnectionFacade.getIstance();
    }
    @Override
    public Optional<Prodotto> selectByCodice(Prodotto p) throws SQLException {
        Optional<Prodotto> out = Optional.empty();
        Connection connection=connectionFacade.connect();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PRODOTTO WHERE CODICE=?")) {
            ps.setInt(1, p.getCodice());
            ResultSet rs = ps.executeQuery();
            //TODO: finire select by codice
            /*if (rs.next()) {
                out = Optional.of(new Prodotto(
                        /*Inserire
                ));
            }*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            connectionFacade.close();
        }
        return out;
    }

    @Override
    public Optional<Prodotto> selectByPosizione(Posizione p) throws SQLException {
        //TODO: finire select by posizione
        return Optional.empty();
    }

    @Override
    public Optional<Prodotto> selectByNome(Prodotto p) throws SQLException {
        Optional<Prodotto> out = Optional.empty();
        Connection connection=connectionFacade.connect();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PRODOTTO WHERE CODICE=?")) {
            ps.setString(1, p.getNome());
            ResultSet rs = ps.executeQuery();
            //TODO: select by nome
            /*if (rs.next()) {
                out = Optional.of(new Prodotto(
                        /*Inserire
                ));
            }*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            connectionFacade.close();
        }
        return out;
    }

    @Override
    public boolean insertProdotto(Prodotto p) throws SQLException {
        PreparedStatement ps = null;
        int queryResult = 0;
        try{
            Connection connection = connectionFacade.connect();
            String query = "INSERT INTO PRODOTTO(" +
                    "CODICE," +
                    "NOME," +
                    "DESCRIZIONE," +
                    "QNT" +
                    ") VALUES(?,?,?,?)";
            ps = connection.prepareStatement(query);

            ps.setInt(1, p.getCodice());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getDescrizione());
            ps.setInt(4, p.getQnt());

            queryResult = ps.executeUpdate();
        }finally {
            ps.close();
            connectionFacade.close();
        }

        return queryResult > 0;
    }

    @Override
    public boolean updateProdotto(Prodotto p) throws SQLException {
        PreparedStatement ps = null;
        int queryResult = 0;
        //TODO: finire update prodotto
        try{
            Connection connection = connectionFacade.connect();
            String query = "UPDATE PRODOTTO SET "; /*Inserire*/

            queryResult = ps.executeUpdate();
        }finally {
            ps.close();
            connectionFacade.close();
        }
        return queryResult > 0;
    }

    @Override
    public boolean dropProdotto(Prodotto p) throws SQLException {
        PreparedStatement ps = null;
        int queryResult = 0;
        try{
            Connection connection = connectionFacade.connect();
            String query = "DELETE FROM PRODOTTO " +
                    "WHERE CODICE = ?";
            //TODO: finire drop prodotto
            ps = connection.prepareStatement(query);

            ps.setInt(1,p.getCodice());

            queryResult = ps.executeUpdate();
        }finally {
            ps.close();
            connectionFacade.close();
        }

        return queryResult > 0;
    }
    public class PosizioneDAO implements IPosizioneDAO{

        @Override
        public boolean insertPosizione(Prodotto p) throws SQLException {
            //TODO: da implementare
            return false;
        }
    }
}
