package com.javarush.pavlenko.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.pavlenko.entity.redis.CityCountry;
import com.javarush.pavlenko.config.redis.RedisConnector;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisData {

    private final Jedis jedis;
    private final ObjectMapper objectMapper;

    public RedisData() {
        jedis = RedisConnector.getRedisConnector();
        objectMapper = new ObjectMapper();
    }

    public void pushToRedis(List<CityCountry> data) {
        for (CityCountry cityCountry : data) {
            try {
                jedis.set(String.valueOf(cityCountry.getId()), objectMapper.writeValueAsString(cityCountry));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void testRedisData(List<Integer> ids) {
        for (Integer id : ids) {
            String value = jedis.get(String.valueOf(id));
            try {
                objectMapper.readValue(value, CityCountry.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}

