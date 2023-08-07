package com.scs;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableAutoDataSourceProxy
public class BorrowServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BorrowServiceApplication.class, args);
    }
}
