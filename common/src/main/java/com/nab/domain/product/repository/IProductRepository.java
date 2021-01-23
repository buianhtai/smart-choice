package com.nab.domain.product.repository;

import com.nab.domain.product.model.Product;
import com.nab.filter.ProductFilter;
import reactor.core.publisher.Flux;

public interface IProductRepository {

    Flux<Product> getProducts(ProductFilter criteria);
}
