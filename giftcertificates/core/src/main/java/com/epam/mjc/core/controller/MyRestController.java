package com.epam.mjc.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @GetMapping("/greet")
    public String greet() {
        return "greeting message from getMapping";
    }
}
