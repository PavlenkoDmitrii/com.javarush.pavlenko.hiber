package com.javarush.pavlenko.service;

import com.javarush.pavlenko.entity.City;
import com.javarush.pavlenko.entity.Country;
import com.javarush.pavlenko.entity.CountryLanguage;
import com.javarush.pavlenko.entity.redis.CityCountry;
import com.javarush.pavlenko.entity.redis.Language;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransformationData {

    private List<CityCountry> transformData(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry res = new CityCountry();
            res.setId(city.getId());
            res.setName(city.getName());
            res.setPopulation(city.getPopulation());
            res.setDistrict(city.getDistrict());

            Country country = city.getCountry();
            res.setAlternativeCountryCode(country.getCode2());
            res.setContinent(country.getContinent());
            res.setCountryCode(country.getCode());
            res.setCountryName(country.getName());
            res.setCountryPopulation(country.getPopulation());
            res.setCountryRegion(country.getRegion());
            res.setCountrySurfaceArea(country.getSurfaceArea());

            Set<CountryLanguage> countryLanguages = country.getCountryLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setOfficial(cl.getOfficial());
                language.setPercentage(cl.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            res.setLanguages(languages);

            return res;
        }).collect(Collectors.toList());
    }

    public List<CityCountry> getTransformedData(List<City> cities){
        return transformData(cities);
    }
}
