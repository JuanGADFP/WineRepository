package org.juang.test.springboot.app.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;


import org.juang.test.springboot.app.models.Wine;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WineControllerTestSpringWebTest {

    @Autowired
    private WebTestClient client;


    @Test
    @Order(1)
    @DisplayName("Prueba GET.*")
    void getWines() {
        client.get().uri("/v1/wines/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.wineResponse.wine[0].name").isEqualTo("Espumante, CASA BOHER")
                .jsonPath("$.wineResponse.wine[0].winery").isEqualTo("CASA BOHER")
                .jsonPath("$.wineResponse.wine[0].año").isEqualTo(2015);

    }

    @Test
    @Order(2)
    @DisplayName("Prueba GET/{id}")
    void consultarPorId() {
        client.get().uri("/v1/wines/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.wineResponse.wine[0].name").isEqualTo("Espumante, CASA BOHER")
                .jsonPath("$.wineResponse.wine[0].winery").isEqualTo("CASA BOHER")
                .jsonPath("$.wineResponse.wine[0].año").isEqualTo(2015);

        client.get().uri("/v1/wines/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.metadata[0].tipo").isEqualTo("Response Status NOT_FOUND")
                .jsonPath("$.metadata[0].codigo").isEqualTo("404")
                .jsonPath("$.metadata[0].dato").isEqualTo("Could not consult wine id");
    }

    @Test
    @Order(3)
    @DisplayName("Prueba POST")
    void crear() {
        Wine wine = new Wine(null, "Espumante de Prueba", "Altaland", 1999);

        client.post().uri("/v1/wines/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(wine)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.wineResponse.wine[0].name").isEqualTo("Espumante de Prueba")
                .jsonPath("$.wineResponse.wine[0].winery").isEqualTo("Altaland")
                .jsonPath("$.wineResponse.wine[0].año").isEqualTo(1999)
                .jsonPath("$.wineResponse.wine[0].id").isEqualTo(2);

    }



    @Test
    @Order(4)
    @DisplayName("Prueba GET despues de Postear en base")
    void testAfterPOST() {
        client.get().uri("/v1/wines/2")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.wineResponse.wine[0].name").isEqualTo("Espumante de Prueba")
                .jsonPath("$.wineResponse.wine[0].winery").isEqualTo("Altaland")
                .jsonPath("$.wineResponse.wine[0].año").isEqualTo(1999)
                .jsonPath("$.wineResponse.wine[0].id").isEqualTo(2);

        client.get().uri("/v1/wines/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.wineResponse.wine").value(hasSize(2));
    }

    @Test
    @Order(5)
    @DisplayName("Probando metodo PUT")
    void actualizar() {

        Wine wine = new Wine(2L, "Espumante de Prueba PUT name", "otro Put winery", 2000);
        client.put().uri("/v1/wines/2")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.wineResponse.wine[0].name").isEqualTo("Espumante de Prueba")
                .jsonPath("$.wineResponse.wine[0].winery").isEqualTo("Altaland")
                .jsonPath("$.wineResponse.wine[0].año").isEqualTo(1999)
                .jsonPath("$.wineResponse.wine[0].id").isEqualTo(2);


       // wine2 = new Wine(null, null, null, 0);
        client.put().uri("/v1/wines/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.metadata[0].tipo").isEqualTo("Response Status NOT_FOUND")
                .jsonPath("$.metadata[0].codigo").isEqualTo("404")
                .jsonPath("$.metadata[0].dato").isEqualTo("Could not consult wine id");

    }



    @Test
    @Order(6)
    @DisplayName("Probando metodo DELETE")
    void eliminar() {
        client.get().uri("/v1/wines/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.wineResponse.wine").value(hasSize(2));

        client.delete().uri("/v1/wines/2")
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/v1/wines/2")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.metadata[0].tipo").isEqualTo("Response Status NOT_FOUND")
                .jsonPath("$.metadata[0].codigo").isEqualTo("404")
                .jsonPath("$.metadata[0].dato").isEqualTo("Could not consult wine id");

        client.get().uri("/v1/wines/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.wineResponse.wine").value(hasSize(1));

    }
}