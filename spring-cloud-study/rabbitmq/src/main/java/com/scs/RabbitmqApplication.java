package com.scs;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
        SpringApplication.run(RabbitmqApplication.class);

        ConnectionFactory factory = new ConnectionFactory();
//
//        factory.setHost("192.168.180.100");
//        factory.setPort(5672);
//        factory.setUsername("admin");
//        factory.setPassword("admin");
//        factory.setVirtualHost("/test");

//
//        try (Connection connection = factory.newConnection();
//             Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare()
//
//            // 声明队列，如果不存在则创建
//            channel.queueDeclare("yyds", true, false, false, null);
//
//            // 绑定交换机
//            channel.queueBind("yyds", "amq.direct", "my-yyds");
//
//            // 发布消息
//            channel.basicPublish("amq.direct", "my-yyds", null, "heeelllloooo world".getBytes());
//        } catch (IOException | TimeoutException e) {
//            throw new RuntimeException(e);
//        }


//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        // 消费者，关闭自动应答
//        channel.basicConsume("yyds", false,
//                (consumerTag, message) -> {
//                    System.out.println("consumerTag = " + consumerTag);
//                    System.out.println("msg = " + new String(message.getBody()));
//
//                    // 手动答复
//                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
//                }, (consumerTag) -> {
//                    System.out.println("cancelCallback consumerTag = " + consumerTag);
//                });


    }
}
