package com.example.demo.kafka;

import com.example.demo.service.UserReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    @Autowired
    private UserReportService userReportService;

    @KafkaListener(topics = {"${message.topic.name}"}, groupId = "greeting")
    public void greetingListener(String greeting) {
        log.info("received message {}", greeting);
    }

    @KafkaListener(topics = {"${message.topic.name}"}, groupId = "json")
    public void jsonListener(String message) {
        log.info("received message {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserReport userReport = objectMapper.readValue(message, UserReport.class);
            log.info("user report {}", userReport);
            userReportService.generateReport(userReport);
        } catch (JsonProcessingException e) {
            log.error("fail to parse json", e);
        }
    }


}
