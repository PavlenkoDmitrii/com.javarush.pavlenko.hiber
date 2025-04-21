package com.javarush.pavlenko;

import com.javarush.pavlenko.entity.City;
import com.javarush.pavlenko.entity.redis.CityCountry;
import com.javarush.pavlenko.service.FetchData;
import com.javarush.pavlenko.service.DataRedis;
import com.javarush.pavlenko.service.TransformationData;
import java.util.List;

public class Main {

    private final FetchData fetchData;
    private final TransformationData transformData;
    private final DataRedis dataRedis;


    public Main() {
        fetchData = new FetchData();
        transformData = new TransformationData();
        dataRedis = new DataRedis();
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<City> allCities = main.fetchData.getFetchedData();
        List<CityCountry> preparedData = main.transformData.getTransformedData(allCities);
        main.dataRedis.pushToRedis(preparedData);
    }
}