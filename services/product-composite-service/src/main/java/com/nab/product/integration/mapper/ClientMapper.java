package com.nab.product.integration.mapper;

import com.nab.domain.ClientProduct;
import com.nab.domain.Product;
import com.nab.mapper.IObjectMapper;

public interface ClientMapper<T extends ClientProduct> extends IObjectMapper<T, Product> {


}

