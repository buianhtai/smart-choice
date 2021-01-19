package com.nab.product.integration.client;

import com.nab.domain.ClientProduct;
import com.nab.domain.Product;
import com.nab.domain.SearchRequest;
import com.nab.domain.Source;
import com.nab.product.integration.AppConfigProperties;
import com.nab.product.integration.mapper.ClientMapper;
import java.time.Duration;
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
            .timeout(Duration.ofSeconds(1))
            .log();
    }

    public abstract Source getSource();


    public abstract Class<T> getTarget();


    public abstract ClientMapper<T> getMapper();


    public String getTargetUrl() {
        return configProperties.getClients().get(getSource().name());
    }

}
