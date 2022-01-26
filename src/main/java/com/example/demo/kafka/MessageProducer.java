package com.example.demo.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, Greeting> greetingKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${message.topic.name}")
    private String topicName;

    public void sendMessage(Greeting greeting) {
        greetingKafkaTemplate.send(topicName, greeting);
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topicName, message);
    }

}
