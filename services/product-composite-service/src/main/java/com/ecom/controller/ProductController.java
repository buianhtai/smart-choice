package com.ecom.controller;


import com.ecom.integration.client.PartnerClient;
import com.nab.domain.AuditInfo;
import com.nab.domain.Product;
import com.nab.domain.SearchRequest;
import com.nab.domain.Sort;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final List<PartnerClient> clients;
    private final ReactiveKafkaProducerTemplate kafkaTemplate;

    @Value("${app.events.topic}")
    private String eventTopic;

    @GetMapping
    public Flux<Product> getProducts(SearchRequest request) {
        return Flux.fromIterable(clients)
            .flatMap(
                client -> client
                    .execute(null, request)
                    .onErrorResume(throwable -> Flux.empty())
                    .subscribeOn(Schedulers.boundedElastic())
            )
            .sort(sortOrder(request.getSort()))
            .doOnComplete(
                () -> {
                    AuditInfo event = AuditInfo.builder().userName("admin")
                        .query(request.getQuery())
                        .build();
                    log.debug("Send event search to kafka topic {} - Event {} ", eventTopic, event);

                    kafkaTemplate.send(eventTopic, event)
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe();
                }
            );
    }


    Comparator<Product> sortByPrice() {
        return Comparator.comparing(Product::getPrice);
    }

    Comparator<Product> sortOrder(Sort sort) {
        if (sort == null) {
            return sortByPrice();
        }
        log.debug("Sort received {}", sort);
        switch (sort) {
            case DECS:
                return sortByPrice().reversed();
            default:
                return sortByPrice();
        }

    }
}
