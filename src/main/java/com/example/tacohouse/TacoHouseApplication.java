package com.example.tacohouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan + @SpringBootApplication
//конфигурация + автоконфигурация + сканирование компонентов на аннотации + спрингприложение
public class TacoHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoHouseApplication.class, args);
    }

}
