package com.nab.ecom.external.infrastructure.mapper;

import com.nab.ecom.external.domain.Product;
import com.nab.ecom.external.domain.TikiProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TikiProductMapper extends IObjectMapper<TikiProduct, Product> {

}
