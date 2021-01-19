package com.nab.ecom.external.application.api.controller;

import com.nab.ecom.external.domain.AuditInfo;
import com.nab.ecom.external.domain.Product;
import com.nab.ecom.external.domain.SearchRequest;
import com.nab.ecom.external.domain.Sort;
import com.nab.ecom.external.domain.TikiProduct;
import com.nab.ecom.external.service.PartnerClient;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import se.magnus.util.exceptions.NotFoundException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final List<PartnerClient> clients;
    private final KafkaTemplate kafkaTemplate;

    @GetMapping
    public Flux<TikiProduct> product(@RequestParam String query) {
        return Flux.fromIterable(Collections.singletonList(
            TikiProduct.builder().code("code -" + UUID.randomUUID().toString())
                .name("name -" + query).price(new Random().nextDouble() * 1000).build()));
    }


    @GetMapping(value = "/integration",produces = {MediaType.APPLICATION_NDJSON_VALUE})
    public Flux<Product> productIntegration(SearchRequest request) {
        return Flux.fromStream(clients::parallelStream)
            .flatMap(
                client ->
                    client.execute(request).subscribeOn(Schedulers.boundedElastic())
                        .onErrorResume(throwable -> Flux.empty())
            )
            .sort(sortOrder(request.getSort()))
            .doOnComplete(() -> {
                    log.info("doOnComplete ");
                    kafkaTemplate
                        .send("audit",
                            AuditInfo.builder().userName("admin").query(request.getQuery()).build())
                        .addCallback((e) -> log.info("Success {}", e),
                            (ex) -> log.info(ex.getMessage()));
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
