package com.nab.ecom.external.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Getter
@Setter
public class TikiProduct {

    String code;
    String name;
    Double price;
    String image;
    BigDecimal discountRate;
    String promotion;
    String category;
    String brand;
    String year;
}
