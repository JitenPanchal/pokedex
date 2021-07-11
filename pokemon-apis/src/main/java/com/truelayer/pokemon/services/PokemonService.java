package com.truelayer.pokemon.services;

import com.truelayer.pokemon.exceptions.ResourceNotFoundException;
import com.truelayer.pokemon.services.models.pokemon.PokemonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokemonService implements IPokemonService {

    private WebClient webClient;
    private String baseUrl;

    @Autowired
    public PokemonService(@Value("${pokemon.api.url}") String baseUrl, WebClient webClient) {
        this.baseUrl = baseUrl;
        this.webClient = webClient;
    }

    @Override
    public Mono<PokemonInformation> getPokemonInformation(String name) {

        if (name == null || name.isBlank()) {
            return  Mono.error(() -> new IllegalArgumentException("invalid pokemon name"));
        }

        return webClient
                .get()
                .uri(baseUrl + "/pokemon-species/{name}", name)
                .retrieve()
                .bodyToMono(PokemonInformation.class)
                .doOnError((ex) -> {
                    throw new ResourceNotFoundException("pokemon named " + name + " not found");
                });
    }
}