package com.nab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication(scanBasePackages = {
    "com.nab"
})
public class ProductCompositeServiceApplication {

    public static void main(String[] args) {
        //Uncomment to debug error
       // Hooks.onOperatorDebug();


        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }
}
