package com.isa.spring.web.resttemplate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/get")
    public ResponseEntity get() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get400")
    public ResponseEntity get400() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get404")
    public ResponseEntity get404() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getTimeout")
    public ResponseEntity getTimeout() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Error occurred", e);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/post")
    public ResponseEntity post() {
        return ResponseEntity.ok().build();
    }
}
