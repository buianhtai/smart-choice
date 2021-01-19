package com.nab.ecom.external.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import com.nab.ecom.external.domain.Product;
import com.nab.ecom.external.domain.Source;
import com.nab.ecom.external.domain.TikiProduct;
import com.nab.ecom.external.domain.SearchRequest;
import com.nab.ecom.external.infrastructure.mapper.TikiProductMapper;
import com.nab.ecom.external.service.PartnerClient;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.handler.advice.RequestHandlerCircuitBreakerAdvice.CircuitBreakerOpenException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.exceptions.NotFoundException;
import se.magnus.util.http.HttpErrorInfo;

@RequiredArgsConstructor
@Component
@Slf4j
public class LazadaConfiguration implements PartnerClient {

    private final ObjectMapper mapper;
    private final WebClient.Builder webClientBuilder;
    private final TikiProductMapper productMapper;
    private WebClient webClient;

    @Value("${app.product-service.timeoutSec}")
    private int productServiceTimeoutSec;


    private WebClient getWebClient() {
        if (webClient == null) {
            webClient = webClientBuilder.build();
        }
        return webClient;
    }

    @Override
    public boolean isSupport(Source source) {
        return Source.LAZADA.equals(source);
    }

    @Retry(name = "product")
    @CircuitBreaker(name = "product")
    @Override
    public Flux<Product> execute(
        SearchRequest request) {

        String tikiProductUrl = "http://localhost:8989/api/v1/products?query=" + request.getQuery();

        return getWebClient().get().uri(tikiProductUrl).retrieve()
            .bodyToFlux(TikiProduct.class).map(productMapper::to).map(product -> {
                product.setSource(Source.LAZADA);
                return product;
            }).log()
            .onErrorReturn(CircuitBreakerOpenException.class,
            getProductFallbackValue())
            .delayElements(Duration.ofMillis(2000));

    }

    private Throwable handleException(Throwable ex) {

        if (!(ex instanceof WebClientResponseException)) {
            log.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
            return ex;
        }

        WebClientResponseException wcre = (WebClientResponseException) ex;

        switch (wcre.getStatusCode()) {

            case NOT_FOUND:
                return new NotFoundException(getErrorMessage(wcre));

            case UNPROCESSABLE_ENTITY:
                return new InvalidInputException(getErrorMessage(wcre));

            default:
                log.warn("Got a unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
                log.warn("Error body: {}", wcre.getResponseBodyAsString());
                return ex;
        }
    }

    private Product getProductFallbackValue() {
        return new Product("","", BigDecimal.ZERO,Source.TIKI);
    }


    private String getErrorMessage(WebClientResponseException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }
}
