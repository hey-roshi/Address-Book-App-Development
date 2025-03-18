package com.development.Address.Book.App.controller;
import com.development.Address.Book.App.producer.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressBookController {

    @Autowired
    private MessageSender messageSender;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam String message) {
        messageSender.sendMessage(message);
        return "Message sent to RabbitMQ!";
    }
}