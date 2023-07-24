package com.cc.sp2.controller;

import com.cc.sp2.service.TestAspectjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAspectjController {
    @Autowired
    private TestAspectjService testAspectjService;

    @GetMapping("/TestAspectj01")
    public String test01Handler() {
        String s = testAspectjService.test01();
        return "okya: " + s;
    }
}
