package com.truelayer.pokemon.services;

import com.truelayer.pokemon.exceptions.ResourceNotFoundException;
import com.truelayer.pokemon.services.models.pokemon.PokemonInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PokemonServiceUnitTests {

    private IPokemonService pokemonService;

    @BeforeEach
    void beforeEach() {
        var baseUrl = "https://pokeapi.co/api/v2";
        pokemonService = new PokemonService(baseUrl, WebClient.create());
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with null parameter then IllegalArgumentException should be thrown")
    void test1() {

        String s = HttpStatus.INTERNAL_SERVER_ERROR.toString();

        pokemonService.getPokemonInformation(null)
                .doOnError(ex -> {
                    assertEquals(IllegalArgumentException.class, ex.getClass());
                })
                .subscribe();
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with blank name parameter then IllegalArgumentException should be thrown")
    void test2() {
        pokemonService.getPokemonInformation("")
                .doOnError(ex -> {
                    assertEquals(IllegalArgumentException.class, ex.getClass());
                })
                .subscribe();
    }

    @Test
    @DisplayName("when getPokemonInformation method is called with valid name e.g. wormadam then response name should be same as input name")
    void test3() {

        var pokemonInformation = new PokemonInformation();
        var flavorTextEntries = new ArrayList<PokemonInformation.FlavorTextEntry>();

        var flavorTextEntry1 = new PokemonInformation.FlavorTextEntry();
        flavorTextEntry1.setFlavorText("wormadam description 1");

        var flavorTextEntry2 = new PokemonInformation.FlavorTextEntry();
        flavorTextEntry2.setFlavorText("wormadam description 2");

        flavorTextEntries.add(flavorTextEntry1);
        flavorTextEntries.add(flavorTextEntry2);

        pokemonInformation.setName("wormadam");
        pokemonInformation.setFlavorTextEntries(flavorTextEntries);
        pokemonInformation.setLegendary(true);

        var habitat = new PokemonInformation.Habitat();
        habitat.setName("habitat");
        pokemonInformation.setHabitat(habitat);


        var webClientMock = Mockito.mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpecMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpecMock = Mockito.mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);


        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString(), anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<PokemonInformation>>notNull())).thenReturn(Mono.just(pokemonInformation));


        pokemonService = new PokemonService("https://pokeapi.co/api/v2", webClientMock);

        pokemonService.getPokemonInformation("wormadam").subscribe(result -> {
            Assertions.assertEquals("wormadam", result.getName());
            Assertions.assertEquals(2, result.getFlavorTextEntries().size());
            Assertions.assertEquals("wormadam description 1", result.getFlavorTextEntries().get(0).getFlavorText());
            Assertions.assertEquals("habitat", result.getHabitat().getName());
            Assertions.assertEquals(true, result.isLegendary());
            verify(webClientMock).get();
        });


    }


    @Test
    @DisplayName("when getPokemonInformation method is called with invalid name then response should be null")
    void test4() {


        var webClientMock = Mockito.mock(WebClient.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpecMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec requestBodySpecMock = Mockito.mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);


        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString(), anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<PokemonInformation>>notNull())).thenReturn(Mono.just(new PokemonInformation()));

        var pokemonService = new PokemonService("https://pokeapi.co/api/v2", webClientMock);

        pokemonService.getPokemonInformation(UUID.randomUUID().toString())
                .doOnError(ex -> {
                    assertEquals(ResourceNotFoundException.class, ex.getClass());
                    verify(webClientMock).get();
                })
                .subscribe();
    }
}