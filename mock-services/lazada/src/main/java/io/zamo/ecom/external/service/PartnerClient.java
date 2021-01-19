package com.nab.ecom.external.service;

import com.nab.ecom.external.domain.Product;
import com.nab.ecom.external.domain.SearchRequest;
import com.nab.ecom.external.domain.Source;
import reactor.core.publisher.Flux;

public interface PartnerClient {

    boolean isSupport(Source source);

    Flux<Product> execute(SearchRequest condition);

}

