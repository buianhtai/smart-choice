package com.ms.event;

import reactor.core.publisher.Mono;

public interface IEventPublisher<E extends Event> {

    Mono<Object> publish(String topic, E event);


}
