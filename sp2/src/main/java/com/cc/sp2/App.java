package com.cc.sp2;

import com.cc.sp2.controller.SecKillController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Test.class);
//        System.out.println(context.getBean("testBean"));

//        System.out.println(context.getBean("testBean"));
    }
}
