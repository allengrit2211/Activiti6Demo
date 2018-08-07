package com.activiti6.demo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class Activiti6DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Activiti6DemoApplication.class, args);
    }
}
