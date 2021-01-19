package com.nab.smartchoice.util.product;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }


    @Bean
    public Consumer<AuditInfo> eventHandler() {
        return (i) -> {
            log.info("eventHandler Received : " + i);
        };
    }
}
