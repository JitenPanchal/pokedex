package com.truelayer.pokemon.services;


import com.truelayer.pokemon.services.models.translator.TranslatedInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TranslationService implements ITranslationService {

    private WebClient webClient;
    private String translatorApiUrl;

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    @Autowired
    public TranslationService(@Value("${translator.api.url}") String translatorApiUrl, WebClient webClient) {
        this.translatorApiUrl = translatorApiUrl;
        this.webClient = webClient;
    }


    private static TranslatedInformation getDefaultTranslatedInformation(String text, TranslationProvider translationProvider) {
        var success = new TranslatedInformation.Success();
        success.setTotal(1);

        var contents = new TranslatedInformation.Contents();
        contents.setTranslated(text);
        contents.setText(text);
        contents.setTranslation(translationProvider.name().toLowerCase());

        return new TranslatedInformation(success, contents);
    }

    @Override
    public Mono<TranslatedInformation> translate(final String text, TranslationProvider translationProvider) {

        if (text == null || text.isBlank()) {
            return Mono.error(() -> new IllegalArgumentException("text can not be null or blank"));
        }

        return webClient
                .get()
                .uri(String.format(translatorApiUrl, translationProvider.name().toLowerCase(), encodeValue(text)))
                .retrieve()
                .bodyToMono(TranslatedInformation.class)
                .onErrorReturn(getDefaultTranslatedInformation(text,translationProvider));

//                .exchangeToMono(response -> {
//                    if (response.statusCode().equals(HttpStatus.OK)) {
//                        return response.bodyToMono(TranslatedInformation.class);
//                    } else {
//                        var success = new TranslatedInformation.Success();
//                        success.setTotal(1);
//
//                        var contents = new TranslatedInformation.Contents();
//                        contents.setTranslated(text);
//                        contents.setText(text);
//                        contents.setTranslation(translationProvider.name().toLowerCase());
//
//                        return Mono.just(new TranslatedInformation(success, contents));
//                    }
//                });
    }
}
