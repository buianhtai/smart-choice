package com.ecom.infrastructure.mapper;

import com.nab.domain.external.ClientProduct;
import com.nab.domain.product.model.Product;
import com.nab.mapper.IObjectMapper;

public interface ClientMapper<T extends ClientProduct> extends IObjectMapper<T, Product> {


}
