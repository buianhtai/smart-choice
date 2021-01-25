package com.nab.infrastructure.client;

import com.nab.infrastructure.config.AppConfigProperties;
import com.nab.domain.external.LazadaProduct;
import com.nab.enums.Source;
import com.nab.infrastructure.mapper.ClientMapper;
import com.nab.infrastructure.mapper.LazadaMapper;
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

