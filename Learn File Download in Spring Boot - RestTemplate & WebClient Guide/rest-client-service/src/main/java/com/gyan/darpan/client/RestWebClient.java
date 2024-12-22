package com.gyan.darpan.client;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class RestWebClient {

    private final WebClient webClient;

    public RestWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T, U> ResponseEntity<T> execute(String baseUrl,
                                            String resourceUrl,
                                            Class<T> responseClassType,
                                            Map<String, Object> queryParam,
                                            HttpHeaders headers,
                                            HttpMethod httpMethod,
                                            Map<String, Object> pathVariables,
                                            U body) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + resourceUrl);

        if (pathVariables != null && !pathVariables.isEmpty()) {
            uriComponentsBuilder.uriVariables(pathVariables);
        }
        if (queryParam != null && !queryParam.isEmpty()) {
            queryParam.forEach(uriComponentsBuilder::queryParam);
        }

        String url = uriComponentsBuilder.toUriString();

        System.out.println("webclient : " + url);


        WebClient.RequestBodySpec requestBodySpec = webClient
                .method(httpMethod)
                .uri(url)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        httpHeaders.addAll(headers);
                    }
                });

        ResponseEntity<T> responseEntity = null;

        if (body != null) {
            responseEntity = requestBodySpec.bodyValue(body)
                    .retrieve()
                    .toEntity(responseClassType)
                    .block();
        } else {
            responseEntity = requestBodySpec
                    .retrieve()
                    .toEntity(responseClassType)
                    .block();
        }

        return responseEntity;

    }
}
