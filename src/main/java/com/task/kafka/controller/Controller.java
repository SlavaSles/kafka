package com.task.kafka.controller;

import com.task.kafka.dto.RequestDto;
import com.task.kafka.dto.ResponseDto;
import com.task.kafka.service.KafkaSyncTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для отправки синхронных запросов в Кафку и получения ответов из нее.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    private Integer messageNumber = 0;

    private final KafkaSyncTemplate<RequestDto, ResponseDto> kafkaSyncTemplate;

    /**
     * Метод публикует сообщение в топике Кафки, считывает его обратно и возвращает пользователю.
     *
     * @param requestDto тело запроса с полем message в {@link RequestDto}.
     * @return возвращает прочитанное из Кафки сообщение {@link ResponseDto}.
     */
    @PostMapping("/kafka")
    public ResponseEntity<?> publishMessage(@RequestBody(required = false) RequestDto requestDto) {
        messageNumber++;
        if (requestDto == null) {
            requestDto = new RequestDto();
            requestDto.setMessage("тестовое сообщение");
        }
        log.info("Post message for publishing to Kafka with text = {}", requestDto.getMessage());
        String message = requestDto.getMessage().concat(" ").concat(messageNumber.toString());
        requestDto.setMessage(message);
        ResponseDto responseDto = new ResponseDto("");
        responseDto = kafkaSyncTemplate.kafkaExchange(requestDto, responseDto);
        return ResponseEntity.ok(responseDto);
    }
}
