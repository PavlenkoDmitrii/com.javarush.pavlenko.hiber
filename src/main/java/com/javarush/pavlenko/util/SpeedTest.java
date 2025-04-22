package com.javarush.pavlenko.util;

import com.javarush.pavlenko.config.hibernate.HibernateConnector;
import com.javarush.pavlenko.config.redis.RedisConnector;
import com.javarush.pavlenko.entity.City;
import com.javarush.pavlenko.entity.redis.CityCountry;
import com.javarush.pavlenko.service.FetchData;
import com.javarush.pavlenko.service.MySQLData;
import com.javarush.pavlenko.service.RedisData;
import com.javarush.pavlenko.service.TransformationData;
import org.hibernate.SessionFactory;
import redis.clients.jedis.Jedis;

import java.util.List;

import static java.util.Objects.nonNull;

public class SpeedTest {
    private final SessionFactory sessionFactory;
    private final Jedis jedis;
    private final FetchData fetchData;
    private final TransformationData transformData;
    private final RedisData redisData;
    private final MySQLData mySQLData;

    public SpeedTest() {
        fetchData = new FetchData();
        transformData = new TransformationData();
        redisData = new RedisData();
        mySQLData = new MySQLData();
        sessionFactory = HibernateConnector.getSessionFactory();
        jedis = RedisConnector.getRedisConnector();
    }

    public void getAllData() {

        List<City> allCities = fetchData.getFetchedData();
        List<CityCountry> preparedData = transformData.getTransformedData(allCities);
        redisData.pushToRedis(preparedData);
        sessionFactory.getCurrentSession().close();
    }

    public void run() {
        List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

        long startRedis = System.currentTimeMillis();
        redisData.testRedisData(ids);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        mySQLData.testMysqlData(ids);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));
    }

    public void shutdown() {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(jedis)) {
            jedis.shutdown();
        }
    }
}
