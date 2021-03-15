package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.ms"
})
public class ProductCompositeServiceApplication {

    public static void main(String[] args) {
        //Uncomment to debug error
       // Hooks.onOperatorDebug();


        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }
}
