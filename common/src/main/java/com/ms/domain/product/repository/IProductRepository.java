package com.ms.domain.product.repository;

import com.ms.domain.product.model.Product;
import com.ms.filter.ProductFilter;
import reactor.core.publisher.Flux;

/***
 * Repository related to perform function related to product
 */
public interface IProductRepository {

    /**
     * Get list of product from the query condition
     *
     * @param criteria the query and sort
     * @return products
     */
    Flux<Product> getProducts(ProductFilter criteria);
}
