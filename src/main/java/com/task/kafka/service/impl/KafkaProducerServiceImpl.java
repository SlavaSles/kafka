package com.task.kafka.service.impl;

import com.task.kafka.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Value("${application.kafka.producer.topic:demo-topic}")
    private final String producerTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String exchangerUuid, String message, String HEADER_NAME) {
        ProducerRecord<String, String> record = new ProducerRecord<>(producerTopic, message);
        record.headers().add(new RecordHeader(HEADER_NAME, exchangerUuid.getBytes()));
        try {
            kafkaTemplate.send(record).whenComplete(
                (result, ex) -> {
                    if (ex == null) {
                        log.info("Message {} with offset = {} and id = {} was sent", message,
                            result.getRecordMetadata().offset(), exchangerUuid);
                    } else {
                        log.error("Message {} with id = {} was not sent", message, exchangerUuid);
                        System.err.println("message: " + message + " was not sent " + ex.getMessage());
                    }
                });
        } catch (Exception ex) {
            log.error("Sending error for message {} with id = {}. Error: {}", message, exchangerUuid, ex.getMessage());
        }
    }
}
