package com.ms.infrastructure.mapper;

import com.ms.domain.external.LazadaProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LazadaMapper extends ClientMapper<LazadaProduct> {
}
