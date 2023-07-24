package com.cc.sp2.threadLocal;


import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTest {
    @Test
    void test01() {
        int a = 0x80000000;

        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
//        map.put();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread();
                        t.setName("abcde");
                        return t;
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());

        executor.execute(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("i = " + i);
            }
        });
    }
}
