package com.gyan.darpan.config;

import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateClientConfig {

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(5))
                .setSocketTimeout(Timeout.ofSeconds(5))
                .build();

        connectionManager.setDefaultConnectionConfig(connectionConfig);

        HttpHost httpHost = new HttpHost("localhost", 8080);
        HttpRoute httpRoute = new HttpRoute(httpHost);

        connectionManager.setMaxPerRoute(httpRoute, 25);

        ConnectionConfig localConnectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(1))
                .setSocketTimeout(Timeout.ofSeconds(2))
                .build();

        connectionManager.setConnectionConfigResolver(route -> {
            if (httpHost.equals(route.getTargetHost())) {
                System.out.println("localConnection");
                return localConnectionConfig;
            }
            return connectionConfig;
        });

        return connectionManager;
    }

    @Bean
    public HttpClient httpClient(PoolingHttpClientConnectionManager connectionManager) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .evictIdleConnections(TimeValue.ofSeconds(30))
                .evictExpiredConnections()
                .build();
        return httpClient;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, HttpClient htpClient) {
//        return restTemplateBuilder
//                .setConnectTimeout(Duration.ofMillis(1000))
//                .setReadTimeout(Duration.ofMillis(1000))
//                .build();

        return restTemplateBuilder.requestFactory(
                        () -> new HttpComponentsClientHttpRequestFactory(htpClient)
                )
                .build();
    }
}
