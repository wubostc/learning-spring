package com.demo.c01.Config.pk1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigMapAndListTest {
    @Autowired
    private ConfigMapAndList list;


    @Test
    void testToString() {
        System.out.println("list.toString() = " + list.toString());

    }
}