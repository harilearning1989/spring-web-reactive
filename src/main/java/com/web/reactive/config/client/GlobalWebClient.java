package com.web.reactive.config.client;

import com.web.reactive.config.filter.RequestLoggingFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

public class GlobalWebClient {

    private WebClient createWebClient(String url){
        return WebClient
                .builder()
                .baseUrl(url)
                .filter(new RequestLoggingFilterFunction())
                .build();
    }
    public <S> S createClient(Class<S> serviceType,String url){
        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(createWebClient(url)))
                .blockTimeout(Duration.ofSeconds(20))
                .build()
                .createClient(serviceType);
    }
}
