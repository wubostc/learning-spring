package com.cc.sp2.controller;

import com.cc.sp2.exception.MyBusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    @GetMapping("/exception01")
    public String exceptionHandler01() {
        throw new RuntimeException("exception01");
    }


    @GetMapping("/exception02")
    public String exceptionHandler02() {
        throw MyBusinessException.fail("fail with 02");
    }

    @GetMapping("/exception03")
    public String exceptionHandler03() {
        int a = 1 / 0;
        return "hello";
    }
}
