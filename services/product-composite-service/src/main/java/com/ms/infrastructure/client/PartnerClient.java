package com.ms.infrastructure.client;

import com.ms.domain.product.model.Product;
import com.ms.filter.ProductFilter;
import reactor.core.publisher.Flux;


public interface PartnerClient {

    Flux<Product> execute(ProductFilter filter);

}

