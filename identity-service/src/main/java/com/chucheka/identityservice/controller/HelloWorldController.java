package com.chucheka.identityservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HelloWorldController {

    @Value("${app.secret}")
    private String secret;


    @GetMapping("/hello")
    public String helloworld(@RequestHeader(value = "correlation-id", required = false) String correlationId) {

        System.out.println("***** THE SECRET " + secret);
        log.info("THE RECEIVED CORRELATION ID {}", correlationId);
        return "Hello World";
    }
}
