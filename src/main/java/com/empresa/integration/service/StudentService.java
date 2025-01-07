package com.empresa.integration.service;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

@MessageEndpoint
public class StudentService {

    @ServiceActivator(inputChannel = "channel.student")
    public void recieveMessage(Message<?> message) throws MessagingException {
        System.out.println("##########channel.student##########");
        System.out.println(message);
        System.out.println(message.getPayload());
    }
}
