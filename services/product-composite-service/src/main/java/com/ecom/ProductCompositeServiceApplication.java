package com.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication(scanBasePackages = {
    "com.ecom",
    "com.nab"
})
public class ProductCompositeServiceApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();


        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }
}
