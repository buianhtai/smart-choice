package com.nab.product.integration.client;

import com.nab.domain.ShoppeProduct;
import com.nab.domain.Source;
import com.nab.product.integration.AppConfigProperties;
import com.nab.product.integration.mapper.ClientMapper;
import com.nab.product.integration.mapper.ShopeeMapper;
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
