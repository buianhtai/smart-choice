package com.ms.infrastructure.mapper;

import com.ms.domain.external.TikiProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TikiProductMapper extends ClientMapper<TikiProduct> {

}
