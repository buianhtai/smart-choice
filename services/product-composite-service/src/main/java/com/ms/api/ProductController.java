package com.ms.api;


import static com.ms.constant.ApiConstants.PRODUCT_API;

import com.ms.domain.product.model.ProductAggregate;
import com.ms.domain.product.repository.IProductAggregateRepository;
import com.ms.event.IEventPublisher;
import com.ms.event.SearchEvent;
import com.ms.filter.ProductFilter;
import com.ms.http.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(PRODUCT_API)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final IEventPublisher<SearchEvent> publisher;
    private final IProductAggregateRepository repository;

    @Value("${app.events.topic}")
    private String eventTopic;

    @GetMapping
    public Mono<BaseResponse<ProductAggregate>> getProducts(ProductFilter request) {
        return
            ReactiveSecurityContextHolder.getContext().map(e -> e.getAuthentication().getName())
                .zipWith(
                    repository.getAggregate(request),
                    (a, p) -> {
                        log.info("Authentication: UserName {} ", a);

                        SearchEvent event = SearchEvent.builder()
                            .userName(a)
                            .query(request.getQuery())
                            .build();

                        log.debug("Send event search to kafka topic {} - Event {} ", eventTopic,
                            event);

                        publisher.publish(eventTopic, event).subscribe();

                        return BaseResponse.ofSucceeded(p);
                    });
    }

    @GetMapping("/current-user")
    public Mono<Authentication> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication);
    }

}
