package com.nab.infrastructure.event;

import com.nab.event.Event;
import com.nab.event.IEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class EventPublisher<E extends Event> implements IEventPublisher<E> {

    private final ReactiveKafkaProducerTemplate kafkaTemplate;

    @Override
    public Mono publish(String topic, E event) {
       return kafkaTemplate.send(topic, event);
    }
}
