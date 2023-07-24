package com.demo.c01.Config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppConfigTest {
    @Autowired
    private AppConfig appConfig;

    @Test
    void test() {
        System.out.println("appConfig.getClass() = " + appConfig.getClass());
    }

    @Test
    void getMemo() {
        System.out.println("appConfig.getMemo() = " + appConfig.getMemo());
    }

    @Test
    void getOwner() {
        System.out.println("appConfig.getOwner() = " + appConfig.getOwner());
    }
}