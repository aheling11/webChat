package com.webchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com", "idworker"})
public class WebchatApplication {

    @Bean
    public SpringUtil getSpringUtil() {
        return new SpringUtil();
    }
    public static void main(String[] args) {
        SpringApplication.run(WebchatApplication.class, args);
    }

}
