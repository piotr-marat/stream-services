package com.backbase.stream.compositions.product.core.config;

import com.backbase.stream.compositions.integration.product.ApiClient;
import com.backbase.stream.compositions.integration.product.api.ProductIntegrationApi;
import com.backbase.stream.compositions.transaction.client.TransactionCompositionApi;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(ProductConfigurationProperties.class)
public class ProductCompositionConfiguration {
    private final ProductConfigurationProperties productConfigurationProperties;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .build();
    }

    @Bean
    @Primary
    public ProductIntegrationApi productIntegrationApi(ApiClient productClient) {
        return new ProductIntegrationApi(productClient);
    }

    @Bean
    @Primary
    public TransactionCompositionApi transactionCompositionApi(
            com.backbase.stream.compositions.transaction.ApiClient transactionClient) {
        return new TransactionCompositionApi(transactionClient);
    }

    @Bean
    public com.backbase.stream.compositions.transaction.ApiClient transactionClient() {
        com.backbase.stream.compositions.transaction.ApiClient apiClient =
                new com.backbase.stream.compositions.transaction.ApiClient();
        apiClient.setBasePath(productConfigurationProperties.getChains().getTransactionComposition().getBaseUrl());

        return apiClient;
    }

    @Bean
    public ApiClient productClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(productConfigurationProperties.getIntegrationBaseUrl());

        return apiClient;
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "product-service");
    }
}
