package com.cc.sp2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);
        System.out.println(context.getBean("testBean"));
        System.out.println(context.getBean("testBean1"));
    }
}
