package com.truelayer.pokemon.services;

import com.truelayer.pokemon.services.models.pokemon.PokemonInformation;
import reactor.core.publisher.Mono;

public interface IPokemonService {
    Mono<PokemonInformation> getPokemonInformation(String name);
}
