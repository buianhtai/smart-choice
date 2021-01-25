package com.nab.infrastructure.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import static reactor.core.scheduler.Schedulers.single;

@Slf4j
public class SmartChoiceServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private Flux<DataBuffer> body;

    public SmartChoiceServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
        final MediaType contentType = delegate.getHeaders().getContentType();
        Flux<DataBuffer> flux = super.getBody();
        if (LogUtils.legalLogMediaTypes.contains(contentType)) {
            body = flux.publishOn(single())
                    .map(dataBuffer -> LogUtils.loggingRequest(log, dataBuffer));
        } else {
            body = flux;
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return body;
    }
}
