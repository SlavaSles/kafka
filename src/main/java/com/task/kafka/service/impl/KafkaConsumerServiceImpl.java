package com.task.kafka.service.impl;

import com.task.kafka.service.KafkaConsumerService;
import com.task.kafka.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final RestService restService;

    private final String topic = "${application.kafka.topic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    @KafkaListener(topics = topic, groupId = kafkaConsumerGroupId)
    public void listen(ConsumerRecord<String, String> record) {
        String exchangerUuid = getExchangerUuid(record.headers());
        log.info("Consumer recieve value with id {} and message {} ", exchangerUuid, record.value());
        if (exchangerUuid != null) {
            restService.receiveMessage(exchangerUuid, record.value());
        }
    }

    private String getExchangerUuid(Headers headers) {
        Header id = headers.headers("exchangerId").iterator().next();
        if (id == null) {
            return null;
        }
        return new String(id.value()); //id.value().toString();
    }
}
