package com.ms.domain.external;

import com.ms.enums.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LazadaProduct implements ClientProduct {

    String code;
    String name;
    String description;
    double price;
    String image;
    double discountRate;
    String promotion;
    String category;
    String brand;

    @Override
    public Source getSource() {
        return Source.LAZADA;
    }
}
