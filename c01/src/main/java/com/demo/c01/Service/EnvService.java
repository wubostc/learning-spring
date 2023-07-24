package com.demo.c01.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EnvService {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private String redisPort;

    int printEnv() {
        System.out.println("redisHost = " + redisHost + " redisPort = " + redisPort);
        return 1000;
    }
}
