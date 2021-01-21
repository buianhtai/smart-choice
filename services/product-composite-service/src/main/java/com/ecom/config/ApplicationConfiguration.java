package com.ecom.config;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ReactiveKafkaProducerTemplate<String, byte[]> reactiveKafkaProducerTemplate(
        KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        props.put(
            ProducerConfig.TRANSACTIONAL_ID_CONFIG, properties.getProducer().getTransactionIdPrefix());
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }
}
