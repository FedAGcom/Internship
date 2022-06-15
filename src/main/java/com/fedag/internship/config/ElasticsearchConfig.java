package com.fedag.internship.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * class ElasticsearchConfig
 *
 * @author damir.iusupov
 * @since 2022-06-14
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.fedag.internship.repository")
@ComponentScan(basePackages = {"com.fedag.internship.service"})
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    @Value("${elasticsearch.url}")
    public String elasticsearchUrl;

    @Value("${elasticsearch.connect-timeout}")
    public int elasticsearchConnectTimeout;

    @Value("${elasticsearch.socket-timeout}")
    public int elasticsearchSocketTimeout;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration config = ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .withConnectTimeout(elasticsearchConnectTimeout)
                .withSocketTimeout(elasticsearchSocketTimeout)
                .build();
        return RestClients.create(config).rest();
    }
}
