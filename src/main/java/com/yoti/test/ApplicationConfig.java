package com.yoti.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yoti.test"})
@EnableAutoConfiguration
@PropertySource("classpath:application.yml")
public class ApplicationConfig {
	
    public static void main(String[] args) {
    	SpringApplication.run(ApplicationConfig.class, args);
    }
}
