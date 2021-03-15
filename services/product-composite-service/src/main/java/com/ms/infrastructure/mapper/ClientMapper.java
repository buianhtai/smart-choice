package com.ms.infrastructure.mapper;

import com.ms.domain.external.ClientProduct;
import com.ms.domain.product.model.Product;
import com.ms.mapper.IObjectMapper;

public interface ClientMapper<T extends ClientProduct> extends IObjectMapper<T, Product> {


}
