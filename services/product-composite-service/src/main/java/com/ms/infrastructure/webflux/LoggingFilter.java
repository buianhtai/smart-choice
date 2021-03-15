package com.ms.infrastructure.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/***
 * Logging tracing
 */
@Component
@Slf4j
public class LoggingFilter implements WebFilter {

    private BuildProperties buildProperties;

    @Autowired
    public LoggingFilter(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(new SmartChoiceServerWebExchangeDecorator(exchange))
                .doOnSuccess((done) -> RequestLogger.info(exchange, System.currentTimeMillis(), buildProperties.getVersion()));
    }
}
