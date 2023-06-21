package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.util.Encryption;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLException;

public class GestoreAccount {
    /**
     * Permette di controllare se l'accoppiata nome utente e password sono corrette
     * @param nomeUtente Il nome utente
     * @param password La password
     * @return L'esito dell'operazione, 'true' -> credenziali corrette; 'false' -> credenziali errate
     */
    public static boolean controllaCredenziali(String nomeUtente, String password) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query<Long> query = session.createNativeQuery("SELECT COUNT(*) FROM T_PERSONA tp WHERE tp.PASSWORD = :password AND tp.ID = (SELECT p.ID FROM Persona p WHERE p.NOME_UTENTE = :nomeUtente)", Long.class);
        query.setParameter("nomeUtente", nomeUtente);
        query.setParameter("password",  Encryption.SHA256Encryptor(password));
        Long result = query.uniqueResult();

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        return result != null && result > 0;
    }

    public static boolean impostaPassword(String nomeUtente, String password) {
        password = Encryption.SHA256Encryptor(password);

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        Query query = session.createNativeQuery("SELECT COUNT(*) FROM T_Persona tp WHERE tp.id = (SELECT p.ID FROM Persona p WHERE p.NOME_UTENTE = :nomeUtente)",Integer.class);
        query.setParameter("nomeUtente", nomeUtente);
        Integer result = (Integer) query.uniqueResult();

        session.getTransaction().commit();

        int rows = 0;
        if(result < 1){
            session.getTransaction().begin();

            Query insert = session.createNativeQuery("INSERT INTO T_Persona(PASSWORD,ID) VALUES (:password,(SELECT p.ID FROM Persona p WHERE p.NOME_UTENTE = :nomeUtente))",Integer.class);
            insert.setParameter("nomeUtente", nomeUtente);
            insert.setParameter("password", password);
            rows = insert.executeUpdate();

            session.getTransaction().commit();
        }

        session.close();
        sessionFactory.close();

        return rows == 1;
    }
}
