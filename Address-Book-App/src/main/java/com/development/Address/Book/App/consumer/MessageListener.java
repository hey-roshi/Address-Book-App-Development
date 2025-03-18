package com.development.Address.Book.App.consumer;
import com.development.Address.Book.App.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class MessageListener {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void receiveMessage(String message) {
        System.out.println("Received Message: " + message);
    }
}

