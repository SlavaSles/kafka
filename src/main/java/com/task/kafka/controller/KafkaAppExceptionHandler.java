package com.task.kafka.controller;

import com.task.kafka.dto.ErrorDto;
import java.util.concurrent.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class KafkaAppExceptionHandler {

    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    @ExceptionHandler(TimeoutException.class)
    public ErrorDto handleSendMessageException(Exception exception) {
        return new ErrorDto(HttpStatus.GATEWAY_TIMEOUT.value(),
            HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase());
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ErrorDto handleOtherException(Exception exception) {
//        return new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//    }
}
