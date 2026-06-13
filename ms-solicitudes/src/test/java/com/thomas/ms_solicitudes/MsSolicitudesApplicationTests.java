package com.thomas.ms_solicitudes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsSolicitudesApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	void pingReturnsServiceStatus() {
		webTestClient.get()
				.uri("/ping")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.service").isEqualTo("ms-solicitudes")
				.jsonPath("$.status").isEqualTo("UP");
	}
}
