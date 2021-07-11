package com.truelayer.pokemon.services;

import com.truelayer.pokemon.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonServiceIntegrationTests {

    private IPokemonService pokemonService;

    @BeforeEach
    void beforeEach() {
        var baseUrl = "https://pokeapi.co/api/v2";
        pokemonService = new PokemonService(baseUrl, WebClient.create());
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with valid name e.g. wormadam then response name should be same as input name")
    void test() throws Exception {
        var expectedName = "wormadam";

        pokemonService.getPokemonInformation("wormadam")
                .subscribe((result) -> {
                    assertEquals(expectedName, result.getName());
                });
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with invalid name then response should be null")
    void test5() {

        pokemonService.getPokemonInformation(UUID.randomUUID().toString())
                .doOnError(ex -> {
                    assertEquals(ResourceNotFoundException.class, ex.getClass());
                })
                .subscribe();
    }

}