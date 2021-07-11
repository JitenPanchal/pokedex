package com.truelayer.pokemon.services;

import com.truelayer.pokemon.services.TranslationProvider;
import com.truelayer.pokemon.services.models.translator.TranslatedInformation;
import reactor.core.publisher.Mono;

public interface ITranslationService {
    Mono<TranslatedInformation> translate(String text, TranslationProvider translationProvider);
}
