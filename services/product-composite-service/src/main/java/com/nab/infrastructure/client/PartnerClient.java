package com.nab.infrastructure.client;

import com.nab.domain.product.model.Product;
import com.nab.filter.ProductFilter;
import reactor.core.publisher.Flux;


public interface PartnerClient {

    Flux<Product> execute(ProductFilter filter);

}

