package com.task.kafka.controller;

import com.task.kafka.service.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private Integer messageNumber = 0;

    private final RestService restService;

    @GetMapping("/kafka")
    public ResponseEntity<?> publishMessage(@RequestParam(value = "msg", required = false, defaultValue = "test")
                                                 String msg) {
        messageNumber++;
        String testMessage = msg + " " + messageNumber;
        String response = restService.sendMessage(testMessage);
        return ResponseEntity.ok(response);
    }
}
