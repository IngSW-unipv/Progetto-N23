package it.unipv.ingsw.magstudio.model.dao;

import it.unipv.ingsw.magstudio.model.bean.*;
import it.unipv.ingsw.magstudio.model.util.HiberanteSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class ProdottoDAO implements IProdottoDAO{

    public ProdottoDAO(){
    }
    @Override
    public Optional<Prodotto> selectByCodice(Prodotto p){
        Optional<Prodotto> out = Optional.empty();

        SessionFactory sessionFactory = HiberanteSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        Prodotto p1 = session.get(Prodotto.class, p.getCodice());

        session.getTransaction().commit();

        if(p1 != null){
            session.refresh(p1);
            out = Optional.of(p1);
        }

        session.close();
        sessionFactory.close();

        return out;
    }

    @Override
    public boolean insertProdotto(Prodotto p){
        SessionFactory sessionFactory = HiberanteSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        session.persist(p);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
        return true;
    }

    @Override
    public boolean updateProdotto(Prodotto p){
        //TODO: Se si inserisce una posizione gia occupata
        //      sostituisce l'id del prodotto con quello nuovo
        SessionFactory sessionFactory = HiberanteSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        session.merge(p);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        return true;
    }

    @Override
    public boolean dropProdotto(Prodotto p){
        SessionFactory sessionFactory = HiberanteSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        session.remove(p);

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
        return true;
    }
}
