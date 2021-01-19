package com.nab.product.integration.mapper;

import com.nab.domain.ShoppeProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ShopeeMapper extends ClientMapper<ShoppeProduct> {

}
