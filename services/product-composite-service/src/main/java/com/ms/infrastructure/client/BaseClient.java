package com.ms.infrastructure.client;

import com.ms.infrastructure.config.AppConfigProperties;
import com.ms.infrastructure.mapper.ClientMapper;
import com.ms.domain.external.ClientProduct;
import com.ms.domain.product.model.Product;
import com.ms.enums.Source;
import com.ms.filter.ProductFilter;
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
