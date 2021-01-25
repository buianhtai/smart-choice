package com.nab.infrastructure.client;

import com.nab.infrastructure.config.AppConfigProperties;
import com.nab.infrastructure.mapper.ClientMapper;
import com.nab.infrastructure.mapper.ShopeeMapper;
import com.nab.domain.external.ShoppeProduct;
import com.nab.enums.Source;
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
