package com.liuwu;

import com.liuwu.config.SpringDevConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDevConfig.class, args);
    }
}
