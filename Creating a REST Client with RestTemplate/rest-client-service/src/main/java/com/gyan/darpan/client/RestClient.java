package com.gyan.darpan.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class RestClient {

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> get(String baseUrl,
                                     String resourceUrl,
                                     Class<T> clazz,
                                     Map<String, Object> queryParam) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl + resourceUrl);
        if (queryParam != null && !queryParam.isEmpty()) {
            queryParam.forEach(uriComponentsBuilder::queryParam);
        }

        String url = uriComponentsBuilder.toUriString();

        System.out.println(url);

        return restTemplate.getForEntity(
                url,
                clazz
        );

    }

}