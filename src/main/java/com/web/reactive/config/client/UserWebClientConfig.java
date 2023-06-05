package com.web.reactive.config.client;

import com.web.reactive.client.services.UserClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserWebClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserWebClientConfig.class);

    @Value("{user.api.baseUrl}")
    private String userBaseUrl;

    @Bean
    public UserClientService userClientService() {
        return new GlobalWebClient()
                .createClient(UserClientService.class, "https://dummyjson.com/users");
    }

}
