package com.truelayer.pokemon.api.controllers;

import com.truelayer.pokemon.models.PokemonInformationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PokemonResourceTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    private String baseUrl;

    @BeforeEach
    void beforeEach() {
        baseUrl = String.format("http://localhost:%s", port);
    }

    @Test
    @DisplayName("when pokemon/mewtwo api is called with valid name e.g. mewtwo then response name should be same as input name")
    void test1() throws Exception {

        var expected = "mewtwo";
        var result = testRestTemplate
                .getForEntity(baseUrl + "/pokemon/{name}", PokemonInformationDto.class, expected)
                .getBody();

        assertEquals(expected, result.getName());

    }

    @Test
    @DisplayName("when pokemon/UUID api is called with invalid name e.g. UUID then response should be 404 Not Found")
    void test2() throws Exception {

        var expected = UUID.randomUUID().toString();
        var result = testRestTemplate
                .getForEntity(baseUrl + "/pokemon/{name}", PokemonInformationDto.class, expected)
                .getStatusCode();

        assertEquals(HttpStatus.NOT_FOUND, result);

    }

    @Test
    @DisplayName("when pokemon/translated/mewtwo api is called with valid name e.g. mewtwo then response name should be same as input name")
    void test3() throws Exception {

        var expected = "mewtwo";
        var result = testRestTemplate
                .getForEntity(baseUrl + "/pokemon/translated/{name}", PokemonInformationDto.class, expected)
                .getBody();

        assertEquals(expected, result.getName());

    }

    @Test
    @DisplayName("when pokemon/translated/UUID api is called with invalid name e.g. UUID then response should be 404 Not Found")
    void test4() throws Exception {

        var expected = UUID.randomUUID().toString();
        var result = testRestTemplate
                .getForEntity(baseUrl + "/pokemon/translated/{name}", PokemonInformationDto.class, expected)
                .getStatusCode();

        assertEquals(HttpStatus.NOT_FOUND, result);

    }
}
