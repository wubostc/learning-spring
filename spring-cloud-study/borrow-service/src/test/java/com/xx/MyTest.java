package com.xx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

@SpringBootTest
@Slf4j
public class MyTest {


    class User implements Serializable {
        public static final long serialVersionUID = 1L;


    }

    public class A implements Serializable {
        private static final long serialVersionUID = 203480238492L;
    }

    public class BB implements  Runnable {

        @Override
        public void run() {
            
        }
    }

    @Test
    void test() {
//        User user = new User();
//        System.out.println("a1111111");
//        System.out.println(ClassLayout.parseInstance(user).toPrintable());
//
//        synchronized (user) {
//            System.out.println(ClassLayout.parseInstance(user).toPrintable());
//        }
//
//        System.out.println(ClassLayout.parseInstance(user).toPrintable());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        log.info("notification using " + thread.getName());
                        return thread;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        log.info("!notification using " + thread.getName());
                        thread.start();
                    }
                }
        );

        ThreadLocal<Object> threadLocal = new ThreadLocal<>();

        threadLocal.set(1);


        List<Future<String>> list = new LinkedList<>();

        for (int i = 0; i<10; i++) {
            final int f = i;
            Future<String> submit = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

                    factory.setConnectTimeout(1000);

                    RestTemplate restTemplate = new RestTemplate(factory);
                    long l = (long) (Math.random() * 2000);
                    Thread.sleep(l);
                    System.out.println("[" + f + "]" + " l = " + l);

//                    restTemplate.exchange()
                    return "[" + f + "] ok" + l;
                }
            });

            list.add(submit);
        }

        for (Future<String> future : list) {
            try {
                String result = future.get(2, TimeUnit.SECONDS);

                System.out.println("result = " + result);
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                System.out.println(e);
            }
        }


        ArrayList<Object> list1 = new ArrayList<>();


    }

//    public static void main(String[] args) {
////        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(13);
////
////        String encoded = encoder.encode("123");
////
////        System.out.println("encoded = " + encoded);
//
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(1);
//
//        StringBuffer sbf = new StringBuffer();
//        sbf.append(1);
//
//        String s = "sdsd";
//
//        s.compareTo(s);
//
//        Map<Object, Object> map = new HashMap<>();
//
//        map.put(1, "");
//
//    }
}
