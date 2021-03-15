package com.ms.partner.lazada.application.api.controller;

import com.github.javafaker.Faker;
import com.ms.domain.external.LazadaProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final Faker faker;

    @GetMapping
    public Mono<LazadaProduct> product(@RequestParam String query) {
        return Mono.fromCallable(() ->
            LazadaProduct.builder()
                .code(faker.code().imei())
                .name(query)
                .description(faker.commerce().productName())
                .price(Double.parseDouble(faker.commerce().price()))
                .brand(faker.company().name())
                .category(faker.random().hex())
                .promotion(faker.commerce().promotionCode())
                .image(faker.internet().image())
                .discountRate(faker.number().randomDouble(10, 1, 100))
                .build()).subscribeOn(Schedulers.boundedElastic());
    }
}
