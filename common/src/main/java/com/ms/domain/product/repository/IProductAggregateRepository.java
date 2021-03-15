package com.ms.domain.product.repository;

import com.ms.domain.product.model.ProductAggregate;
import com.ms.filter.ProductFilter;
import reactor.core.publisher.Mono;

/***
 *  Centralized all things that ProductAggregate should have.
 */
public interface IProductAggregateRepository {

    /***
     * Used to build ProductAggregate
     * @param criteria the query and sorting
     * @return ProductAggregate
     */
    Mono<ProductAggregate> getAggregate(ProductFilter criteria);
}
