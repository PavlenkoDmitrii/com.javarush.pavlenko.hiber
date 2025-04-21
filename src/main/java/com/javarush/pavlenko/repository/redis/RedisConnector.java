package com.javarush.pavlenko.repository.redis;

import redis.clients.jedis.Jedis;

public class RedisConnector {

    private static RedisConnector instance;
    private final Jedis jedis;

    private RedisConnector() {
        jedis = new Jedis("localhost", 6379);
        jedis.connect();
    }

    public static Jedis getRedisConnector() {
        if (instance == null) {
            instance = new RedisConnector();
        }
        System.out.println("\nConnected to Redis\n");
        return instance.jedis;
    }
}