package com.task.kafka.service;

public interface RestService {

    String sendMessage(String message);

    void receiveMessage(String exchangerUuid, String message);
}
