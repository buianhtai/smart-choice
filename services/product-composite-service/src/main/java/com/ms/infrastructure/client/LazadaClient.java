package com.ms.infrastructure.client;

import com.ms.infrastructure.config.AppConfigProperties;
import com.ms.domain.external.LazadaProduct;
import com.ms.enums.Source;
import com.ms.infrastructure.mapper.ClientMapper;
import com.ms.infrastructure.mapper.LazadaMapper;
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

