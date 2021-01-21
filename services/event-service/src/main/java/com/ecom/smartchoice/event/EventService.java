package com.ecom.smartchoice.event;

import com.ecom.smartchoice.persistence.EventEntity;
import com.ecom.smartchoice.persistence.EventRepository;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventService<T> {

    private final EventRepository repository;

    @Bean
    public Consumer<T> eventHandler() {
        return (i) -> {
            log.info("Receive Event : " + i);
            repository
                .save(EventEntity.builder().event(i).build())
                .map(EventEntity::getId)
                .subscribe(e->log.info("Document ID {}",e));
        };
    }
}
