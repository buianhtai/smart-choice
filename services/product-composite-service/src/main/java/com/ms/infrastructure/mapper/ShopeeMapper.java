package com.ms.infrastructure.mapper;

import com.ms.domain.external.ShoppeProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ShopeeMapper extends ClientMapper<ShoppeProduct> {

}
