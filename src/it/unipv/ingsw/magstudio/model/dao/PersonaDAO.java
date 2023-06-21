package it.unipv.ingsw.magstudio.model.dao;


import it.unipv.ingsw.magstudio.model.bean.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Optional;

public class PersonaDAO implements IPersonaDAO{

    /**
     * Crea un nuovo oggetto PersonaDAO
     */
    public PersonaDAO() {
    }

    /**
     * Restituisce l'oggetto Persona identificato dal nome utente passato
     *
     * @param p L'oggetto Persona con il nome utente
     * @return L'oggetto Persona
     * @see Persona
     */
    @Override
    public Optional<Persona> selectByNomeUtente(Persona p){
        Optional<Persona> out = Optional.empty();

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        Persona a = session.get(Persona.class, p.getNomeUtente());

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        if(a != null){
            out = Optional.of(a);
        }
        return out;
    }

    @Override
    public void insertPersona(Persona p){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        session.persist(p);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }

    @Override
    public boolean updatePersona(Persona p){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        session.merge(p);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        return true;
    }

    @Override
    public boolean dropPersona(Persona p){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        Query query = session.createNativeQuery("DELETE FROM T_Persona tp WHERE tp.id = (SELECT p.ID FROM Persona p WHERE p.NOME_UTENTE = :nomeUtente)");
        query.setParameter("nomeUtente", p.getNomeUtente());
        query.executeUpdate();

        session.remove(p);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        return true;
    }
}
