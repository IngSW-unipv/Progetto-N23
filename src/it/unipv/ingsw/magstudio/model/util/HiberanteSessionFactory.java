package it.unipv.ingsw.magstudio.model.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HiberanteSessionFactory {
    public static SessionFactory getSessionFactory(){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        return cfg.buildSessionFactory();
    }
}
