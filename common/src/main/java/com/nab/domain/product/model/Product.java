package com.nab.domain.product.model;

import com.nab.enums.Source;
import com.nab.shared.ValueObject;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Product implements ValueObject<Product> {

    String code;
    String name;
    String description;
    double price;
    String image;
    double discountRate;
    String promotion;
    String category;
    String brand;
    Source source;
}
