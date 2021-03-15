package com.ms.domain.product.model;

import com.ms.enums.Source;
import com.ms.shared.ValueObject;
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
