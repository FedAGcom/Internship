package com.fedag.internship.config;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

/**
 * class ElasticsearchConfig
 *
 * @author damir.iusupov
 * @since 2022-06-14
 */
@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.fedag.internship.service"})
@EnableElasticsearchRepositories(basePackages = "com.fedag.internship.repository")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    private final ElasticsearchProperties elasticsearchProperties;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration config = ClientConfiguration.builder()
                .connectedTo(elasticsearchProperties.getUris().get(0))
                .withConnectTimeout(elasticsearchProperties.getConnectionTimeout())
                .withSocketTimeout(elasticsearchProperties.getSocketTimeout())
                .withBasicAuth(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword())
                .withDefaultHeaders(headers())
                .build();
        return RestClients.create(config).rest();
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7");
        return headers;
    }
}
