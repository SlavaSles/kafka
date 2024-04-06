package com.task.kafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl {

    @Value("${application.kafka.topic}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        try {
            kafkaTemplate.send(topicName, message).whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        System.out.println("message: " + message + " was sent, offset: " +
                            result.getRecordMetadata().offset() + ".");
                    } else {
                        System.err.println("message: " + message + " was not sent " + ex.getMessage());
                    }
                });
        } catch (Exception ex) {
            System.err.println("send error, value: " + message + " " + ex.getMessage());
        }
    }
}
