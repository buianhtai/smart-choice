package com.ecom.controller;


import com.ecom.config.Constants;
import com.ecom.config.security.Permissions;
import com.ecom.config.security.UserPrincipal;
import com.nab.domain.product.model.ProductAggregate;
import com.nab.domain.product.repository.IProductAggregateRepository;
import com.nab.event.SearchEvent;
import com.nab.filter.ProductFilter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Constants.PRODUCT_URL)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ReactiveKafkaProducerTemplate kafkaTemplate;

    private final IProductAggregateRepository repository;

    @Value("${app.events.topic}")
    private String eventTopic;

    @GetMapping
    public Mono<ProductAggregate> getProducts(ProductFilter request) {
        return
            ReactiveSecurityContextHolder.getContext().map(e->e.getAuthentication().getName())
                .zipWith(
                    repository.getAggregate(request),
                    (a, p) -> {
                        log.info("Authentication: UserName {} ", a);

                        SearchEvent event = SearchEvent.builder()
                            .eventId(UUID.randomUUID())
                            .userName(a)
                            .query(request.getQuery())
                            .build();

                        log.debug("Send event search to kafka topic {} - Event {} ", eventTopic,
                            event);

                        kafkaTemplate.send(eventTopic, event)
                            .subscribe();
                        return p;
                    });
    }

    @GetMapping("/current-user")
    public Mono<Authentication> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication);
    }

}
