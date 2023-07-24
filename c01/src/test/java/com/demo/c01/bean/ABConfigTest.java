package com.demo.c01.bean;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootTest
class ABConfigTest {


    @Test
    void aaa() {
    }

    @Test
    void bbb() {
    }

    @Test
    void test01() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ABConfig.class);
        A aaa = context.getBean(A.class);

        System.out.println("aaa = " + aaa.getB());
    }
}