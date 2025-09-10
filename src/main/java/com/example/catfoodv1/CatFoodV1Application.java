package com.example.catfoodv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CatFoodV1Application {

    public static void main(String[] args) {
        SpringApplication.run(CatFoodV1Application.class, args);
    }

}
