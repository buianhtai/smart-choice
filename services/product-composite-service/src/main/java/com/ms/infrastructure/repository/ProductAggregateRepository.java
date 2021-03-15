package com.ms.infrastructure.repository;

import com.ms.domain.product.model.Product;
import com.ms.domain.product.model.ProductAggregate;
import com.ms.domain.product.repository.IProductAggregateRepository;
import com.ms.domain.product.repository.IProductRepository;
import com.ms.enums.Sort;
import com.ms.filter.ProductFilter;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductAggregateRepository implements IProductAggregateRepository {

    private final IProductRepository repository;

    @Override
    public Mono<ProductAggregate> getAggregate(
        ProductFilter criteria) {
        return repository
            .getProducts(criteria)
            .sort(sortOrder(criteria.getSort()))
            .collectList()
            .map(products -> ProductAggregate.builder().products(products).build());
    }

    Comparator<Product> sortByPrice() {
        return Comparator.comparing(Product::getPrice);
    }

    Comparator<Product> sortOrder(Sort sort) {
        if (sort == null) {
            return sortByPrice();
        }
        log.debug("Sort received {}", sort);
        switch (sort) {
            case DECS:
                return sortByPrice().reversed();
            default:
                return sortByPrice();
        }
    }

}
