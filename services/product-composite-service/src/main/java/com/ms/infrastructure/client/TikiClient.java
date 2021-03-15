package com.ms.infrastructure.client;

import com.ms.enums.Source;
import com.ms.domain.external.TikiProduct;
import com.ms.infrastructure.config.AppConfigProperties;
import com.ms.infrastructure.mapper.ClientMapper;
import com.ms.infrastructure.mapper.TikiProductMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TikiClient extends BaseClient<TikiProduct> {

    private final TikiProductMapper mapper;

    public TikiClient(WebClient webClient,
        AppConfigProperties configProperties,
        TikiProductMapper mapper) {
        super(webClient, configProperties);
        this.mapper = mapper;
    }


    @Override
    public Source getSource() {
        return Source.TIKI;
    }

    @Override
    public Class<TikiProduct> getTarget() {
        return TikiProduct.class;
    }

    @Override
    public ClientMapper<TikiProduct> getMapper() {
        return mapper;
    }

}
