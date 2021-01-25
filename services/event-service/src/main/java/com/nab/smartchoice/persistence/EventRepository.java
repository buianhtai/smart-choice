package com.nab.smartchoice.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventRepository extends ReactiveCrudRepository<EventEntity, String> {

}
