package com.demo.c01.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnvServiceTest {
    @Autowired
    private EnvService service;

    @Test
    void printEnv() {
        service.printEnv();
    }
}