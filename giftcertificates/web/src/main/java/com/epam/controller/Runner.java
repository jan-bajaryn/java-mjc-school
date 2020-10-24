package com.epam.controller;

import com.epam.config.AppConfig;
import com.epam.service.MyService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);
        MyService myService =context.getBean(MyService.class);

        myService.somePrint();
    }
}
