package com.task.kafka.service.impl;

import com.task.kafka.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Value("${application.kafka.topic}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String exchangerUuid, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, message);
        record.headers().add(new RecordHeader("exchangerId", exchangerUuid.getBytes()));
        try {
            kafkaTemplate.send(record).whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        System.out.println("message: " + message + " was sent, offset: " +
                            result.getRecordMetadata().offset() + ".");
                    } else {
                        System.err.println("message: " + message + " was not sent " + ex.getMessage());
                    }
                });
        } catch (Exception ex) {
            System.err.println("send error, value: " + message + " " + ex.getMessage());
        }
    }
}
