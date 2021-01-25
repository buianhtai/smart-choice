package com.nab.infrastructure.webflux;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public class SmartChoiceServerWebExchangeDecorator extends ServerWebExchangeDecorator {

    private SmartChoiceServerHttpRequestDecorator requestDecorator;

    private SmartChoiceServerHttpResponseDecorator responseDecorator;

    public SmartChoiceServerWebExchangeDecorator(ServerWebExchange delegate) {
        super(delegate);
        requestDecorator = new SmartChoiceServerHttpRequestDecorator(delegate.getRequest());
        responseDecorator = new SmartChoiceServerHttpResponseDecorator(delegate.getResponse());
    }

    @Override
    public ServerHttpRequest getRequest() {
        return requestDecorator;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return responseDecorator;
    }
}
