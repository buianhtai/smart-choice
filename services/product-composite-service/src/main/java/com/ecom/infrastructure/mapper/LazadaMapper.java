package com.ecom.infrastructure.mapper;

import com.nab.domain.external.LazadaProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LazadaMapper extends ClientMapper<LazadaProduct> {
}
