package com.javarush.pavlenko.config.hibernate;

import com.javarush.pavlenko.entity.City;
import com.javarush.pavlenko.entity.Country;
import com.javarush.pavlenko.entity.CountryLanguage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnector {

    private static HibernateConnector instance;
    private final SessionFactory sessionFactory;

    private HibernateConnector() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(CountryLanguage.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new HibernateConnector();
        }
        return instance.sessionFactory;
    }
}
