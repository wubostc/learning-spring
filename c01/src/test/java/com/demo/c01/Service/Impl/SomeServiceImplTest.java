package com.demo.c01.Service.Impl;

import com.demo.c01.Service.SomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SomeServiceImplTest {

    @Autowired
    private SomeService someService;

    @Test
    void testAspectBefore() {
        someService.query(11111);
        someService.save("hahah🐶狗啊", "coddd⬆️");
    }
}