package com.task.kafka.service;

public interface KafkaProducerService {

    void send(String exchangerUuid, String message);
}
