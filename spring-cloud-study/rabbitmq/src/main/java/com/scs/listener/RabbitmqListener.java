package com.scs.listener;

import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * Consumer
 */
@Component
public class RabbitmqListener {
    @SneakyThrows
    @RabbitListener(queues = {"yyds"}, containerFactory = "myContainerFactory")
    void listener(Message message) {
        String str = new String(message.getBody());

        System.out.println("1 receive... " + str);

        Thread.sleep(500);
        // 消费者返回响应
//        return "响应成功👌";
    }

    @SneakyThrows
    @RabbitListener(queues = {"yyds"}, containerFactory = "myContainerFactory")
    void listener2(Message message) {
        String str = new String(message.getBody());

        System.out.println("2 receive... " + str);
        Thread.sleep(500);

        // 消费者返回响应
//        return "响应成功👌";
    }

//    @RabbitListener(queues = {"dl-yyds"})
//    void dlListener(Message message) {
//        String str = new String(message.getBody());
//
//        System.out.println("死信receive... " + str);
//
//        // 消费者返回响应
////        return "响应成功👌，dead letter...";
//    }


    //////// fanout ////////
    @RabbitListener(queues = {"yyds-fanoutQueue1"})
    void fanoutListener1(Message message) {
        String str = new String(message.getBody());

        System.out.println("fanoutQueue1... " + str);

        // 消费者返回响应
//        return "响应成功👌";
    }

    @RabbitListener(queues = {"yyds-fanoutQueue2"})
    void fanoutListener2(Message message) {
        String str = new String(message.getBody());

        System.out.println("fanoutQueue2... " + str);

        // 消费者返回响应
//        return "响应成功👌";
    }


    // topic模式
    @RabbitListener(queues = {"yyds-topic-queue"})
    void topicListener(Message message) {
        System.out.println("topic... " + new String(message.getBody()));
    }


    // header模式
    @RabbitListener(queues = {"yyds-headers-queue"})
    void headersListener(Message message) {
        System.out.println("headers... " + new String(message.getBody()));
    }

}
