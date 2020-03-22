package com.example.restwithspringbootudemy;

import com.example.restwithspringbootudemy.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageConfig.class})
public class RestWithSpringbootUdemyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWithSpringbootUdemyApplication.class, args);
    }

}
