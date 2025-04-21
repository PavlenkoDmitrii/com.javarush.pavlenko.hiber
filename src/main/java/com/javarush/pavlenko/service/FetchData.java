package com.javarush.pavlenko.service;

import com.javarush.pavlenko.entity.City;
import com.javarush.pavlenko.entity.Country;
import com.javarush.pavlenko.repository.dao.CityDAO;
import com.javarush.pavlenko.repository.dao.CountryDAO;
import com.javarush.pavlenko.repository.hibernate.HibernateConnector;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class FetchData {

    private final SessionFactory sessionFactory;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;

    public FetchData() {
        sessionFactory = HibernateConnector.getSessionFactory();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
    }

    private List<City> fetchData() {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();
            List<Country> countries = countryDAO.getAll();
            int totalCount = cityDAO.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(cityDAO.getItems(i, step));
            }
            session.getTransaction().commit();
            return allCities;
        }
    }

    public List<City> getFetchedData(){
        return fetchData();
    }
}
