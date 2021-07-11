package com.truelayer.pokemon.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslationServiceIntegrationTests {

    private ITranslationService translationService;
    private String translatorApiUrl;

    @BeforeEach
    void beforeEach() {
        translatorApiUrl = "https://api.funtranslations.com/translate/%s.json?text=%s";
        translationService = new TranslationService(translatorApiUrl, WebClient.create());
    }

    @Test
    @DisplayName("when translate method is called with valid text e.g. hi then response text should be same as input text")
    void test1() throws Exception {

        var expectedName = "hi";

        translationService.translate("hi", TranslationProvider.YODA)
                .subscribe((result) -> {
                    assertEquals(expectedName, result.getContents().getText());
                });
    }

    @Test
    @DisplayName("when translate method is called with valid number e.g. 3 then response text & translated text should be same as input text")
    void test2() throws Exception {
        var expectedName = "3";

        translationService.translate("3", TranslationProvider.YODA).subscribe((result) -> {
            assertEquals(expectedName, result.getContents().getText());
            assertEquals(expectedName, result.getContents().getTranslated());
        });

    }
}
