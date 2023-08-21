package com.example.booklendapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class BookLendAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookLendAppApplication.class, args);
    }
    @Bean
    public Validator validator(){
        return new LocalValidatorFactoryBean();
    }

}
