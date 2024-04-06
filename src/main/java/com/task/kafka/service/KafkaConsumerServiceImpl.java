package com.task.kafka.service;

import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl {

    private final String topic = "${application.kafka.topic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    @KafkaListener(topics = topic, groupId = kafkaConsumerGroupId) //, properties = {"spring.json.value.default.type=java.lang.String"}
    public void listen(@Payload List<String> values) {
        System.out.println("Consume values, values.size: " + values.size());
        values.forEach(System.out::println);
//        return values;
    }
}
