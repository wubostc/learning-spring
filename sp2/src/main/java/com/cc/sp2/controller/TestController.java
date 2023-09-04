package com.cc.sp2.controller;

import com.cc.sp2.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    TestService testService;;

    @GetMapping("/test01")
    public String test01Handler(@RequestParam(value = "p1", required = false) Integer p1) {
        return "test01 ok " + p1;
    }


    @GetMapping("/test")
    public String testHandler(){
        new Object();
        System.out.println("testHandler");
        return testService.handleTest();
    }
}
