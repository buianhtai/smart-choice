package com.ms.partner.tiki;

import com.github.javafaker.Faker;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
    scanBasePackages = {"com.ms"}
)
@Slf4j
public class TikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TikiApplication.class, args);
    }

    @Bean
    Faker faker(){
       return new Faker(new Locale("vi"));
    }
}
