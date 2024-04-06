package com.task.kafka.service.impl;

import com.task.kafka.service.KafkaProducerService;
import com.task.kafka.service.RestService;
import java.util.List;
import java.util.concurrent.Exchanger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {

    Exchanger<String> exchanger = new Exchanger<>();

    private final KafkaProducerService kafkaProducerService;

    public String sendMessage(String message) {
        kafkaProducerService.send(message);
        String answer;
        try {
            answer = exchanger.exchange("");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    public void receiveMessage(List<String> values) {
//        System.out.println("RestService успешно получил сообщение: " + values.get(0));
        try {
            exchanger.exchange(values.get(0));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
