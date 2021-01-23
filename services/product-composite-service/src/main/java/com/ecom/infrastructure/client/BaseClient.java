package com.ecom.infrastructure.client;

import com.ecom.infrastructure.AppConfigProperties;
import com.ecom.infrastructure.mapper.ClientMapper;
import com.nab.domain.external.ClientProduct;
import com.nab.domain.product.model.Product;
import com.nab.enums.Source;
import com.nab.filter.ProductFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseClient<T extends ClientProduct> implements PartnerClient {

    private final WebClient webClient;
    private final AppConfigProperties configProperties;

    @Override
    public Flux<Product> execute(
        ProductFilter request) {

        String targetUrl = getTargetUrl() + "?query=" + request.getQuery();
        log.info("targetUrl {}", targetUrl);

        return webClient.get().uri(targetUrl)
            .retrieve()
            .bodyToFlux(getTarget())
            .map(e -> getMapper().to(e))
            .timeout(configProperties.getRetry().getMaxDuration())
            .retry(configProperties.getRetry().getMaxTimes())
            .log();
    }

    public abstract Source getSource();


    public abstract Class<T> getTarget();


    public abstract ClientMapper<T> getMapper();


    public String getTargetUrl() {
        return configProperties.getClients().get(getSource().name());
    }

}
