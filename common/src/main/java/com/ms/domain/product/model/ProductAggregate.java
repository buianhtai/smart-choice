package com.ms.domain.product.model;

import com.ms.shared.AggregateRoot;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Builder
@AllArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Getter
public class ProductAggregate implements AggregateRoot<ProductAggregate> {

    private final List<Product> products;

    public ProductAggregate add(Product product) {
        this.products.add(product);
        return this;
    }

}
