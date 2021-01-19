package com.nab.product.integration.client;

import com.nab.domain.Product;
import com.nab.domain.SearchRequest;
import com.nab.domain.Source;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Flux;


public interface PartnerClient {

    boolean isSupport(Source source);

    Flux<Product> execute(HttpHeaders headers, SearchRequest condition);

}

