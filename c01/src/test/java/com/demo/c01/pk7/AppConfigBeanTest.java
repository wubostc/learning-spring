package com.demo.c01.pk7;

import com.demo.c01.Config.Security;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppConfigBeanTest {

    @Autowired
    private Security security;

    @Test
    void getSSS() {
        System.out.println("security.toString() = " + security.toString());
    }
}