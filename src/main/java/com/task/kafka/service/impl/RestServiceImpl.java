package com.task.kafka.service.impl;

import com.task.kafka.service.KafkaProducerService;
import com.task.kafka.service.RestService;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {

    Map<String, Exchanger<String>> exchangerMap = new ConcurrentHashMap<>();

    private final KafkaProducerService kafkaProducerService;

    public String sendMessage(String message) {
        Exchanger<String> exchanger = new Exchanger<>();
        String exchangerUuid = UUID.randomUUID().toString();
        exchangerMap.put(exchangerUuid, exchanger);
        kafkaProducerService.send(exchangerUuid, message);
        String answer;
        try {
            answer = exchanger.exchange("", 5_000, TimeUnit.MILLISECONDS);
            exchangerMap.remove(exchangerUuid);
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    public void receiveMessage(String exchangerUuid, String message) {
        if (exchangerMap.containsKey(exchangerUuid)) {
            System.out.println("RestService successfully receive message: " + message);
            try {
                Exchanger<String> exchanger = exchangerMap.get(exchangerUuid);
                exchanger.exchange(message, 5_000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
