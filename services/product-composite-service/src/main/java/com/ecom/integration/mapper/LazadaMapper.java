package com.ecom.integration.mapper;

import com.nab.domain.LazadaProduct;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LazadaMapper extends ClientMapper<LazadaProduct> {

}
