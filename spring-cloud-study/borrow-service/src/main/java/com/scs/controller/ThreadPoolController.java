package com.scs.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;


@Controller
@Slf4j
public class ThreadPoolController implements DisposableBean, InitializingBean {
    ThreadPoolExecutor executor;


    @Override
    public void afterPropertiesSet() throws Exception {

        executor = new ThreadPoolExecutor(5, 20, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(50),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setDaemon(true);
                        log.info("notification using " + thread.getName());
                        return thread;
                    }
                },

                new ThreadPoolExecutor.CallerRunsPolicy()

//                new RejectedExecutionHandler() {
//                    @Override
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        log.info("ThreadPool rejectedExecution" + r.toString());
//                        throw MyBusinessException.fail("ThreadPool rejectedExecution");
//                    }
//                }
//                new RejectedExecutionHandler() {
//                    @Override
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        Thread thread = new Thread(r);
//                        thread.setDaemon(true);
//                        log.info("!notification using " + thread.getName());
//                        thread.start();
//                    }
//                }
        );
    }

    @GetMapping("/thread1")
    @ResponseBody
    public List<String> thread1() {
        if (executor.isTerminating() || executor.isTerminated() || executor.isShutdown()) {
            return null;
        }

        System.out.println("thread1Handler: " + Thread.currentThread().getName());

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


        ArrayList<String> result = new ArrayList<>(list.size());


        for (Future<String> future : list) {
            try {
                String s = future.get(2, TimeUnit.SECONDS);

                result.add(s);

                System.out.println("result = " + result);
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                System.out.println(e);
            }
        }

        return result;

    }

    @Override
    public void destroy() throws Exception {
        System.out.println("waiting shutdown...");

        executor.shutdown();

        long s = System.currentTimeMillis();

        while (!executor.isTerminated()) {
            Thread.yield();
        }

        long end = System.currentTimeMillis();

        System.out.println("destroyed, spend " + (end - s) + " ms");
    }
}
