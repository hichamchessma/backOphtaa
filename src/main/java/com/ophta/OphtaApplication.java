package com.ophta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.ophta")
public class OphtaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OphtaApplication.class, args);
    }
}
