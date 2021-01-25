package com.nab.infrastructure.mapper;

import com.nab.domain.external.TikiProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TikiProductMapper extends ClientMapper<TikiProduct> {

}
