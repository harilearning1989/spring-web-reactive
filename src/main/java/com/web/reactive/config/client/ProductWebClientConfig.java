package com.web.reactive.config.client;

import com.web.reactive.client.services.ProductClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProductWebClientConfig {

    private static final Logger LOGGER= LoggerFactory.getLogger(ProductWebClientConfig.class);

    @Value("{product.api.baseUrl}")
    private String productBaseUrl;

    @Bean
    public ProductClientService productClientService(){
        return new GlobalWebClient()
                .createClient(ProductClientService.class,"https://dummyjson.com/products");
    }

}
