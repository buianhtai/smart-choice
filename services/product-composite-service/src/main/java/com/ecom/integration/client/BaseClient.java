package com.ecom.integration.client;

import com.ecom.integration.AppConfigProperties;
import com.ecom.integration.mapper.ClientMapper;
import com.nab.domain.ClientProduct;
import com.nab.domain.Product;
import com.nab.domain.SearchRequest;
import com.nab.domain.Source;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseClient<T extends ClientProduct> implements PartnerClient {

    private final WebClient webClient;
    private final AppConfigProperties configProperties;

    @Override
    public boolean isSupport(Source source) {
        return Source.LAZADA.equals(source);
    }

    @Override
    public Flux<Product> execute(
        HttpHeaders headers,
        SearchRequest request) {

        String targetUrl = getTargetUrl() + "?query=" + request.getQuery();
        log.info("targetUrl {}",targetUrl);
        return webClient.get().uri(targetUrl)
            .retrieve()
            .bodyToFlux(getTarget())
            .map(e -> getMapper().to(e))
            .map(product -> {
                product.setSource(getSource());
                return product;
            })
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
