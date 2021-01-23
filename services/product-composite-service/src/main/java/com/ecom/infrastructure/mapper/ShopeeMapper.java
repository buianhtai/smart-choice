package com.ecom.infrastructure.mapper;

import com.nab.domain.external.ShoppeProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ShopeeMapper extends ClientMapper<ShoppeProduct> {

}
