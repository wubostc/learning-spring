package com.scs.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// 声明交换机/队列和他们的绑定关系，并且在application.yml中配置了虚拟主机/test
@Configuration
public class RabbitmqConfiguration {
    @Autowired
    CachingConnectionFactory connectionFactory;

    @Bean("myContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(1); // 一次只能取一个
//        factory.setConcurrentConsumers(3);
//        factory.setMaxConcurrentConsumers(10);
//        factory.setContainerCustomizer(container -> /* customize the container */);
        return factory;
    }


    // 交换机
    @Bean("directExchange")
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    // 队列，增加死信队列
    @Bean("yydsQueue")
    public Queue queue() {
        return QueueBuilder.nonDurable("yyds")
                // 添加死信交换机
                .deadLetterExchange("dlx.direct")
                // 添加死信rk
                .deadLetterRoutingKey("my-dl-yyds")
                // 消息存活5秒后自动删除，并转发到死信队列
//                .ttl(5000)
                .maxLength(3)
                .build();
    }

    // 绑定交换机和队列，routingKey为my-yyds
    @Bean("binding")
    public Binding binding(@Qualifier("directExchange") Exchange exchange,
                           @Qualifier("yydsQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("my-yyds").noargs();
    }


    // 死信交换机
    @Bean("directDlExchange")
    public Exchange dlExchange() {
        return ExchangeBuilder.directExchange("dlx.direct").build();
    }


    // 死信队列
    @Bean("yydsDlQueue")
    public Queue dlQueue() {
        return QueueBuilder.nonDurable("dl-yyds").build();
    }


    @Bean("dlBinding")
    public Binding dlBinding(@Qualifier("directDlExchange") Exchange exchange,
                             @Qualifier("yydsDlQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("my-dl-yyds").noargs();
    }


    //////////////// fanout ////////////////
    // fanout 会广播所有到queue

    @Bean("fanoutExchange")
    public Exchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("amq.fanout").build();
    }

    @Bean("fanoutQueue1")
    public Queue fanoutQueue1() {
        return QueueBuilder.nonDurable("yyds-fanoutQueue1")
                .build();
    }

    @Bean("fanoutQueue2")
    public Queue fanoutQueue2() {
        return QueueBuilder.nonDurable("yyds-fanoutQueue2")
                .build();
    }

    @Bean("fanoutBinding1")
    public Binding fanoutBinding1(@Qualifier("fanoutQueue1") Queue q1,
                                  @Qualifier("fanoutExchange") Exchange exchange) {
        return BindingBuilder.bind(q1)
                .to(exchange)
                .with("fanoutrk1")
                .noargs();

    }

    @Bean("fanoutBinding2")
    public Binding fanoutBinding2(@Qualifier("fanoutQueue2") Queue q2,
                                  @Qualifier("fanoutExchange") Exchange exchange) {
        return BindingBuilder.bind(q2)
                .to(exchange)
                .with("fanoutrk2")
                .noargs();

    }


    //////////////// topic ////////////////
    @Bean("topicExchange")
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange("amq.topic").build();
    }

    @Bean("topicQueue1")
    public Queue topicQueue1() {
        return QueueBuilder.nonDurable("yyds-topic-queue")
                .build();
    }

    @Bean("topicBinding")
    public Binding topicBinding(@Qualifier("topicQueue1") Queue queue,
                                @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                // *：至少一个字符；#：至少零个字符
                .with("*.abc.*")
                .noargs();
    }


    //////////////// headers ////////////////
    @Bean("headersExchange")
    public Exchange headersExchange() {
        return ExchangeBuilder.headersExchange("amq.headers").build();
    }

    @Bean("headersQueue1")
    public Queue headersQueue1() {
        return QueueBuilder.nonDurable("yyds-headers-queue")
                .build();
    }

    @Bean("headersBinding")
    public Binding headersBinding(@Qualifier("headersQueue1") Queue queue,
                                  @Qualifier("headersExchange") HeadersExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                // 消息headers 只要存在key x-filter-name和x-deliver，就会发送给队列
                .whereAny("x-filter-name", "x-deliver")
                .exist();
    }
}
