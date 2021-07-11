package com.truelayer.pokemon.services;


import com.truelayer.pokemon.services.models.translator.TranslatedInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TranslationServiceUnitTests {

    private ITranslationService translationService;
    private String translatorApiUrl;

    @BeforeEach
    void beforeEach() {
        translatorApiUrl = "https://api.funtranslations.com/translate/%s.json?text=%s";
        translationService = new TranslationService(translatorApiUrl, WebClient.create());

    }

    @Test
    @DisplayName("when translate method is called with null parameter then IllegalArgumentException should be thrown")
    void test1() {

        translationService.translate(null, TranslationProvider.YODA)
                .doOnError(ex -> {
                    assertEquals(IllegalArgumentException.class, ex.getClass());
                })
                .subscribe();

    }

    @Test
    @DisplayName("when translate method is called with blank text parameter then IllegalArgumentException should be thrown")
    void test2() {
        translationService.translate("", TranslationProvider.YODA)
                .doOnError(ex -> {
                    assertEquals(IllegalArgumentException.class, ex.getClass());
                })
                .subscribe();
    }

    @Test
    @DisplayName("when translate method is called with valid text then response text should be same as input text")
    void test3() {

        var success = new TranslatedInformation.Success();
        success.setTotal(1);

        var contents = new TranslatedInformation.Contents();
        contents.setTranslated("Translated text");
        contents.setText("text");
        contents.setTranslation("shakespeare");

        var translatedInformation = new TranslatedInformation(success, contents);

        var webClientMock = Mockito.mock(WebClient.class);
        var requestBodyUriSpecMock = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        var requestBodySpecMock = Mockito.mock(WebClient.RequestBodySpec.class);
        var requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        var requestHeadersUriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);


        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<TranslatedInformation>>notNull())).thenReturn(Mono.just(translatedInformation));

        var translationService = new TranslationService(translatorApiUrl, webClientMock);

        translationService.translate("wormadam", TranslationProvider.YODA).subscribe(result -> {

            assertEquals("Translated text", result.getContents().getTranslated());
            assertEquals("shakespeare", result.getContents().getTranslation());
            assertEquals("text", result.getContents().getText());
            assertEquals(1, result.getSuccess().getTotal());

            verify(webClientMock).get();
        });
    }
}