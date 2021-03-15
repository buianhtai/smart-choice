package com.ms;

import static com.ms.constant.ApiConstants.PRODUCT_API;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication;

import com.ms.test.config.SecurityTestConfig;
import com.ms.test.data.MockDataTest;
import com.github.javafaker.Faker;
import com.ms.domain.product.model.Product;
import com.ms.domain.product.model.ProductAggregate;
import com.ms.domain.product.repository.IProductAggregateRepository;
import com.ms.enums.Sort;
import com.ms.enums.Source;
import com.ms.exceptions.InvalidInputException;
import com.ms.exceptions.NotFoundException;
import com.ms.filter.ProductFilter;
import java.util.Arrays;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "debug=true")
@AutoConfigureWebTestClient(timeout = "18000")
@Import(SecurityTestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductCompositeServiceApplicationTests {

    private static final ProductFilter SEARCH_PRODUCT_OK =  ProductFilter.builder()
        .query("iphone")
        .sort(Sort.ASC)
        .build();


    private static final ProductFilter SEARCH_PRODUCT_NOT_FOUND = ProductFilter.builder()
        .query("iphone 12 000000")
        .sort(Sort.ASC)
        .build();

    private static final ProductFilter SEARCH_PRODUCT_IN_VALID = ProductFilter.builder()
        .query("xxx")
        .sort(Sort.ASC)
        .build();

    @Autowired
    private WebTestClient client;

    @MockBean
    private IProductAggregateRepository repository;


    private final Faker faker = new Faker(new Locale("vi"));

    @BeforeEach
    public void setUp() {

        when(repository.getAggregate(eq(SEARCH_PRODUCT_OK))).
            thenReturn(
                Mono.just(
                    ProductAggregate.builder()
                        .products(
                            Arrays.asList(
                                generateMock(faker, Source.TIKI),
                                generateMock(faker, Source.SHOPEE),
                                generateMock(faker, Source.LAZADA)
                            )
                        )
                        .build()

                ));

        when(repository.getAggregate(eq(SEARCH_PRODUCT_NOT_FOUND))).
            thenThrow(
                new NotFoundException(
                    "No product found for query: " + SEARCH_PRODUCT_NOT_FOUND.getQuery())
            );

        when(repository.getAggregate(eq(SEARCH_PRODUCT_IN_VALID)))
            .thenThrow(new InvalidInputException("INVALID: " + SEARCH_PRODUCT_IN_VALID));
    }

    @Test
    public void contextLoads() {
    }


    private Product generateMock(Faker faker, Source source) {

        return Product.builder()
            .code(faker.code().imei())
            .name(SEARCH_PRODUCT_OK.getQuery())
            .description(faker.commerce().productName())
            .price(Double.parseDouble(faker.commerce().price()))
            .brand(faker.company().name())
            .category(faker.random().hex())
            .promotion(faker.commerce().promotionCode())
            .image(faker.internet().image())
            .discountRate(faker.number().randomDouble(10, 1, 100))
            .source(source)
            .build();
    }

    @Test
    public void test_get_product_should_Ok() {
        getAndVerifyProduct(SEARCH_PRODUCT_OK, OK)
            .jsonPath("$.data.products.length()").isEqualTo(3)
            .jsonPath("$.data.products[0].code").isNotEmpty()
            .jsonPath("$.data.products.[0].source").isNotEmpty()
            .jsonPath("$.data.products.[0].name").isNotEmpty()
            .jsonPath("$.data.products.[1].code").isNotEmpty()
            .jsonPath("$.data.products.[1].source").isNotEmpty()
            .jsonPath("$.data.products.[1].name").isNotEmpty()
            .jsonPath("$.data.products.[2].code").isNotEmpty()
            .jsonPath("$.data.products.[2].source").isNotEmpty()
            .jsonPath("$.data.products.[2].name").isNotEmpty();
    }

    @Test
    public void test_get_product_not_found() {

        getAndVerifyProduct(SEARCH_PRODUCT_NOT_FOUND, NOT_FOUND)
            .jsonPath("$.path").isEqualTo(PRODUCT_API)
            .jsonPath("$.message")
            .isEqualTo("No product found for query: " + SEARCH_PRODUCT_NOT_FOUND.getQuery());
    }

    @Test
    public void test_get_product_invalid_input() {

        getAndVerifyProduct(SEARCH_PRODUCT_IN_VALID, UNPROCESSABLE_ENTITY)
            .jsonPath("$.path").isEqualTo(PRODUCT_API)
            .jsonPath("$.message").isEqualTo("INVALID: " + SEARCH_PRODUCT_IN_VALID);
    }

    @Test
    public void test_get_product_should_throw_UNAUTHORIZED() {
        client
            .get()
            .uri(PRODUCT_API)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(UNAUTHORIZED)
            .expectBody()
            .consumeWith(System.out::println);
    }

    @Test
    public void test_get_product_should_throw_FORBIDDEN() {
        client
            .mutateWith(mockAuthentication(MockDataTest.WITHOUT_AUTH))
            .get()
            .uri(PRODUCT_API)
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(FORBIDDEN)
            .expectBody()
            .consumeWith(System.out::println);
    }

    private WebTestClient.BodyContentSpec getAndVerifyProduct(ProductFilter request,
        HttpStatus expectedStatus) {
        return client
            .mutateWith(mockAuthentication(MockDataTest.AUTH))
            .get()
            .uri("/api/v1/products?query=" + request.getQuery() + "&sort=" + request.getSort())
            .accept(APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(expectedStatus)
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBody()
            .consumeWith(System.out::println);
    }
}
