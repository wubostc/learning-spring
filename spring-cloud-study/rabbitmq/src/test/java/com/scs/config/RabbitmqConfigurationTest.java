package com.scs.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqConfigurationTest {
    @Autowired
    RabbitTemplate template;


    @Test
    void publisher() {
        // 发布消息
        Object o = template.convertSendAndReceive("amq.direct", "my-yyds", "1234098");
        System.out.println("生产者收到响应 = " + o);
    }
}