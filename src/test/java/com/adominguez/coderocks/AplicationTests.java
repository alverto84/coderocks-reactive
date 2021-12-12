package com.adominguez.coderocks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class AplicationTests {

  @Autowired
  private WebTestClient webTestClient;
  private Integer port = 8080;

  @Test
  void httpClientExample() {

    // no need for this
    WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:" + port)
        .build();

    this.webTestClient
        .post()
        .uri("/auth/login")
        .exchange()
        .expectStatus().is2xxSuccessful();
  }
}
