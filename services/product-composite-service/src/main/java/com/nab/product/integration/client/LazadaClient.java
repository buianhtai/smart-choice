package com.nab.product.integration.client;

import com.nab.domain.LazadaProduct;
import com.nab.domain.Source;
import com.nab.product.integration.AppConfigProperties;
import com.nab.product.integration.mapper.ClientMapper;
import com.nab.product.integration.mapper.LazadaMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LazadaClient extends BaseClient<LazadaProduct> {

    private final LazadaMapper mapper;

    public LazadaClient(WebClient webClient,
        AppConfigProperties configProperties,
        LazadaMapper mapper) {
        super(webClient, configProperties);
        this.mapper = mapper;
    }


    @Override
    public Source getSource() {
        return Source.LAZADA;
    }

    @Override
    public Class<LazadaProduct> getTarget() {
        return LazadaProduct.class;
    }


    @Override
    public ClientMapper<LazadaProduct> getMapper() {
        return mapper;
    }
}

