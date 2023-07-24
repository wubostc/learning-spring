package com.demo.c01;

import com.demo.c01.Controller.HelloController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan(basePackages = {"com.demo.c01.Config.pk1"})
public class C01Application {

    public static void main(String[] args) {
        ApplicationContext run = SpringApplication.run(C01Application.class, args);

        HelloController helloController = (HelloController) run.getBean("HelloController");

        System.out.println("helloController.hello() = " + helloController.hello());
    }

}
