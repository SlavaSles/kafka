package com.task.kafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestServiceImpl {

    private final KafkaProducerServiceImpl kafkaProducerService;

    public void sendMessage(String message) {
        kafkaProducerService.send(message);
    }

    public String receiveMessage() {

        return "";
    }
}
