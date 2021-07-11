package com.truelayer.pokemon.interceptors;


import com.truelayer.pokemon.exceptions.ResourceNotFoundException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        return assembleError(request);
    }

    private Map<String, Object> assembleError(ServerRequest request) {
        var errorAttributes = new HashMap<String, Object>();

        Throwable error = getError(request);

        if (error instanceof ResourceNotFoundException) {
            errorAttributes.put("code", HttpStatus.NOT_FOUND);
            errorAttributes.put("data", error.getMessage());
        } else {
            errorAttributes.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
            errorAttributes.put("data", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }

        return errorAttributes;
    }
}
