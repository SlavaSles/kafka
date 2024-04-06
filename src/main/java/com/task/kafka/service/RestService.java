package com.task.kafka.service;

import java.util.List;

public interface RestService {

    String sendMessage(String message);

    void receiveMessage(List<String> values);
}
