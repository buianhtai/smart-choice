package com.nab.domain.product.repository;

import com.nab.domain.product.model.ProductAggregate;
import com.nab.filter.ProductFilter;
import reactor.core.publisher.Mono;

public interface IProductAggregateRepository {

    Mono<ProductAggregate> getAggregate(ProductFilter criteria);
}
