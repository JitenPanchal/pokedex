package com.truelayer.pokemon.services;

@FunctionalInterface
public interface IMapper<From,To> {
    To map(From from);
}
