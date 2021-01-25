package com.nab.smartchoice.event;

import com.nab.event.Event;
import com.nab.event.IEventConsumer;
import com.nab.event.IEventHandler;
import com.nab.smartchoice.persistence.EventEntity;
import com.nab.smartchoice.persistence.EventRepository;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/***
 * EvenConsumer class
 * @param <E> the given class that extend from Event .
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EventConsumer<E extends Event> implements IEventConsumer {

    private final List<IEventHandler<E>> handlers;

    private final EventRepository repository;

    /***
     * Consume event and handler it event base on their class.
     * @return Consumer bean.
     */
    @Bean
    public Consumer<E> consumeEvent() {
        return (e) -> {
            log.info("EventConsumer Event : {} class {}", e, e.getClass());

            repository
                .save(EventEntity.builder().event(e).build())
                .map(EventEntity::getId)
                .subscribe(d -> log.info("Document ID {}", d));

            handlers.stream()
                .filter(handler -> handler.isSupport(e.getClass()))
                .findFirst()
                .ifPresent(handler -> handler.handle(e));

        };
    }
}
