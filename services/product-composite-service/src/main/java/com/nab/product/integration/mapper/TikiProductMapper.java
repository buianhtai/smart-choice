package com.nab.product.integration.mapper;

import com.nab.domain.TikiProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TikiProductMapper extends ClientMapper<TikiProduct> {

}
