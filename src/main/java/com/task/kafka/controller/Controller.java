package com.task.kafka.controller;

import com.task.kafka.service.RestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private Integer messageNumber = 0;

    private final RestServiceImpl restService;

    @GetMapping("/kafka")
    public ResponseEntity<?> publishMessage() {
        messageNumber++;
        String testMessage = "test " + messageNumber;
        restService.sendMessage(testMessage);
        String response = restService.receiveMessage();
        return ResponseEntity.ok(response);
    }
}
