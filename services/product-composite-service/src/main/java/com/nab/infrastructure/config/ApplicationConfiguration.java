package com.nab.infrastructure.config;

import com.nab.event.Event;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class ApplicationConfiguration<T extends Event> {

    @Bean
    public ReactiveKafkaProducerTemplate<T, byte[]> reactiveKafkaProducerTemplate(
        KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        props.put(
            ProducerConfig.TRANSACTIONAL_ID_CONFIG,
            properties.getProducer().getTransactionIdPrefix());

        props.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            "org.springframework.kafka.support.serializer.JsonSerializer");
        props.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.StringSerializer");
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }
}
