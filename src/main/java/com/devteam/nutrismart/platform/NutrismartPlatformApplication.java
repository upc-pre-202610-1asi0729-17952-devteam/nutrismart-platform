package com.devteam.nutrismart.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NutrismartPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(NutrismartPlatformApplication.class, args);
    }

}
