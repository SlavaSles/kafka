package com.task.kafka.service.impl;

import com.task.kafka.service.KafkaConsumerService;
import com.task.kafka.service.RestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final RestService restService;

    private final String topic = "${application.kafka.topic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    @KafkaListener(topics = topic, groupId = kafkaConsumerGroupId) //, properties = {"spring.json.value.default.type=java.lang.String"}
    public void listen(@Payload List<String> values) {
        System.out.println("Consume values, values.size: " + values.size());
        values.forEach(System.out::println);
        restService.receiveMessage(values);
    }
}
