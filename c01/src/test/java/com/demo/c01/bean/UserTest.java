package com.demo.c01.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    @Autowired
    private User user;

    @Test
    void testMyBeanFP() {
        System.out.println("user.getName() = " + user.getName());
    }
}