package com.scs;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableFeignClients
@EnableAutoDataSourceProxy
public class BorrowServiceApplication {
    public static void main(String[] args) {
        // security context holder 子线程继承当前用户 session，也可以在vm参数加：
        // -Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL
        System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY, SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);


        SpringApplication.run(BorrowServiceApplication.class, args);
    }
}
