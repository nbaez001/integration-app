package com.empresa.integration.config;

import com.empresa.integration.model.Address;
import com.empresa.integration.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.integration.router.PayloadTypeRouter;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.MessageChannel;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableIntegration
@IntegrationComponentScan("com.empresa.integration")
public class IntegrationConfig {

    @Bean
    public MessageChannel recieverChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel replyChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "router.channel")
    @Bean
    public PayloadTypeRouter router() {
        PayloadTypeRouter router = new PayloadTypeRouter();
        router.setChannelMapping(Student.class.getName(), "router.channel.student");
        router.setChannelMapping(Address.class.getName(), "router.channel.address");
        return router;
    }

    @Bean
    @Transformer(inputChannel = "router.channel.student",
            outputChannel = "router.channel.header")
    public HeaderEnricher enrichHeaderForStudent() {
        Map<String, HeaderValueMessageProcessor<String>> headersToAdd = new HashMap<>();
        headersToAdd.put("testHeader", new StaticHeaderValueMessageProcessor<>("student"));
        return new HeaderEnricher(headersToAdd);
    }

    @Bean
    @Transformer(inputChannel = "router.channel.address",
            outputChannel = "router.channel.header")
    public HeaderEnricher enrichHeaderForAddress() {
        Map<String, HeaderValueMessageProcessor<String>> headersToAdd = new HashMap<>();
        headersToAdd.put("testHeader", new StaticHeaderValueMessageProcessor<>("address"));
        return new HeaderEnricher(headersToAdd);
    }

    @ServiceActivator(inputChannel = "router.channel.header")
    @Bean
    public HeaderValueRouter headerRouter() {
        HeaderValueRouter router = new HeaderValueRouter("testHeader");
        router.setChannelMapping("student", "channel.student");
        router.setChannelMapping("address", "channel.address");
        return router;
    }
}
