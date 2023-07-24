package com.demo.c01.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("HelloController")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
