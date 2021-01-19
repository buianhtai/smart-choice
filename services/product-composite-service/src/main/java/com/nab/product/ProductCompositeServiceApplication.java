package com.nab.product;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.core.publisher.Hooks;
import reactor.kafka.sender.SenderOptions;

@SpringBootApplication
@ComponentScan("com.nab")
public class ProductCompositeServiceApplication {
//
//    @Value("${api.common.version}")           String apiVersion;
//    @Value("${api.common.title}")             String apiTitle;
//    @Value("${api.common.description}")       String apiDescription;
//    @Value("${api.common.termsOfServiceUrl}") String apiTermsOfServiceUrl;
//    @Value("${api.common.license}")           String apiLicense;
//    @Value("${api.common.licenseUrl}")        String apiLicenseUrl;
//    @Value("${api.common.contact.name}")      String apiContactName;
//    @Value("${api.common.contact.url}")       String apiContactUrl;
//    @Value("${api.common.contact.email}")     String apiContactEmail;
//
//	/**
//	 * Will exposed on $HOST:$PORT/swagger-ui.html
//	 *
//	 * @return
//	 */
//	@Bean
//	public Docket apiDocumentation() {
//
//		return new Docket(SWAGGER_2)
//			.select()
//			.apis(basePackage("se.magnus.microservices.composite.product"))
//			.paths(PathSelectors.any())
//			.build()
//				.globalResponseMessage(POST, emptyList())
//				.globalResponseMessage(GET, emptyList())
//				.globalResponseMessage(DELETE, emptyList())
//				.apiInfo(new ApiInfo(
//                    apiTitle,
//                    apiDescription,
//                    apiVersion,
//                    apiTermsOfServiceUrl,
//                    new Contact(apiContactName, apiContactUrl, apiContactEmail),
//                    apiLicense,
//                    apiLicenseUrl,
//                    emptyList()
//                ));
//    }

//	@Bean
//	@LoadBalanced
//	public WebClient.Builder loadBalancedWebClientBuilder() {
//		final WebClient.Builder builder = WebClient.builder();
//		return builder;
//	}

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(ProductCompositeServiceApplication.class, args);
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, byte[]> reactiveKafkaProducerTemplate(
        KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        props.put(
            ProducerConfig.TRANSACTIONAL_ID_CONFIG, properties.getProducer().getTransactionIdPrefix());
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(props));
    }
}
