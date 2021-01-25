package com.nab.domain.product.repository;

import com.nab.domain.product.model.ProductAggregate;
import com.nab.filter.ProductFilter;
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
