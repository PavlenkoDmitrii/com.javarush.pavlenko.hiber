package com.javarush.pavlenko.service;

import com.javarush.pavlenko.config.hibernate.HibernateConnector;
import com.javarush.pavlenko.entity.City;
import com.javarush.pavlenko.entity.CountryLanguage;
import com.javarush.pavlenko.repository.dao.CityDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Set;

public class MySQLData {

    private final SessionFactory sessionFactory;
    private final CityDAO cityDAO;

    public MySQLData() {
        sessionFactory = HibernateConnector.getSessionFactory();
        cityDAO = new CityDAO(sessionFactory);

    }

    public void testMysqlData(List<Integer> ids) {
        try (Session session = HibernateConnector.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            for (Integer id : ids) {
                City city = cityDAO.getById(id);
                Set<CountryLanguage> languages = city.getCountry().getCountryLanguages();
            }
            session.getTransaction().commit();
        }
    }
}
