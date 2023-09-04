package com.cc.sp2.controller;

import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

//@RestController
public class SecKillController implements BeanPostProcessor, InitializingBean {
    public static final String REDIS_LOCK = "redis_lock";

    @Autowired
    private StringRedisTemplate srt;

    @Value("${server.port:6379}")
    private String serverPort;



    @Autowired
    @Qualifier("111")
    private Redisson myredisson;


//    @Resource(name = "redisson-1")
//    private Redisson redisson1;
//    @Resource(name = "redisson-2")
//    private Redisson redisson2;
//    @Resource(name = "redisson-3")
//    private Redisson redisson3;

    public SecKillController() {

    }

//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (bean == SecKillController.class) {
//
//
//        }
//
//        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RSemaphore redisSemaphore = myredisson.getSemaphore("redis_semaphore");
        redisSemaphore.trySetPermits(10);
    }

    @GetMapping("/sk9")
    public String secKillHandler9() {
//        RLock rLock1 = redisson1.getLock(REDIS_LOCK);
//        RLock rLock2 = redisson2.getLock(REDIS_LOCK);
//        RLock rLock3 = redisson3.getLock(REDIS_LOCK);
//        RLock rLock1 = myredisson.getLock(REDIS_LOCK);

        RLock rLock = myredisson.getFairLock("xxx");


        try {
            if (!rLock.tryLock()) {
                return "没有抢到锁";
            }

            // or
//            if (!rLock.tryLock()) {
//                return "没有抢到锁";
//            }

            String result = "抱歉，您没抢到……";

            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        } finally {

            rLock.unlock();
//                if ("1".equals(res.toString())) {
//                    System.out.println("释放锁成功");
//                } else {
//                    System.out.println("释放锁异常");
//                }


        }

    }


    @GetMapping("/sk8")
    public String secKillHandler8() throws InterruptedException {
//        RLock rLock1 = redisson1.getLock(REDIS_LOCK);
//        RLock rLock2 = redisson2.getLock(REDIS_LOCK);
//        RLock rLock3 = redisson3.getLock(REDIS_LOCK);
//        RLock rLock1 = myredisson.getLock(REDIS_LOCK);

        RSemaphore redisSemaphore = myredisson.getSemaphore("redis_semaphore");




        try {
            if (!redisSemaphore.tryAcquire(2)) {
                return "没有抢到锁";
            }

            // or
//            if (!rLock.tryLock()) {
//                return "没有抢到锁";
//            }

            String result = "抱歉，您没抢到……";

            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        } finally {

            redisSemaphore.release();
//                if ("1".equals(res.toString())) {
//                    System.out.println("释放锁成功");
//                } else {
//                    System.out.println("释放锁异常");
//                }


        }

    }

    @GetMapping("/sk7")
    public String secKillHandler7() {
        Redisson redisson1 = myredisson;
        RLock rLock = myredisson.getLock(REDIS_LOCK);

        try {

            if (!rLock.tryLock(20, 5, TimeUnit.SECONDS)) {
                return "没有抢到锁";
            }

            // or
//            if (!rLock.tryLock()) {
//                return "没有抢到锁";
//            }

            String result = "抱歉，您没抢到……";

            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            rLock.unlock();
//                if ("1".equals(res.toString())) {
//                    System.out.println("释放锁成功");
//                } else {
//                    System.out.println("释放锁异常");
//                }


        }

    }


    @GetMapping("/sk6")
    public String secKillHandler6() {
        Redisson redisson1 = myredisson;

        String clientId = UUID.randomUUID().toString();
        try {
            Boolean locked = srt.opsForValue().setIfAbsent(REDIS_LOCK, clientId, 600, TimeUnit.SECONDS);
            if (Boolean.FALSE.equals(locked)) {
                return "没有抢到锁";
            }

            String result = "抱歉，您没抢到……";

            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        } finally {
            try (JedisPool jedis = new JedisPool("127.0.0.1", 6379)) {
                List<String> keys = Collections.singletonList(REDIS_LOCK);
                List<String> argv = Collections.singletonList(clientId);
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                        "then return redis.call('del', KEYS[1]) " +
                        "end " +
                        "return 0";
                Object res = jedis.getResource().eval(script, keys, argv);
                if ("1".equals(res.toString())) {
                    System.out.println("释放锁成功");
                } else {
                    System.out.println("释放锁异常");
                }
            }

        }

    }




    @GetMapping("/sk5")
    public String secKillHandler5() {
        String clientId = UUID.randomUUID().toString();
        try {
            Boolean locked = srt.opsForValue().setIfAbsent(REDIS_LOCK, clientId, 5, TimeUnit.SECONDS);
            if (Boolean.FALSE.equals(locked)) {
                return "没有抢到锁";
            }

            String result = "抱歉，您没抢到……";

            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        } finally {

            try (JedisPool jedis = new JedisPool("127.0.0.1", 6379)) {
                List<String> keys = Collections.singletonList(REDIS_LOCK);
                List<String> argv = Collections.singletonList(clientId);
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                                "then return redis.call('del', KEYS[1]) " +
                                "end " +
                                "return 0";
                Object res = jedis.getResource().eval(script, keys, argv);
                if ("1".equals(res.toString())) {
                    System.out.println("释放锁成功");
                } else {
                    System.out.println("释放锁异常");
                }
            }

        }

    }

    @GetMapping("/sk4")
    public String secKillHandler4() {
        String clientId = UUID.randomUUID().toString();
        try {
            Boolean locked = srt.opsForValue().setIfAbsent(REDIS_LOCK, clientId, 5, TimeUnit.SECONDS);
            if (!locked) {
                return "没有抢到锁";
            }

            String result = "抱歉，您没抢到……";

            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        } finally {
            if (Objects.equals(srt.opsForValue().get(REDIS_LOCK), clientId)) {
                srt.delete(REDIS_LOCK);
            }
        }

    }

    @GetMapping("/sk2")
    public String secKillHandler2() {
        try {
            Boolean locked = srt.opsForValue().setIfAbsent(REDIS_LOCK, "I'm a lock", 5, TimeUnit.SECONDS);
            if (!locked) {
                return "没有抢到锁";
            }

            String result = "抱歉，您没抢到……";

            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        } finally {
            srt.delete(REDIS_LOCK);
        }

    }

    @GetMapping("/sk")
    public String secKillHandler() {
        String stoke = srt.opsForValue().get("sk:0008");
        int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
        if (amount > 0) {
            srt.opsForValue().set("sk:0008", String.valueOf(--amount));
            return "库存剩余" + amount + "台";
        }
        return "抱歉，您没抢到……";
    }

    @GetMapping("/sk1")
    public String secKillHandler1() {
        String result = "抱歉，您没抢到……";
        synchronized (this) {
            String stoke = srt.opsForValue().get("sk:0008");
            int amount = Objects.isNull(stoke) ? 0 : Integer.parseInt(stoke);
            if (amount > 0) {
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                result = "库存剩余" + amount + "台";
                result = result + " server is " + serverPort;
                System.out.println(result);
            }
            return result;
        }
    }


}
