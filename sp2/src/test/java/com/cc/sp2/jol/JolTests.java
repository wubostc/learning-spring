package com.cc.sp2.jol;


import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class JolTests {
    @Test
    void test01() {
        Object o = new Object();
        System.out.println(o);
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
//        ThreadPoolExecutor executor = new ThreadPoolExecutor();
    }
}
