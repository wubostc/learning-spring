package com.scs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);

        Object name = context.getEnvironment().getProperty("hello");
        System.err.println("hello : " + name);

    }
}
