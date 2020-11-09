package com.epam.mjc.core.controller;

import com.epam.mjc.api.util.SearchParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam(required = true, name = "map") Map<SearchParams, Integer> map) {
        System.out.println(map);
        return "success";
    }
}
