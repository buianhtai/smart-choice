package com.nab.infrastructure.repository;

import com.nab.infrastructure.client.PartnerClient;
import com.nab.domain.product.model.Product;
import com.nab.domain.product.repository.IProductRepository;
import com.nab.filter.ProductFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRepository implements IProductRepository {

    private final List<PartnerClient> clients;

    public Flux<Product> getProducts(ProductFilter request) {
        return Flux.fromIterable(clients)
            .flatMap(
                client -> client
                    .execute(request)
                    .onErrorResume(throwable -> Flux.empty())
                    .subscribeOn(Schedulers.boundedElastic())
            );
    }
}
