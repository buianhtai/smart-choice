package com.ms.infrastructure.client;

import com.ms.infrastructure.config.AppConfigProperties;
import com.ms.infrastructure.mapper.ClientMapper;
import com.ms.infrastructure.mapper.ShopeeMapper;
import com.ms.domain.external.ShoppeProduct;
import com.ms.enums.Source;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ShopeeClient extends BaseClient<ShoppeProduct> {

    private final ShopeeMapper mapper;

    public ShopeeClient(WebClient webClient,
        AppConfigProperties configProperties,
        ShopeeMapper mapper) {
        super(webClient, configProperties);
        this.mapper = mapper;
    }


    @Override
    public Source getSource() {
        return Source.SHOPEE;
    }

    @Override
    public Class<ShoppeProduct> getTarget() {
        return ShoppeProduct.class;
    }


    @Override
    public ClientMapper<ShoppeProduct> getMapper() {
        return mapper;
    }

}
