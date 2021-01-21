package com.ecom.integration.client;

import com.ecom.integration.AppConfigProperties;
import com.ecom.integration.mapper.ClientMapper;
import com.ecom.integration.mapper.ShopeeMapper;
import com.nab.domain.ShoppeProduct;
import com.nab.domain.Source;
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
