package com.bern.norstienpaymentsapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "pathToCsvFile=/home/unknown/Downloads/propertyApi.csv")
class NorstienpaymentsapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
