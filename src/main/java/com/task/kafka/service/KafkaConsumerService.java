package com.task.kafka.service;

import java.util.List;
import org.springframework.messaging.handler.annotation.Payload;

public interface KafkaConsumerService {

    void listen(@Payload List<String> values);
}
