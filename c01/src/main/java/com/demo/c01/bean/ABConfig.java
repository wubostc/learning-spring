package com.demo.c01.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ABConfig {
    @Bean
    public A a() {
        A a = new A();
        return a;
    }

    @Bean
    public B b() {
        B b = new B();
        return b;
    }
}
