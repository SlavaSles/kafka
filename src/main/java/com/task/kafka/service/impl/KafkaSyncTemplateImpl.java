package com.task.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.kafka.dto.RequestDto;
import com.task.kafka.dto.ResponseDto;
import com.task.kafka.service.KafkaProducerService;
import com.task.kafka.service.KafkaSyncTemplate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
public class KafkaSyncTemplateImpl implements KafkaSyncTemplate {

    private final Map<String, Exchanger<Object>> exchangerMap = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    private final KafkaProducerService kafkaProducerService;

    private final String consumerTopic = "${application.kafka.consumer.topic:demo-topic}";

    private final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id:consumer-group1}";

    private final String HEADER_NAME = "exchangerId";

    public ResponseDto kafkaExchange(RequestDto requestDto) {
        Exchanger<Object> exchanger = new Exchanger<>();
        String exchangerUuid = UUID.randomUUID().toString();
        exchangerMap.put(exchangerUuid, exchanger);
        Object answer;
        try {
            kafkaProducerService.sendMessage(exchangerUuid, objectMapper.writeValueAsString(requestDto),
                HEADER_NAME);
            answer = exchanger.exchange(null, 5_000, TimeUnit.MILLISECONDS);
            exchangerMap.remove(exchangerUuid);
        } catch (InterruptedException | TimeoutException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info("RestService successfully convert message to ResponseDto = {}", ((ResponseDto) answer).message());
        return (ResponseDto) answer;
    }

    @KafkaListener(topics = consumerTopic, groupId = kafkaConsumerGroupId)
    private void receiveMessage(ConsumerRecord<String, String> record) {
        String exchangerUuid = getExchangerUuid(record.headers());
        log.info("Consumer receive value with id {} and message {} ", exchangerUuid, record.value());
        if (exchangerUuid != null) {
            exchangeMessage(exchangerUuid, record.value());
        }
    }

    private String getExchangerUuid(Headers headers) {
        Header id = headers.headers(HEADER_NAME).iterator().next();
        if (id == null) {
            return null;
        }
        return new String(id.value()); //id.value().toString();
    }

    private void exchangeMessage(String exchangerUuid, String message) {
        if (exchangerMap.containsKey(exchangerUuid)) {
            log.info("Returning response message = {}", message);
            try {
                RequestDto requestDto = objectMapper.readValue(message, RequestDto.class);
                ResponseDto responseDto = new ResponseDto(requestDto.getMessage());
                Exchanger<Object> exchanger = exchangerMap.get(exchangerUuid);
                exchanger.exchange(responseDto, 5_000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | TimeoutException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
