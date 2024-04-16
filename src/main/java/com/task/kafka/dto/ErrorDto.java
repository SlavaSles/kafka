package com.task.kafka.dto;

public record ErrorDto (

    int errorCode,

    String errorMessage
) {
}
