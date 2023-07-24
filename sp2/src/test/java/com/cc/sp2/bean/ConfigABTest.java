package com.cc.sp2.bean;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ConfigABTest {
    @Test
     public void test01() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cycle.xml");

        A a = context.getBean(A.class);
        B b = context.getBean(B.class);
        System.out.println("a = " + a + " b = " + a.getB());
        System.out.println("b = " + b + " a = " + b.getA());
    }
}