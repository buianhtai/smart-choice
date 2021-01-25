package com.nab.smartchoice.util.product;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "debug=true")
@AutoConfigureWebTestClient(timeout = "18000")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
